package ru.yascooter;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetOrderListTest {
    CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }
    @Test
    public void getOrderList() {
        ValidatableResponse orderListResponse = courierClient.getOrderList();
        int statusCode = orderListResponse.extract().statusCode();
        ArrayList<String> ordersList= orderListResponse.extract().path("orders");
        assertThat( "Invalid status code", statusCode, equalTo(SC_OK));
        assertThat("Orders are absent in the list", ordersList, is(notNullValue()));

    }
}
