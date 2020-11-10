package com.example.android.quakereport;

public class EarthQuake {

    private String location_offset;
    private String location_primary;
    private double magnitude;
    private String dateOfOccurrence;
    private String timeOfOccurrence;
    private String url;

    /**
     * Constructor
     * @param magnitude the magnitude of earthQuake
     * @param dateOfOccurrence the day when the earth quake occurred.
     * @param timeOfOccurrence the time of the earthQuake occurrence.
     * @param url the url of the earth quake .
     */
    public EarthQuake(String location_offset, String location_primary, double magnitude, String dateOfOccurrence, String timeOfOccurrence, String url) {
        this.location_offset = location_offset;
        this.location_primary = location_primary;
        this.magnitude = magnitude;
        this.dateOfOccurrence = dateOfOccurrence;
        this.timeOfOccurrence = timeOfOccurrence;
        this.url = url;
    }


    public String getLocation_offset() {
        return location_offset;
    }

    public String getLocation_primary() {
        return location_primary;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getDateOfOccurrence() {
        return dateOfOccurrence;
    }

    public String getTimeOfOccurrence() {
        return timeOfOccurrence;
    }

    public String getUrl() {
        return url;
    }
}
