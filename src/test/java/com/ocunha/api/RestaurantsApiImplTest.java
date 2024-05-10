package com.ocunha.api;

import com.ocunha.domain.restaurant.model.model.Restaurant;
import com.ocunha.spec.api.ErrorResource;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestaurantsApiImplTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testGetRestaurantsSuccess() {
        ExtractableResponse<Response> response = given()
                .when()
                .get("/restaurants")
                .then()
                .statusCode(200)
                .and()
                .body(Matchers.notNullValue())
                .extract();

        assertThat(response.body().as(new TypeRef<List<Restaurant>>() {
        })).isNotEmpty()
                .hasSize(5);
    }

    @Test
    void testGetRestaurantsBadRequest() {
        ExtractableResponse<Response> response = given()
                .when()
                .get("/restaurants?rating=10")
                .then()
                .statusCode(400)
                .and()
                .body(Matchers.notNullValue())
                .extract();

        assertThat(response.body().as(new TypeRef<List<ErrorResource>>() {
        }))
                .isNotNull()
                .hasSize(1)
                .satisfiesExactly(
                        errorResource -> assertThat(errorResource)
                                .hasFieldOrPropertyWithValue("description", "must be less than or equal to 5")
                                .hasFieldOrPropertyWithValue("field", "rating")
                                .hasFieldOrPropertyWithValue("value", "10")
                );
    }

    @Test
    void testGetRestaurantsBadRequestInvalidParameter() {
        ExtractableResponse<Response> response = given()
                .when()
                .get("/restaurants?rating=null")
                .then()
                .statusCode(400)
                .and()
                .body(Matchers.notNullValue())
                .extract();

        assertThat(response.body().as(new TypeRef<List<ErrorResource>>() {
        }))
                .isNotNull()
                .hasSize(1)
                .satisfiesExactly(
                        errorResource -> assertThat(errorResource).hasFieldOrPropertyWithValue("description", "Must be class java.lang.Integer")
                                .hasFieldOrPropertyWithValue("field", "rating")
                                .hasFieldOrPropertyWithValue("value", "null")
                );
    }
}