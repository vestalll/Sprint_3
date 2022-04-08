package ru.yascooter;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    CourierClient courierClient;
    Order order;
    Integer track;
    private final String[] scooterColor;

    public CreateOrderTest(String[] scooterColor) {
        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}{1}{2}{3}")
    public static List<Object[]> getScooterColor() {

        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        });
    }


    @Before
    public void setUp() {
        courierClient = new CourierClient();
        order = new Order(scooterColor);
    }

    @After
    public void tearDown() {
        courierClient.cancelOrder(track.toString());
    }

    @Test
    @DisplayName("Создание заказа")
    @Step("Код ответа 201. Трек-номер получен")
    public void orderCreation() {
        ValidatableResponse createResponse = courierClient.createOrder(order);
        int statusCode = createResponse.extract().statusCode();
        track = createResponse.extract().path("track");
        assertThat("Invalid status code after order creation", statusCode, equalTo(SC_CREATED));
        assertThat("Track number is absent after order creation", track, is(notNullValue()));
    }
}
