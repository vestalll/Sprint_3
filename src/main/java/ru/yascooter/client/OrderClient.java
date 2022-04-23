package ru.yascooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yascooter.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient {
    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(String track) {
        return given()
                .spec(getBaseSpec())
                .body(track)
                .when()
                .put(ORDER_PATH + "cancel")
                .then();
    }
}
