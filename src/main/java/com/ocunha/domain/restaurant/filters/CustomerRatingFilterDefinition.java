package com.ocunha.domain.restaurant.filters;

import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class CustomerRatingFilterDefinition implements FilterDefinition {

    @Override
    public boolean filter(Restaurant restaurant, RestaurantSearchParams searchParams) {
        return isGreaterOrEquals(restaurant.rating(), searchParams.getRating());
    }

}
