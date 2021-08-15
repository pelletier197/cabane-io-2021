package com.qohash.cabaneio2021.inserter.arangodb

import com.qohash.cabaneio2021.inserter.Inserter
import com.qohash.cabaneio2021.inserter.TwitterModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

const val ARANGO_DB = "arangodb"

@Component
@Qualifier(ARANGO_DB)
class ArangoDbInserter : Inserter {
    override fun insert(model: TwitterModel) {
        TODO("Not yet implemented")
    }
}