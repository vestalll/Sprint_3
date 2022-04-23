package ru.yascooter.model;

public class Courier {

    //поля
    private String login;
    private String password;
    private String firstName;

    //конструктор без параметров
    public Courier() {
    }

    //конструктор со всеми параметрами
    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    //геттеры для полей
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    //сеттеры для полей
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}