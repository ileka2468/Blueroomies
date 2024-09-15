package edu.depaul.cdm.se452.concept.base.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole   {
    @NonNull
    private String authority;
    
}
