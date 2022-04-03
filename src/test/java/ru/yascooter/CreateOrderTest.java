package ru.yascooter;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
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

        public CreateOrderTest(String [] scooterColor) {
            this.scooterColor = scooterColor;
        }

        @Parameterized.Parameters
        public static Object[][] getScooterColor() {
            return new Object[][]{
                    { new String[] {"BLACK"}},
                    { new String[] {"GREY"}},
                    { new String[] {"BLACK", "GREY"}},
                    { new String[] {}}
            };
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
        //Cоздание заказа
        public void orderCreation() {
            ValidatableResponse createResponse = courierClient.createOrder(order);
            int statusCode = createResponse.extract().statusCode();
            track = createResponse.extract().path("track");
            assertThat( "Invalid status code", statusCode, equalTo(SC_CREATED));
            assertThat("Track number isn't get",track ,is(not(0)));
        }

}
