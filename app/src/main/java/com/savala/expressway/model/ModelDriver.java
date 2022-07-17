package com.savala.expressway.model;

public class ModelDriver {
    private String first_name, last_name, bus_manufacturer, year, number_plate,
            national_id, dl_ref_no, id_photo, profile_photo, dl_photo, ntsa_license,
            good_conduct, number_of_seats, rating;

    private ModelDriver(){}

    public ModelDriver(String first_name, String last_name, String bus_manufacturer, String year, String number_plate, String national_id, String dl_ref_no, String id_photo, String profile_photo, String dl_photo, String ntsa_license, String good_conduct, String number_of_seats, String rating) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.bus_manufacturer = bus_manufacturer;
        this.year = year;
        this.number_plate = number_plate;
        this.national_id = national_id;
        this.dl_ref_no = dl_ref_no;
        this.id_photo = id_photo;
        this.profile_photo = profile_photo;
        this.dl_photo = dl_photo;
        this.ntsa_license = ntsa_license;
        this.good_conduct = good_conduct;
        this.number_of_seats = number_of_seats;
        this.rating = rating;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBus_manufacturer() {
        return bus_manufacturer;
    }

    public void setBus_manufacturer(String bus_manufacturer) {
        this.bus_manufacturer = bus_manufacturer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getDl_ref_no() {
        return dl_ref_no;
    }

    public void setDl_ref_no(String dl_ref_no) {
        this.dl_ref_no = dl_ref_no;
    }

    public String getId_photo() {
        return id_photo;
    }

    public void setId_photo(String id_photo) {
        this.id_photo = id_photo;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getDl_photo() {
        return dl_photo;
    }

    public void setDl_photo(String dl_photo) {
        this.dl_photo = dl_photo;
    }

    public String getNtsa_license() {
        return ntsa_license;
    }

    public void setNtsa_license(String ntsa_license) {
        this.ntsa_license = ntsa_license;
    }

    public String getGood_conduct() {
        return good_conduct;
    }

    public void setGood_conduct(String good_conduct) {
        this.good_conduct = good_conduct;
    }

    public String getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(String number_of_seats) {
        this.number_of_seats = number_of_seats;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ModelDriver{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", bus_manufacturer='" + bus_manufacturer + '\'' +
                ", year='" + year + '\'' +
                ", number_plate='" + number_plate + '\'' +
                ", national_id='" + national_id + '\'' +
                ", dl_ref_no='" + dl_ref_no + '\'' +
                ", id_photo='" + id_photo + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", dl_photo='" + dl_photo + '\'' +
                ", ntsa_license='" + ntsa_license + '\'' +
                ", good_conduct='" + good_conduct + '\'' +
                ", number_of_seats='" + number_of_seats + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
