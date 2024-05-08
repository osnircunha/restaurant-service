package com.ocunha.api;

import com.ocunha.domain.restaurant.RestaurantService;
import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantsApiImpl implements RestaurantsApi {

    private final RestaurantService restaurantService;

    @Override
    public ResponseEntity<List<RestaurantResource>> getRestaurants(String name, Integer rating, Integer distance, Integer price, String cuisine) {
        RestaurantSearchParams searchParams = RestaurantSearchParams.builder()
                .name(name)
                .rating(rating)
                .distance(distance)
                .price(price)
                .cuisine(cuisine)
                .build();

        return ResponseEntity.ok(restaurantService.findRestaurants(searchParams)
                .stream().map(this::getRestaurantResource).toList());
    }

    private RestaurantResource getRestaurantResource(Restaurant restaurant) {
        return RestaurantResource.builder()
                .name(restaurant.name())
                .cuisine(restaurant.cuisine())
                .price(String.valueOf(restaurant.price()))
                .distance(String.valueOf(restaurant.distance()))
                .rating(String.valueOf(restaurant.rating()))
                .build();
    }


}
