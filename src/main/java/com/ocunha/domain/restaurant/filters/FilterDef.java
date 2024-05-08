package com.ocunha.domain.restaurant.filters;

import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import org.apache.commons.lang3.StringUtils;

import static java.util.Objects.isNull;

@FunctionalInterface
public interface FilterDef {

    boolean filter(Restaurant restaurant, RestaurantSearchParams searchParams);

    default boolean isGreaterOrEquals(Integer current, Integer searchParam) {
        return isNull(searchParam) || searchParam.compareTo(current) >= 0;
    }

    default boolean isLessOrEquals(Integer current, Integer searchParam) {
        return isNull(searchParam) || searchParam.compareTo(current) <= 0;
    }

    default boolean startsWith(String current, String searchParam) {
        return isNull(searchParam) || StringUtils.startsWithIgnoreCase(current, searchParam);
    }

}
