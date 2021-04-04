package com.brs.model;

public class Route {

    private String routeID;
    private String station;
    private String destination;
    private double fare;

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public Route(String routeID, String station, String destination, double fare) {
        setRouteID(routeID);
        setStation(station);
        setDestination(destination);
        setFare(fare);
    }   //  end of constructor

}   //  end of class
