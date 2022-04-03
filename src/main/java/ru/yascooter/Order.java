package ru.yascooter;


    public class Order {
        private final String firstName = "Илон";
        private final String lastName = "Маск";
        private final String address = "Московская, 1";
        private final int metroStation = 4;
        private final String phone = "+71111111111";
        private final int rentTime = 4;
        private final String deliveryDate = "2022-04-01";
        private final String comment = "Как можно быстрее, пожалуйста, на 1-2-3";
        private String[] scooterColor;

        public Order() {
        }

        public Order(String[] scooterColor) {

            this.scooterColor = scooterColor;
        }

        public String[] getScooterColor() {
            return scooterColor;
        }

        public void setScooterColor(String[] scooterColor) {
            this.scooterColor = scooterColor;
        }

    }

