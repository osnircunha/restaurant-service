package com.ocunha.infrastructure.files.csv;

import com.ocunha.domain.restaurant.model.model.Cuisine;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Component
public class RestaurantsCsvReader {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    @Value("classpath:csv/restaurants.csv")
    private Resource restaurantsCsvFile;
    @Value("classpath:csv/cuisines.csv")
    private Resource cuisinesCsvFile;

    private List<Restaurant> restaurants;

    public List<Restaurant> getRestaurants() {

        if (Objects.isNull(restaurants)) {
            loadCsvFiles();
        }

        return restaurants;
    }

    private void loadCsvFiles() {
        List<CSVRecord> restaurantsRecords = csvRecords(restaurantsCsvFile);
        List<CSVRecord> cuisineRecords = csvRecords(cuisinesCsvFile);

        List<Cuisine> cuisines = cuisineRecords.stream().map(cuisineRecord -> new Cuisine(
                Long.parseLong(cuisineRecord.get(ZERO)),
                cuisineRecord.get(ONE)
        )).toList();

        restaurants = restaurantsRecords.stream().map(restaurantsRecord ->
                new Restaurant(
                        restaurantsRecord.get(ZERO),
                        Integer.parseInt(restaurantsRecord.get(ONE)),
                        Integer.parseInt(restaurantsRecord.get(TWO)),
                        Integer.parseInt(restaurantsRecord.get(THREE)),
                        cuisines
                                .stream()
                                .filter(cuisine -> cuisine.id() == Long.parseLong(restaurantsRecord.get(FOUR)))
                                .findFirst()
                                .orElseThrow()
                                .name())
        ).toList();
    }

    private List<CSVRecord> csvRecords(Resource resource) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            return csvParser.getRecords();
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
