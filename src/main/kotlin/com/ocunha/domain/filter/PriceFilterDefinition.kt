package com.ocunha.domain.filter;

import com.ocunha.domain.restaurant.model.Restaurant
import com.ocunha.domain.restaurant.model.RestaurantSearchParams
import org.springframework.stereotype.Component

@Component
class PriceFilterDefinition : FilterDefinition {
    override fun filter(restaurant: Restaurant?, searchParams: RestaurantSearchParams?): Boolean {
        return isLessOrEquals(restaurant?.price, searchParams?.price);
    }
}
