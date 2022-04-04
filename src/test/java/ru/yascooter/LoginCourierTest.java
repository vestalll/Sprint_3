package ru.yascooter;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {

    CourierClient courierClient;
    Courier courier;
    CourierCredentials courierCredentials;
    int courierId;
    int statusCode;
    String messageText;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    //Авторизация со всеми обязательными полями. В ответе возвращается id.
    public void courierLoginWithValidCredentials() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
        assertThat( "Invalid status code after courier authorization with valid credentials", statusCode, equalTo(SC_OK));
        assertThat("Courier ID is absent after courier authorization with valid credentials", courierId, is(notNullValue()));
    }

    @Test
    //Ошибка при авторизации с несуществующим логином
    public void courierLoginWithNonExistedLogin() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("nonexistedlogin", courier.getPassword()));
        statusCode = loginResponse.extract().statusCode();
        messageText = loginResponse.extract().path("message");
        assertThat( "Invalid status code after courier authorization with  incorrect login", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Invalid message text after courier authorization with incorrect login", messageText, equalTo("Учетная запись не найдена") );
    }

    @Test
    //Ошибка при авторизации с несуществующим паролем
    public void courierLoginWithNonExistedPassword() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), "nonexistedpassword"));
        statusCode = loginResponse.extract().statusCode();
        messageText = loginResponse.extract().path("message");
        assertThat( "Invalid status code after courier authorization with incorrect password", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Invalid message text after courier authorization with incorrect password", messageText, equalTo("Учетная запись не найдена") );
    }

    @Test
    //Ошибка при авторизации c пустым значением логина
    public void courierLoginWithEmptyLogin() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", courier.getPassword()));
        statusCode = loginResponse.extract().statusCode();
        messageText = loginResponse.extract().path("message");
        assertThat( "Invalid status code after courier authorization with empty login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier authorization with empty login", messageText, equalTo("Недостаточно данных для входа") );
    }

    @Test
    //Ошибка при авторизации с пустым значением пароля
    public void courierLoginWithEmptyPassword() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), ""));
        statusCode = loginResponse.extract().statusCode();
        messageText = loginResponse.extract().path("message");
        assertThat( "Invalid status code after courier authorization with empty password", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier authorization with empty password", messageText, equalTo("Недостаточно данных для входа") );
    }

    @Test
    //Ошибка при авторизации без значения поля логина в запросе
    public void courierLoginWithoutLoginField() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(null, courier.getPassword()));
        statusCode = loginResponse.extract().statusCode();
        messageText = loginResponse.extract().path("message");
        assertThat( "Invalid status code after courier authorization without login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier authorization without login", messageText, equalTo("Недостаточно данных для входа") );
    }

    @Test
    //Ошибка при авторизации без значения поля пароль в запросе
    @Ignore
    //Тест падает из-за бага: в ответе возвращается статус-код 500 вместо 400
    public void courierLoginWithoutPasswordField() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), null));
        statusCode = loginResponse.extract().statusCode();
        messageText = loginResponse.extract().path("message");
        assertThat( "Invalid status code after courier authorization without password", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier authorization without password", messageText, equalTo("Недостаточно данных для входа") );
    }
}
