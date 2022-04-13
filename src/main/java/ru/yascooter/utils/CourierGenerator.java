package ru.yascooter.utils;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yascooter.model.Courier;

public class CourierGenerator {

    public static Courier getRandom() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }
}