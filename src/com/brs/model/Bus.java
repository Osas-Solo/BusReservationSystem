package com.brs.model;

import java.util.ArrayList;

public class Bus {

    private String busID;
    private int numberOfSeats;
    private ArrayList<Integer> seatNumbers;
    private Route route;

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public ArrayList<Integer> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = new ArrayList<>(numberOfSeats);
        String[] splitSeatNumbers = seatNumbers.split("-");

        for (int i = 0; i < numberOfSeats; i++) {
            this.seatNumbers.add(Integer.parseInt(splitSeatNumbers[i]));
        }
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Bus(String busID, int numberOfSeats, Route route) {
        setBusID(busID);
        setNumberOfSeats(numberOfSeats);
        setRoute(route);
    }   //  end of constructor

}   //  end of class
