package edu.depaul.cdm.se452.rfa.profileManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.depaul.cdm.se452.rfa.authentication.entity.User;
import edu.depaul.cdm.se452.rfa.profileManagement.entity.Profile;
import edu.depaul.cdm.se452.rfa.profileManagement.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    private Profile mockProfile;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1);

        mockProfile = new Profile();
        mockProfile.setId(1);
        mockProfile.setUser(user);
        mockProfile.setIsActivelyLooking(true);
        mockProfile.setBio("This is a sample bio.");
        mockProfile.setCharacteristics(Map.of("hobby", "reading", "skill", "coding"));
        mockProfile.setPfpImage("https://ui-avatars.com/api/?background=0D8ABC&color=fff");
    }

    @Test
    void testCreateProfile() throws Exception {
        when(profileService.saveProfile(any(Profile.class))).thenReturn(mockProfile);

        mockMvc.perform(post("/api/profiles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockProfile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isActivelyLooking").value(true))
                .andExpect(jsonPath("$.bio").value("This is a sample bio."))
                .andExpect(jsonPath("$.characteristics.hobby").value("reading"))
                .andExpect(jsonPath("$.pfpImage").value("https://ui-avatars.com/api/?background=0D8ABC&color=fff"));
    }

    @Test
    void testGetProfileById_Found() throws Exception {
        when(profileService.getProfileById(1)).thenReturn(Optional.of(mockProfile));

        mockMvc.perform(get("/api/profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isActivelyLooking").value(true))
                .andExpect(jsonPath("$.bio").value("This is a sample bio."))
                .andExpect(jsonPath("$.characteristics.hobby").value("reading"))
                .andExpect(jsonPath("$.pfpImage").value("https://ui-avatars.com/api/?background=0D8ABC&color=fff"));
    }

    @Test
    void testGetProfileById_NotFound() throws Exception {
        when(profileService.getProfileById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/profiles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProfileByUserId_Found() throws Exception {
        when(profileService.getProfileByUserId(1)).thenReturn(mockProfile);

        mockMvc.perform(get("/api/profiles/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isActivelyLooking").value(true))
                .andExpect(jsonPath("$.bio").value("This is a sample bio."))
                .andExpect(jsonPath("$.characteristics.hobby").value("reading"))
                .andExpect(jsonPath("$.pfpImage").value("https://ui-avatars.com/api/?background=0D8ABC&color=fff"));
    }

    @Test
    void testDeleteProfile() throws Exception {
        doNothing().when(profileService).deleteProfile(1);

        mockMvc.perform(delete("/api/profiles/1"))
                .andExpect(status().isNoContent());
    }
}
