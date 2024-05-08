package com.ocunha.domain.restaurant.filters;

import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class DistanceFilterDefinition implements FilterDefinition {

    @Override
    public boolean filter(Restaurant restaurant, RestaurantSearchParams searchParams) {
        return isLessOrEquals(restaurant.distance(), searchParams.getDistance());
    }

}
