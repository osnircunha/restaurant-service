package com.ocunha.domain.restaurant.model

class RestaurantSearchParams(
    val name: String?,
    val rating: Int?,
    val price: Int?,
    val distance: Int?,
    val cuisine: String?,
) {
    data class Builder(
        var name: String? = null,
        var rating: Int? = null,
        var price: Int? = null,
        var distance: Int? = null,
        var cuisine: String? = null,
    ) {
        fun name(name: String?) = apply { this.name = name }
        fun rating(rating: Int?) = apply { this.rating = rating }
        fun price(price: Int?) = apply { this.price = price }
        fun distance(distance: Int?) = apply { this.distance = distance }
        fun cuisine(cuisine: String?) = apply { this.cuisine = cuisine }
        fun build() = RestaurantSearchParams(name, rating, price, distance, cuisine)
    }
}