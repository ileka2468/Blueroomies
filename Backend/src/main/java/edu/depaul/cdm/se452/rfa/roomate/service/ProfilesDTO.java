package edu.depaul.cdm.se452.rfa.roomate.service;

import java.util.Map;

public class ProfilesDTO {
    private String name;
    private String bio;
    private Map<String, Object> characteristics;

    public ProfilesDTO(String name, String bio, Map<String, Object> characteristics) {
        this.name = name;
        this.bio = bio;
        this.characteristics = characteristics;
    }
}
