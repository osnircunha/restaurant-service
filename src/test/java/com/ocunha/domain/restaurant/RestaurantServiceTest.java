package com.ocunha.domain.restaurant;

import com.ocunha.configuration.ApplicationConfiguration;
import com.ocunha.domain.restaurant.filters.*;
import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import com.ocunha.infrastructure.files.csv.RestaurantsCsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    @Mock
    private ApplicationConfiguration applicationConfiguration;
    @Mock
    private RestaurantsCsvReader restaurantRepository;

    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        restaurantService = new RestaurantService(applicationConfiguration, restaurantRepository, Arrays.asList(
                new CuisineFilterDefinition(),
                new CustomerRatingFilterDefinition(),
                new DistanceFilterDefinition(),
                new PriceFilterDefinition(),
                new RestaurantNameFilterDefinition()
        ));
    }

    @Test
    void testFindRestaurantsWithNoParamsShouldReturnAllSorted() {
        RestaurantSearchParams searchParams = RestaurantSearchParams.builder().build();

        List<Restaurant> restaurants = getRestaurants();

        setUpCommonMocks(restaurants);

        List<Restaurant> result = restaurantService.findRestaurants(searchParams);

        assertThat(result)
                .hasSize(3)
                .satisfiesExactly(
                        restaurant -> assertThat(restaurant.name()).isEqualTo("Five Guys"),
                        restaurant -> assertThat(restaurant.name()).isEqualTo("TGIF"),
                        restaurant -> assertThat(restaurant.name()).isEqualTo("Steak'n Shake")
                );

    }

    @Test
    void testFindRestaurantsMaxDistance() {
        RestaurantSearchParams searchParams = RestaurantSearchParams.builder()
                .distance(20)
                .build();

        List<Restaurant> restaurants = getRestaurants();

        setUpCommonMocks(restaurants);

        List<Restaurant> result = restaurantService.findRestaurants(searchParams);

        assertThat(result)
                .hasSize(2)
                .satisfiesExactly(
                        restaurant -> assertThat(restaurant.name()).isEqualTo("Five Guys"),
                        restaurant -> assertThat(restaurant.name()).isEqualTo("TGIF")
                );
    }

    @Test
    void testFindRestaurantsMaxPrice() {
        RestaurantSearchParams searchParams = RestaurantSearchParams.builder()
                .price(15)
                .build();

        List<Restaurant> restaurants = getRestaurants();

        setUpCommonMocks(restaurants);

        List<Restaurant> result = restaurantService.findRestaurants(searchParams);

        assertThat(result)
                .hasSize(1)
                .satisfiesExactly(
                        restaurant -> assertThat(restaurant.name()).isEqualTo("Five Guys")
                );
    }

    @Test
    void testFindRestaurantsMinRating() {
        RestaurantSearchParams searchParams = RestaurantSearchParams.builder()
                .rating(3)
                .build();

        List<Restaurant> restaurants = getRestaurants();

        setUpCommonMocks(restaurants);

        List<Restaurant> result = restaurantService.findRestaurants(searchParams);

        assertThat(result)
                .hasSize(2)
                .satisfiesExactly(
                        restaurant -> assertThat(restaurant.name()).isEqualTo("Five Guys"),
                        restaurant -> assertThat(restaurant.name()).isEqualTo("Steak'n Shake")
                );
    }

    @Test
    void testFindRestaurantsNameContains() {
        RestaurantSearchParams searchParams = RestaurantSearchParams.builder()
                .name("Five")
                .build();

        List<Restaurant> restaurants = getRestaurants();

        setUpCommonMocks(restaurants);

        List<Restaurant> result = restaurantService.findRestaurants(searchParams);

        assertThat(result)
                .hasSize(1)
                .satisfiesExactly(
                        restaurant -> assertThat(restaurant.name()).isEqualTo("Five Guys")
                );
    }

    @Test
    void testFindRestaurantsCuisineContains() {
        RestaurantSearchParams searchParams = RestaurantSearchParams.builder()
                .cuisine("Brazilian")
                .build();

        List<Restaurant> restaurants = getRestaurants();

        setUpCommonMocks(restaurants);

        List<Restaurant> result = restaurantService.findRestaurants(searchParams);

        assertThat(result)
                .hasSize(1)
                .satisfiesExactly(
                        restaurant -> assertThat(restaurant.name()).isEqualTo("Steak'n Shake")
                );
    }

    private void setUpCommonMocks(List<Restaurant> restaurants) {
        when(restaurantRepository.getRestaurants()).thenReturn(restaurants);
        when(applicationConfiguration.getApiResultSize()).thenReturn(5);
    }

    private static List<Restaurant> getRestaurants() {
        return Arrays.asList(
                new Restaurant("TGIF", 1, 20, 20, "Canadian"),
                new Restaurant("Five Guys", 5, 15, 15, "American"),
                new Restaurant("Steak'n Shake", 3, 40, 25, "Brazilian")
        );
    }
}