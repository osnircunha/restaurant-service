package com.ocunha.domain.restaurant.filters;

import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class DistanceFilterDefImpl implements FilterDef {

    @Override
    public boolean filter(Restaurant restaurant, RestaurantSearchParams search) {
        return isLessOrEquals(restaurant.distance(), search.getDistance());
    }

}
