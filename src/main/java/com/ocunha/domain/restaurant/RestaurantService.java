package com.ocunha.domain.restaurant;

import com.ocunha.configuration.ApplicationConfiguration;
import com.ocunha.domain.restaurant.filters.FilterDefinition;
import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import com.ocunha.infrastructure.files.csv.RestaurantsCsvReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final ApplicationConfiguration applicationConfiguration;
    private final RestaurantsCsvReader restaurantRepository;
    private final List<FilterDefinition> filterDefinitions;

    public List<Restaurant> findRestaurants(RestaurantSearchParams searchParams) {
        return restaurantRepository
                .getRestaurants()
                .stream()
                .filter(restaurant -> filterDefinitions
                        .stream()
                        .allMatch(filterDef -> filterDef.filter(restaurant, searchParams)))
                .sorted(restaurantComparator())
                .limit(applicationConfiguration.getApiResultSize())
                .toList();
    }

    private Comparator<Restaurant> restaurantComparator() {
        return (thisRestaurant, otherRestaurant) -> new CompareToBuilder()
                .append(thisRestaurant.distance(), otherRestaurant.distance())
                .append(otherRestaurant.rating(), thisRestaurant.rating())
                .append(thisRestaurant.price(), otherRestaurant.price())
                .append(thisRestaurant.cuisine(), otherRestaurant.cuisine())
                .append(thisRestaurant.name(), otherRestaurant.name())
                .toComparison();
    }

}
