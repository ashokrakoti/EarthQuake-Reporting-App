package com.example.android.quakereport;

public class EarthQuake {

    private String location_offset;
    private String location_primary;
    private double magnitude;
    private String dateOfOccurrence;
    private String timeOfOccurrence;

    /**
     * Constructor
     * @param magnitude the magnitude of earthQuake
     * @param dateOfOccurrence the day when the earth quake occurred.
     * @param timeOfOccurrence the time of the earthQuake occurrence.
     */
    public EarthQuake(String location_offset, String location_primary, double magnitude, String dateOfOccurrence, String timeOfOccurrence) {
        this.location_offset = location_offset;
        this.location_primary = location_primary;
        this.magnitude = magnitude;
        this.dateOfOccurrence = dateOfOccurrence;
        this.timeOfOccurrence = timeOfOccurrence;
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
}
