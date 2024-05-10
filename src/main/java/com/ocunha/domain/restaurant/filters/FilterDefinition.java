package com.ocunha.domain.restaurant.filters;

import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import org.apache.commons.lang3.StringUtils;

import static java.util.Objects.isNull;

@FunctionalInterface
public interface FilterDefinition {

    boolean filter(Restaurant restaurant, RestaurantSearchParams searchParams);

    default boolean isGreaterOrEquals(Integer current, Integer searchParam) {
        return isNull(searchParam) || current.compareTo(searchParam) >= 0;
    }

    default boolean isLessOrEquals(Integer current, Integer searchParam) {
        return isNull(searchParam) || current.compareTo(searchParam) <= 0;
    }

    default boolean contains(String current, String searchParam) {
        return isNull(searchParam) || StringUtils.containsIgnoreCase(current, searchParam);
    }

}
