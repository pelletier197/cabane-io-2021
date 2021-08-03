package com.qohash.cabaneio2021.inserter.postgres

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User
import org.springframework.stereotype.Component

const val POSTGRES = "postgres"

@Component
class PostgresInserter : Inserter {
    override val name: String = POSTGRES

    override fun insert(model: TwitterModel) {
        TODO("Not yet implemented")
    }
}