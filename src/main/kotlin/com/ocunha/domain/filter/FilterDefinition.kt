package com.ocunha.domain.filter

import com.ocunha.domain.restaurant.model.Restaurant
import com.ocunha.domain.restaurant.model.RestaurantSearchParams
import org.apache.commons.lang3.StringUtils
import java.util.Objects.isNull


interface FilterDefinition {

    fun filter(restaurant: Restaurant?, searchParams: RestaurantSearchParams?): Boolean

    fun isGreaterOrEquals(current: Int?, searchParam: Int?): Boolean {
        return isNull(searchParam) || current!! >= searchParam!!
    }

    fun isLessOrEquals(current: Int?, searchParam: Int?): Boolean {
        return isNull(searchParam) || current!! <= searchParam!!
    }

    fun contains(current: String?, searchParam: String?): Boolean {
        return isNull(searchParam) || StringUtils.containsIgnoreCase(current, searchParam)
    }
}