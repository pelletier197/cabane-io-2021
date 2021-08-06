package com.qohash.cabaneio2021.inserter

import com.qohash.cabaneio2021.generator.randomTwitterModel
import com.qohash.cabaneio2021.print.insertWithPrintedStatistics
import com.qohash.cabaneio2021.print.printSeparator
import com.qohash.cabaneio2021.print.printStatistics
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class DataInsertionService(
    private val applicationContext: ApplicationContext
) {
    fun insertData(
        usersCount: UInt,
        inserterNames: Set<String>,
    ) {
        val inserters = findInserters(inserterNames)
        val model = randomTwitterModel(usersCount)

        model.printStatistics()
        printSeparator()

        inserters.forEach {
            it.insertWithPrintedStatistics(model)
        }
    }

    private fun findInserters(inserterNames: Set<String>): List<Inserter> {
        // We call the application context directly because we run this application lazily,
        // which means it's possible a connection to a database is not yet established until now
        return inserterNames.map { name ->
            BeanFactoryAnnotationUtils.qualifiedBeanOfType(
                applicationContext.autowireCapableBeanFactory,
                Inserter::class.java,
                name
            )
        }
    }
}