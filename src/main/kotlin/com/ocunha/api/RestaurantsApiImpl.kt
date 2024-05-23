package com.ocunha.api

import com.ocunha.domain.restaurant.RestaurantService
import com.ocunha.domain.restaurant.model.Restaurant
import com.ocunha.domain.restaurant.model.RestaurantSearchParams
import com.ocunha.spec.api.RestaurantResource
import com.ocunha.spec.api.RestaurantsApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController


@RestController
class RestaurantsApiImpl(private val restaurantService: RestaurantService) : RestaurantsApi {

    override fun getRestaurants(
        name: String?,
        rating: Int?,
        distance: Int?,
        price: Int?,
        cuisine: String?
    ): ResponseEntity<List<RestaurantResource>> {
        val restaurants = restaurantService.findRestaurants(
            RestaurantSearchParams.Builder()
                .name(name)
                .rating(rating)
                .distance(distance)
                .price(price)
                .cuisine(cuisine)
                .build()
        )
        return ResponseEntity.ok(restaurants?.map { restaurant -> getRestaurantResource(restaurant) });
    }

    private fun getRestaurantResource(restaurant: Restaurant?): RestaurantResource {
        val restaurantResource = RestaurantResource()
        restaurantResource.name = restaurant?.name
        restaurantResource.cuisine = restaurant?.cuisine
        restaurantResource.price = restaurant?.price
        restaurantResource.distance = restaurant?.distance
        restaurantResource.rating = restaurant?.rating
        return restaurantResource
    }
}