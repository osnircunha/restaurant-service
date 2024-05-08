package com.ocunha.domain.restaurant;

import com.ocunha.configuration.ApplicationConfiguration;
import com.ocunha.domain.restaurant.filters.FilterDef;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    @Mock
    private ApplicationConfiguration applicationConfiguration;
    @Mock
    private RestaurantsCsvReader restaurantRepository;
    @Mock
    private FilterDef filterDef;

    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        restaurantService = new RestaurantService(applicationConfiguration, restaurantRepository, Arrays.asList(filterDef));
    }

    @Test
    void testFindRestaurants() {
        RestaurantSearchParams searchParams = new RestaurantSearchParams();

        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant("TGIF", 1, 15, 20, "Canadian"),
                new Restaurant("Five Guys", 5, 20, 15, "American"),
                new Restaurant("Steak'n Shake", 3, 40, 25, "Brazilian")
        );

        when(restaurantRepository.getRestaurants()).thenReturn(restaurants);
        when(applicationConfiguration.getApiResultSize()).thenReturn(5);

        when(filterDef.filter(restaurants.get(0), searchParams)).thenReturn(true);
        when(filterDef.filter(restaurants.get(1), searchParams)).thenReturn(true);
        when(filterDef.filter(restaurants.get(2), searchParams)).thenReturn(true);

        List<Restaurant> result = restaurantService.findRestaurants(searchParams);

        assertEquals(3, result.size());
        assertEquals("Five Guys", result.get(0).name());
        assertEquals("TGIF", result.get(1).name());
        assertEquals("Steak'n Shake", result.get(2).name());
    }

    @Test
    void testFindRestaurantsCroppingResult() {
        RestaurantSearchParams searchParams = new RestaurantSearchParams();

        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant("TGIF", 1, 15, 20, "Canadian"),
                new Restaurant("Five Guys", 5, 20, 15, "American"),
                new Restaurant("Steak'n Shake", 3, 40, 25, "Brazilian")
        );

        when(restaurantRepository.getRestaurants()).thenReturn(restaurants);
        when(applicationConfiguration.getApiResultSize()).thenReturn(2);

        when(filterDef.filter(restaurants.get(0), searchParams)).thenReturn(true);
        when(filterDef.filter(restaurants.get(1), searchParams)).thenReturn(true);
        when(filterDef.filter(restaurants.get(2), searchParams)).thenReturn(true);

        List<Restaurant> result = restaurantService.findRestaurants(searchParams);

        assertEquals(2, result.size());
        assertEquals("Five Guys", result.get(0).name());
        assertEquals("TGIF", result.get(1).name());
    }
}