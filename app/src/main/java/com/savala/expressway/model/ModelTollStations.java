package com.savala.expressway.model;

public class ModelTollStations {

    private String station_id;
    private String station_name;

    public ModelTollStations(){}

    public ModelTollStations(String station_id, String station_name) {
        this.station_id = station_id;
        this.station_name = station_name;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    @Override
    public String toString() {
        return "ModelTollStations{" +
                "station_id='" + station_id + '\'' +
                ", station_name='" + station_name + '\'' +
                '}';
    }
}
