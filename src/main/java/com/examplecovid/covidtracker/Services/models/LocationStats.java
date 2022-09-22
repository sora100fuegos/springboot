package com.examplecovid.covidtracker.Services.models;

public class LocationStats {

    private String State ;
    private String Country;
    private int latestTotalCases;

    public int getDiffPreviousDays() {
        return diffPreviousDays;
    }

    public void setDiffPreviousDays(int diffPreviousDays) {
        this.diffPreviousDays = diffPreviousDays;
    }

    private int diffPreviousDays;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "State='" + State + '\'' +
                ", Country='" + Country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }
}
