package ru.yascooter.client;

import io.restassured.response.ValidatableResponse;
import ru.yascooter.model.Order;
import ru.yascooter.constants.ScooterApiEndpoint;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient{
    private static final String ORDER_PATH = ScooterApiEndpoint.orders;
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

    public ValidatableResponse cancelOrder(String track) {
        return given()
                .spec(getBaseSpec())
                .body(track)
                .when()
                .put(ORDER_PATH + "cancel")
                .then();
    }
}
