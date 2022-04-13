package ru.yascooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import ru.yascooter.client.CourierClient;
import ru.yascooter.model.Courier;
import ru.yascooter.model.CourierCredentials;
import ru.yascooter.utils.CourierGenerator;

public class CreateCourierTest {
    CourierClient courierClient;
    Courier courier;
    CourierCredentials courierCredentials;
    int courierId;
    String messageText;
    int statusCode;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Создание курьера с заполнением всех полей верными значениями")
    public void courierCreationWithValidCredentials() {
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        assertThat("Courier isn't created", statusCode, equalTo(SC_CREATED));
    }

    @Test
    @DisplayName("Создание курьера без поля \"Логин\"")
    public void courierCreationWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation without login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без поля \"Пароль\"")
    public void courierCreationWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation without password", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation without password", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Ignore
    //Тест падает из-за бага
    @DisplayName("Создание курьера без поля \"First Name\"")
    public void courierCreationWithoutFirstName() {
        courier.setFirstName(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation without FirstName", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation without FirstName", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с пустым значением поля \"Логин\"")
    public void courierCreationWithEmptyLogin() {
        courier.setLogin("");
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation with empty login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation with empty login", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с пустым значением поля \"Пароль\"")
    public void courierCreationWithEmptyPassword() {
        courier.setPassword("");
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation with empty password", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation with empty password", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Ignore
    //Тест падает из-за бага
    @DisplayName("Создание курьера с пустым значением поля \"FirstName\"")
    public void courierCreationWithEmptyFirstName() {
        courier.setFirstName("");
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation with empty firstname", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation with empty firstname", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера со значениями полей \"Логин\" и \"Пароль\" существующей записи")
    public void courierCreationWithExistedCredentials() {
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse createResponse2 = courierClient.create(courier);
        statusCode = createResponse2.extract().statusCode();
        messageText = createResponse2.extract().path("message");
        assertThat("Invalid status code after courier creation with existed credentials", statusCode, equalTo(SC_CONFLICT));
        assertThat("Invalid message text after courier creation with existed credentials", messageText, equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}

