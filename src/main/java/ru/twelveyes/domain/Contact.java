package ru.twelveyes.domain;

import java.util.List;

/**
 * Created by lameroot on 28.12.13.
 */
public class Contact {

    private Integer id;
    private String firstName;
    private String secondName;
    private MediaContent avatar;
    private List<MediaContent> contents;

    private String emails;
    private String phones;
    private String address;
    private double latitude;
    private double longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public MediaContent getAvatar() {
        return avatar;
    }

    public void setAvatar(MediaContent avatar) {
        this.avatar = avatar;
    }

    public List<MediaContent> getContents() {
        return contents;
    }

    public void setContents(List<MediaContent> contents) {
        this.contents = contents;
    }

}
