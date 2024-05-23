package com.ocunha.domain.filter

import com.ocunha.domain.restaurant.model.Restaurant
import com.ocunha.domain.restaurant.model.RestaurantSearchParams
import org.springframework.stereotype.Component

@Component
class RestaurantNameFilterDefinition : FilterDefinition {
    override fun filter(restaurant: Restaurant?, searchParams: RestaurantSearchParams?): Boolean {
        return contains(restaurant?.name, searchParams?.name);
    }
}