package ru.yascooter.model;

public class CourierCredentials {

    private final String login;

    private final String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
