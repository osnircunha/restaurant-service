package com.ocunha.infrastructure.files.csv

import com.ocunha.domain.restaurant.model.Cuisine
import com.ocunha.domain.restaurant.model.Restaurant
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*


@Component
class RestaurantsCsvReader {
    private val ZERO = 0
    private val ONE = 1
    private val TWO = 2
    private val THREE = 3
    private val FOUR = 4

    @Value("classpath:csv/restaurants.csv")
    private val restaurantsCsvFile: Resource? = null

    @Value("classpath:csv/cuisines.csv")
    private val cuisinesCsvFile: Resource? = null

    private var restaurants: List<Restaurant?>? = null

    fun getRestaurants(): List<Restaurant?>? {
        if (Objects.isNull(restaurants)) {
            loadCsvFiles()
        }
        return restaurants
    }

    private fun loadCsvFiles() {
        val restaurantsRecords = csvRecords(restaurantsCsvFile)
        val cuisineRecords = csvRecords(cuisinesCsvFile)
        val cuisines: List<Cuisine> = cuisineRecords.map { cuisineRecord: CSVRecord ->
            Cuisine(
                cuisineRecord[ZERO].toLong(),
                cuisineRecord[ONE]
            )
        }.toList()
        restaurants = restaurantsRecords.map { restaurantsRecord: CSVRecord ->
            Restaurant(
                restaurantsRecord[ZERO],
                restaurantsRecord[ONE].toInt(),
                restaurantsRecord[TWO].toInt(),
                restaurantsRecord[THREE].toInt(),
                cuisines
                    .find { cuisine: Cuisine ->
                        cuisine.id == restaurantsRecord[FOUR].toLong()
                    }?.name
            )
        }.toList()
    }

    private fun csvRecords(resource: Resource?): List<CSVRecord> {
        try {
            BufferedReader(InputStreamReader(resource!!.inputStream, StandardCharsets.UTF_8)).use { fileReader ->
                CSVParser(
                    fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
                ).use { csvParser -> return csvParser.records }
            }
        } catch (e: IOException) {
            throw RuntimeException("fail to parse CSV file: " + e.message)
        }
    }
}