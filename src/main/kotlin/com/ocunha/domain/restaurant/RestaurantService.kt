package com.ocunha.domain.restaurant

import com.ocunha.configuration.ApplicationConfiguration
import com.ocunha.domain.filter.FilterDefinition
import com.ocunha.domain.restaurant.model.Restaurant
import com.ocunha.domain.restaurant.model.RestaurantSearchParams
import com.ocunha.infrastructure.files.csv.RestaurantsCsvReader
import org.apache.commons.lang3.builder.CompareToBuilder
import org.springframework.stereotype.Service

@Service
class RestaurantService(
    private val applicationConfiguration: ApplicationConfiguration,
    private val restaurantRepository: RestaurantsCsvReader,
    private val filterDefinitions: List<FilterDefinition>
) {

    fun findRestaurants(searchParams: RestaurantSearchParams?): List<Restaurant?>? {
        return restaurantRepository
            .getRestaurants()
            ?.filter { restaurant ->
                filterDefinitions
                    .stream()
                    .allMatch { it.filter(restaurant, searchParams) }
            }
            ?.sortedWith(restaurantComparator())
            ?.take(applicationConfiguration.getApiResultSize())
    }

    private fun restaurantComparator(): Comparator<Restaurant?> {
        return Comparator { thisRestaurant, otherRestaurant ->
            CompareToBuilder()
                .append(thisRestaurant!!.distance, otherRestaurant!!.distance)
                .append(otherRestaurant.rating, thisRestaurant.rating)
                .append(thisRestaurant.price, otherRestaurant.price)
                .append(thisRestaurant.cuisine, otherRestaurant.cuisine)
                .append(thisRestaurant.name, otherRestaurant.name)
                .toComparison()
        }
    }

}