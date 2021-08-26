package com.qohash.cabaneio2021.inserter.neo4j

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.inserter.neo4j.assembler.*
import com.qohash.cabaneio2021.inserter.neo4j.entity.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.neo4j.core.Neo4jClient
import org.springframework.stereotype.Component
import java.util.*

const val NEO4J = "neo4j"

data class TweetImport(
    val tweet: Neo4jTweetEntity,
    val authorId: UUID,
    val hashTags: List<Neo4jHashTagEntity>,
    val links: List<Neo4jLinkEntity>,
    val source: Neo4jSourceEntity,
    val mentionUserIds: List<UUID>
)

data class RetweetImport(
    val retweet: Neo4jRetweetEntity,
    val authorId: UUID,
    val tweetId: UUID,
)

data class UserFollows(
    val followerId: UUID,
    val followedId: UUID,
)

data class UserLike(
    val userId: UUID,
    val tweetId: UUID
)

@Component
@Qualifier(NEO4J)
class Neo4jInserter(
    private val client: Neo4jClient,
    private val mapper: ObjectMapper
) : Inserter, InitializingBean {
    override fun insert(model: TwitterModel) {
        val users = model.users.map { it.toNeo4j() }
        // Super optimal batch insertion. Converts something that takes around 10 minutes into a few seconds.
        // Has the downside to not return any error if something in the queries is wrong
        client.query(
            """
            ${
                periodicIterate(
                    fetchQuery = "UNWIND $${"businesses"} AS business RETURN business",
                    callback = "MERGE (businessNode:User { id: business.id }) SET businessNode = business SET businessNode:Business",
                    params = "{ businesses: $${"businesses"} }"
                )
            }
            
            ${
                periodicIterate(
                    fetchQuery = "UNWIND $${"individuals"} AS individual RETURN individual",
                    callback = "MERGE (individualNode:User { id: individual.id }) SET individualNode = individual SET individualNode:Individual",
                    params = "{ individuals: $${"individuals"} }"
                )
            }
            
            ${
                periodicIterate(
                    fetchQuery = "UNWIND $${"tweets"} AS tweet RETURN tweet",
                    callback = """
                            MERGE (user:User {id: tweet.authorId})
                            MERGE (tweetNode:Tweet:Publication { id: tweet.tweet.id }) SET tweetNode = tweet.tweet
                            CREATE (user)-[:POSTED]->(tweetNode)
                            
                            FOREACH (userId IN tweet.mentionUserIds | 
                                MERGE (mentioned:User { id: userId })
                                CREATE (tweetNode)-[:MENTIONS]->(mentioned)
                            )
                            
                            FOREACH(tag IN tweet.hashTags | 
                                MERGE (tagNode:HashTag {value: tag.value})
                                CREATE (tweetNode)-[:HAS_TAG]->(tagNode)
                            )
                            
                           FOREACH(link IN tweet.links | 
                                MERGE (linkNode:Link {url: link.url }) 
                                CREATE (tweetNode)-[:HAS_LINK]->(linkNode)
                            )
                            
                            MERGE (sourceNode:Source {name: tweet.source.name})
                            CREATE (tweetNode)-[:POSTED_VIA]->(sourceNode)
                    """,
                    params = "{ tweets: $${"tweets"} }",
                    batchSize = 25
                )
            }

            ${
                periodicIterate(
                    fetchQuery = "UNWIND $${"retweets"} AS retweet RETURN retweet",
                    callback = """
                        MERGE (author:User { id: retweet.authorId })
                        MERGE (tweet:Tweet:Publication { id: retweet.tweetId })
                        CREATE (retweetNode:Retweet:Publication) SET retweetNode = retweet.retweet
                        CREATE (author)-[:POSTED]->(retweetNode)
                        CREATE (retweetNode)-[:RETWEETS]->(tweet)
                    """,
                    params = "{ retweets: $${"retweets"} }"
                )
            }
            
            ${
                periodicIterate(
                    fetchQuery = "UNWIND $${"userLikes"} AS like RETURN like",
                    callback = """
                        MERGE (user:User { id: like.userId })
                        MERGE (tweet:Tweet:Publication { id: like.tweetId })
                        CREATE (user)-[:LIKES]->(tweet)
                    """,
                    params = "{ userLikes: $${"userLikes"} }"
                )
            }
            
            ${
                periodicIterate(
                    fetchQuery = "UNWIND $${"userFollows"} AS follow RETURN follow",
                    callback = """
                        MERGE (user:User { id: follow.followerId })
                        MERGE (followed:User { id: follow.followedId })
                        CREATE (user)-[:FOLLOWS]->(followed)
                    """,
                    params = "{ userFollows: $${"userFollows"} }"
                )
            }
            
            RETURN true
        """
        ).bindAll(
            mapOf(
                "businesses" to users.filterIsInstance<Neo4jBusinessEntity>(),
                "individuals" to users.filterIsInstance<Neo4jIndividualEntity>(),
                "tweets" to model.userTweets.toNeo4jTweets(),
                "retweets" to model.userRetweets.toNeo4jRetweets(),
                "userLikes" to model.userTweetLikes.toNeo4jTweetLikes(),
                "userFollows" to model.userFollows.toNeo4jFollows(),
            ).map { it.key to mapper.convertValue<List<Map<String, Any>>>(it.value) }.toMap()
        ).run()

    }

    override fun afterPropertiesSet() {
        listOf(
            "CREATE CONSTRAINT user_id_unique IF NOT EXISTS ON (user:User) ASSERT user.id IS UNIQUE",
            "CREATE CONSTRAINT publication_id_unique IF NOT EXISTS ON (publication:Publication) ASSERT publication.id IS UNIQUE",
            "CREATE CONSTRAINT link_url_unique IF NOT EXISTS ON (link:Link) ASSERT link.url IS UNIQUE",
            "CREATE CONSTRAINT hashtag_value_unique IF NOT EXISTS ON (hashTag:HashTag) ASSERT hashTag.value IS UNIQUE",
            "CREATE CONSTRAINT source_value_unique IF NOT EXISTS ON (source:Source) ASSERT source.name IS UNIQUE"
        ).forEach {
            client.query(it).run()
        }
    }

    private fun periodicIterate(
        fetchQuery: String,
        callback: String,
        params: String,
        batchSize: Int = 50
    ): String {
        val randomVariableLetters: List<Char> = ('A'..'Z').toList()
        val randomVariable = (1..10).map { randomVariableLetters.random() }.joinToString(separator = "")
        return """
            CALL apoc.periodic.iterate(
                '$fetchQuery', 
                '$callback',
                {batchSize: $batchSize, parallel: true, params: $params}
            ) YIELD total AS $randomVariable
        """.trimIndent()
    }
}