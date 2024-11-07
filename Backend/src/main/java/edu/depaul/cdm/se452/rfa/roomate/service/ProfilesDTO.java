package edu.depaul.cdm.se452.rfa.roomate.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ProfilesDTO {
    private String name;
    private String bio;
    private Map<String, Object> characteristics;

}
