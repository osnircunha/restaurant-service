package com.ocunha.domain.restaurant;

import com.ocunha.configuration.ApplicationConfiguration;
import com.ocunha.domain.restaurant.filters.FilterDef;
import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import com.ocunha.infrastructure.files.csv.RestaurantsCsvReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    public static final int INDEX_ZERO = 0;
    private final ApplicationConfiguration applicationConfiguration;
    private final RestaurantsCsvReader restaurantRepository;
    private final List<FilterDef> filterDefinitions;

    public List<Restaurant> findRestaurants(RestaurantSearchParams searchParams) {
        List<Restaurant> restaurants = restaurantRepository
                .getRestaurants().stream()
                .filter(restaurant -> filterDefinitions
                        .stream()
                        .allMatch(filterDef -> filterDef.filter(restaurant, searchParams)))
                .collect(Collectors.toList());

        sortRestaurants(restaurants);

        return cropListRecords(restaurants);
    }

    private List<Restaurant> cropListRecords(List<Restaurant> restaurants) {

        return restaurants.size() >= applicationConfiguration.getApiResultSize() ?
                restaurants.subList(INDEX_ZERO, applicationConfiguration.getApiResultSize()) :
                restaurants;
    }

    private void sortRestaurants(List<Restaurant> restaurants) {
        restaurants.sort((thisRestaurant, otherRestaurant) -> new CompareToBuilder()
                .append(thisRestaurant.distance(), otherRestaurant.distance())
                .append(otherRestaurant.rating(), thisRestaurant.rating())
                .append(thisRestaurant.price(), otherRestaurant.price())
                .append(thisRestaurant.cuisine(), otherRestaurant.cuisine())
                .append(thisRestaurant.name(), otherRestaurant.name())

                .toComparison());
    }

}
