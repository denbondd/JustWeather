package com.denbondd.justweather.services;

public class Keys {
    private static native String getOWMkey();
    public static String readOWMkey() {
        return getOWMkey();
    }
}
