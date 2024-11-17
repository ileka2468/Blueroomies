package edu.depaul.cdm.se452.rfa.preferences;

import static edu.depaul.cdm.se452.rfa.preferences.PreferenceType.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreferenceData<Type> {
    private Type value;
    private PreferenceType type;

    public PreferenceData(Type value, PreferenceType type) {
        if (type.equals(scalar) && !value.getClass().equals(Integer.class)) {
            throw new InvalidPreferenceException("Invalid scalar type");
        } else if (type.equals(binary) && !value.getClass().equals(Boolean.class)) {
            throw new InvalidPreferenceException("Invalid binary type");
        } else if (type.equals(categorical) && !value.getClass().equals(String.class)) {
            throw new InvalidPreferenceException("Invalid categorical type");
        }

        this.value = value;
        this.type = type;
    }
}
