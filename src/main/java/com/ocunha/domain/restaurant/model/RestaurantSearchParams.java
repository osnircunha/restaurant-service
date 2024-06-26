package com.ocunha.domain.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantSearchParams {
    private String name;

    private Integer rating;

    private Integer price;

    private Integer distance;

    private String cuisine;
}
