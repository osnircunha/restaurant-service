package com.ocunha.domain.filter

import com.ocunha.domain.restaurant.model.Restaurant
import com.ocunha.domain.restaurant.model.RestaurantSearchParams
import org.springframework.stereotype.Component

@Component
class DistanceFilterDefinition : FilterDefinition {
    override fun filter(restaurant: Restaurant?, searchParams: RestaurantSearchParams?): Boolean {
        return isLessOrEquals(restaurant?.distance, searchParams?.distance)
    }

}