package com.example.websitetrivia.domain.model;


public class Website {
    private String id;
    private String name;
    private int foundingYear;
    private String founders;
    private String location;
    private String CEO;
    private int rank;
    private String timeSpent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Website(String id, String name, int foundingYear, String founders, String location, String CEO, int rank, String timeSpent) {
        this.id = id;
        this.name = name;

        this.foundingYear = foundingYear;
        this.founders = founders;
        this.location = location;
        this.CEO = CEO;
        this.rank = rank;
        this.timeSpent = timeSpent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFoundingYear() {
        return foundingYear;
    }

    public void setFoundingYear(int foundingYear) {
        this.foundingYear = foundingYear;
    }

    public String getFounders() {
        return founders;
    }

    public void setFounders(String founders) {
        this.founders = founders;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCEO() {
        return CEO;
    }

    public void setCEO(String CEO) {
        this.CEO = CEO;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }
}
