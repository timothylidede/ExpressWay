package com.savala.expressway.model;

public class ModelBus {
    private String number_plate, route, time, price, seats, rating;

    public ModelBus(){}

    public ModelBus(String number_plate, String route, String time, String price, String seats, String rating) {
        this.number_plate = number_plate;
        this.route = route;
        this.time = time;
        this.price = price;
        this.seats = seats;
        this.rating = rating;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ModelBus{" +
                "number_plate='" + number_plate + '\'' +
                ", route='" + route + '\'' +
                ", time='" + time + '\'' +
                ", price='" + price + '\'' +
                ", seats='" + seats + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
