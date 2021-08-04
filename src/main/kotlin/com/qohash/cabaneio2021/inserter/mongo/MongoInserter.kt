package com.qohash.cabaneio2021.inserter.mongo

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import com.qohash.cabaneio2021.model.post.Retweet
import com.qohash.cabaneio2021.model.post.Tweet
import com.qohash.cabaneio2021.model.user.User
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

const val MONGO = "mongo"

@Component
@Qualifier(MONGO)
class MongoInserter  : Inserter {
    override fun insert(model: TwitterModel) {
        TODO("Not yet implemented")
    }
}