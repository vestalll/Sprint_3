package ru.yascooter;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH = ScooterApiEndpoint.courier;
    private static final String ORDER_PATH = ScooterApiEndpoint.orders;

    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .body(courierId)
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }

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