package com.qohash.cabaneio2021.inserter.arangodb

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User
import org.springframework.stereotype.Component

const val ARANGO_DB = "arangodb"

@Component
class ArangoDbInserter : Inserter {
    override val name: String = ARANGO_DB

    override fun insert(model: TwitterModel) {
        TODO("Not yet implemented")
    }
}