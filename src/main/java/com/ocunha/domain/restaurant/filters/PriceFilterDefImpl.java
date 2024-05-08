package com.ocunha.domain.restaurant.filters;

import com.ocunha.domain.restaurant.model.RestaurantSearchParams;
import com.ocunha.domain.restaurant.model.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class PriceFilterDefImpl implements FilterDef {

    @Override
    public boolean filter(Restaurant restaurant, RestaurantSearchParams searchParams) {
        return isLessOrEquals(restaurant.price(), searchParams.getPrice());
    }

}
