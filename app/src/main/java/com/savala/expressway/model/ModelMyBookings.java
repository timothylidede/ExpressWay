package com.savala.expressway.model;

public class ModelMyBookings{

    private String departure_station, user_id, booking_id;

    public ModelMyBookings(){}

    public ModelMyBookings(String departure_station, String user_id, String booking_id) {
        this.departure_station = departure_station;
        this.user_id = user_id;
        this.booking_id = booking_id;
    }

    public String getDeparture_station() {
        return departure_station;
    }

    public void setDeparture_station(String departure_station) {
        this.departure_station = departure_station;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    @Override
    public String toString() {
        return "ModelMyBookings{" +
                "departure_station='" + departure_station + '\'' +
                ", user_id='" + user_id + '\'' +
                ", booking_id='" + booking_id + '\'' +
                '}';
    }
}
