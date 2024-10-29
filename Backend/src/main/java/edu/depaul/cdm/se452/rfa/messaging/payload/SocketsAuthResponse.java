package edu.depaul.cdm.se452.rfa.messaging.payload;

import edu.depaul.cdm.se452.rfa.authentication.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class SocketsAuthResponse {
    Integer id;
    String username;
    private Set<UserRole> userRoles;
}
