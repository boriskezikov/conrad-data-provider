package com.conrad.dataprovider.dto;

public enum CarType {

    AMBULANCE,
    FIRE_TRUCK,
    POLICE,
    CAR_WITH_FLASHER,
    COMMON;

    public static CarType getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
