package edu.depaul.cdm.se452.rfa.preferences;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class PreferenceDataWithOptions<Type> extends PreferenceData<Type> {
    public PreferenceDataWithOptions(Type value, PreferenceType type, String[] options) {
        super(value, type);
        if (options == null || options.length == 0) {
            throw new InvalidPreferenceException("No options provided");
        }
        boolean validOption = Arrays.stream(options).anyMatch(option -> option.equals(value));

        if (!validOption) {
            throw new InvalidPreferenceException("Invalid option provided");
        }

        this.options = options;
    }
    private String[] options;
}
