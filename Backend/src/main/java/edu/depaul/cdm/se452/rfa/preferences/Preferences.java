package edu.depaul.cdm.se452.rfa.preferences;
import static edu.depaul.cdm.se452.rfa.preferences.PreferenceType.binary;
import static edu.depaul.cdm.se452.rfa.preferences.PreferenceType.categorical;
import static edu.depaul.cdm.se452.rfa.preferences.PreferenceType.scalar;

import java.util.HashMap;
import java.util.Map;


public class Preferences {
    private String genderPreference;
    private boolean smokingPreference;
    private boolean alcoholUsage;
    private int cleanlinessLevel; // 1-5 scale
    private int noiseTolerance;   // 1-5 scale
    private int hygiene;          // 1-5 scale
    private String sleepSchedule;
    private int guestsVisitors;   // 1-5 scale
    private int workStudyFromHome; // 1-5 scale
    private int petTolerance;     // 1-5 scale
    private int sharedExpenses;   // 1-5 scale
    private int studyHabits;      // 1-5 scale
    private int roomPrivacy;      // 1-5 scale
    private int cookingFrequency; // 1-5 scale
    private boolean foodSharing;
    private int exerciseFrequency; // 1-5 scale
    private String personalityType;
    private int sharedLivingSpaceUse; // 1-5 scale
    private int roomTemperaturePreference; // 1-5 scale
    private String decoratingStyle;

    public Preferences() {
        this("No Preference", false, false, 3, 3, 3, "Flexible", 3, 3, 3, 3, 3, 3, 3, false, 3, "Ambivert", 3, 3, "Modern");
    }


    public Preferences(String genderPreference, boolean smokingPreference, boolean alcoholUsage, int cleanlinessLevel,
                               int noiseTolerance, int hygiene, String sleepSchedule, int guestsVisitors, int workStudyFromHome,
                               int petTolerance, int sharedExpenses, int studyHabits, int roomPrivacy, int cookingFrequency,
                               boolean foodSharing, int exerciseFrequency, String personalityType, int sharedLivingSpaceUse,
                               int roomTemperaturePreference, String decoratingStyle) {
        this.genderPreference = genderPreference;
        this.smokingPreference = smokingPreference;
        this.alcoholUsage = alcoholUsage;
        this.cleanlinessLevel = cleanlinessLevel;
        this.noiseTolerance = noiseTolerance;
        this.hygiene = hygiene;
        this.sleepSchedule = sleepSchedule;
        this.guestsVisitors = guestsVisitors;
        this.workStudyFromHome = workStudyFromHome;
        this.petTolerance = petTolerance;
        this.sharedExpenses = sharedExpenses;
        this.studyHabits = studyHabits;
        this.roomPrivacy = roomPrivacy;
        this.cookingFrequency = cookingFrequency;
        this.foodSharing = foodSharing;
        this.exerciseFrequency = exerciseFrequency;
        this.personalityType = personalityType;
        this.sharedLivingSpaceUse = sharedLivingSpaceUse;
        this.roomTemperaturePreference = roomTemperaturePreference;
        this.decoratingStyle = decoratingStyle;
        ValidateCharacteristics();
    }



    public Map<String, Object> toMap() {
        Map<String, Object> preferencesMap = new HashMap<>();
        preferencesMap.put("gender_preference", genderPreference);
        preferencesMap.put("smoking_preference", smokingPreference);
        preferencesMap.put("alcohol_usage", alcoholUsage);
        preferencesMap.put("cleanliness_level", cleanlinessLevel);
        preferencesMap.put("noise_tolerance", noiseTolerance);
        preferencesMap.put("hygiene", hygiene);
        preferencesMap.put("sleep_schedule", sleepSchedule);
        preferencesMap.put("guests_visitors", guestsVisitors);
        preferencesMap.put("work_study_from_home", workStudyFromHome);
        preferencesMap.put("pet_tolerance", petTolerance);
        preferencesMap.put("shared_expenses", sharedExpenses);
        preferencesMap.put("study_habits", studyHabits);
        preferencesMap.put("room_privacy", roomPrivacy);
        preferencesMap.put("cooking_frequency", cookingFrequency);
        preferencesMap.put("food_sharing", foodSharing);
        preferencesMap.put("exercise_frequency", exerciseFrequency);
        preferencesMap.put("personality_type", personalityType);
        preferencesMap.put("shared_living_space_use", sharedLivingSpaceUse);
        preferencesMap.put("room_temperature_preference", roomTemperaturePreference);
        preferencesMap.put("decorating_style", decoratingStyle);
        return preferencesMap;
    }


    @Override
    public String toString() {
        return "RoommatePreferences{" +
                "genderPreference='" + genderPreference + '\'' +
                ", smokingPreference=" + smokingPreference +
                ", alcoholUsage=" + alcoholUsage +
                ", cleanlinessLevel=" + cleanlinessLevel +
                ", noiseTolerance=" + noiseTolerance +
                ", hygiene=" + hygiene +
                ", sleepSchedule='" + sleepSchedule + '\'' +
                ", guestsVisitors=" + guestsVisitors +
                ", workStudyFromHome=" + workStudyFromHome +
                ", petTolerance=" + petTolerance +
                ", sharedExpenses=" + sharedExpenses +
                ", studyHabits=" + studyHabits +
                ", roomPrivacy=" + roomPrivacy +
                ", cookingFrequency=" + cookingFrequency +
                ", foodSharing=" + foodSharing +
                ", exerciseFrequency=" + exerciseFrequency +
                ", personalityType='" + personalityType + '\'' +
                ", sharedLivingSpaceUse=" + sharedLivingSpaceUse +
                ", roomTemperaturePreference=" + roomTemperaturePreference +
                ", decoratingStyle='" + decoratingStyle + '\'' +
                '}';
    }


    public static void main(String[] args) {
        Preferences preferences = new Preferences(
                "No Preference", false, false, 5, 4, 5, "Night Owl", 3, 2, 5, 4, 3, 4, 2, false, 4, "Ambivert", 3, 3, "Modern");

        preferences.getSleepSchedule();
        System.out.println(preferences.toString());

        Map<String, Object> preferencesMap = preferences.toMap();
        System.out.println(preferencesMap);
    }


    public PreferenceDataWithOptions<String> getGenderPreference() {
        return new PreferenceDataWithOptions<>(genderPreference, categorical, new String[]{"No Preference", "Male", "Female", "Non-Binary"});
    }

    public void setGenderPreference(String genderPreference) {
        this.genderPreference = genderPreference;
    }

    public PreferenceData<Boolean> getSmokingPreference() {
        return new PreferenceData<>(smokingPreference, binary);
    }

    public void setSmokingPreference(boolean smokingPreference) {
        this.smokingPreference = smokingPreference;
    }

    public PreferenceData<Boolean> getAlcoholUsage() {
        return new PreferenceData<>(alcoholUsage, binary);
    }

    public void setAlcoholUsage(boolean alcoholUsage) {
        this.alcoholUsage = alcoholUsage;
    }

    public PreferenceData<Integer> getCleanlinessLevel() {
        return new PreferenceData<>(cleanlinessLevel, scalar);
    }

    public void setCleanlinessLevel(int cleanlinessLevel) {
        this.cleanlinessLevel = cleanlinessLevel;
    }

    public PreferenceData<Integer> getNoiseTolerance() {
        return new PreferenceData<>(noiseTolerance, scalar);
    }

    public void setNoiseTolerance(int noiseTolerance) {
        this.noiseTolerance = noiseTolerance;
    }

    public PreferenceData<Integer> getHygiene() {
        return new PreferenceData<>(hygiene, scalar);
    }

    public void setHygiene(int hygiene) {
        this.hygiene = hygiene;
    }

    public PreferenceDataWithOptions<String> getSleepSchedule() {
        return new PreferenceDataWithOptions<>(sleepSchedule, categorical, new String[]{"Early Riser", "Night Owl", "Flexible"});
    }

    public void setSleepSchedule(String sleepSchedule) {
        this.sleepSchedule = sleepSchedule;
    }

    public PreferenceData<Integer> getGuestsVisitors() {
        return new PreferenceData<>(guestsVisitors, scalar);
    }

    public void setGuestsVisitors(int guestsVisitors) {
        this.guestsVisitors = guestsVisitors;
    }

    public PreferenceData<Integer> getWorkStudyFromHome() {
        return new PreferenceData<>(workStudyFromHome, scalar);
    }

    public void setWorkStudyFromHome(int workStudyFromHome) {
        this.workStudyFromHome = workStudyFromHome;
    }

    public PreferenceData<Integer> getPetTolerance() {
        return new PreferenceData<>(petTolerance, scalar);
    }

    public void setPetTolerance(int petTolerance) {
        this.petTolerance = petTolerance;
    }

    public PreferenceData<Integer> getSharedExpenses() {
        return new PreferenceData<>(sharedExpenses, scalar);
    }

    public void setSharedExpenses(int sharedExpenses) {
        this.sharedExpenses = sharedExpenses;
    }

    public PreferenceData<Integer> getStudyHabits() {
        return new PreferenceData<>(studyHabits, scalar);
    }

    public void setStudyHabits(int studyHabits) {
        this.studyHabits = studyHabits;
    }

    public PreferenceData<Integer> getRoomPrivacy() {
        return new PreferenceData<>(roomPrivacy, scalar);
    }

    public void setRoomPrivacy(int roomPrivacy) {
        this.roomPrivacy = roomPrivacy;
    }

    public PreferenceData<Integer> getCookingFrequency() {
        return new PreferenceData<>(cookingFrequency, scalar);
    }

    public void setCookingFrequency(int cookingFrequency) {
        this.cookingFrequency = cookingFrequency;
    }

    public PreferenceData<Boolean> getFoodSharing() {
        return new PreferenceData<>(foodSharing, binary);
    }

    public void setFoodSharing(boolean foodSharing) {
        this.foodSharing = foodSharing;
    }

    public PreferenceData<Integer> getExerciseFrequency() {
        return new PreferenceData<>(exerciseFrequency, scalar);
    }

    public void setExerciseFrequency(int exerciseFrequency) {
        this.exerciseFrequency = exerciseFrequency;
    }

    public PreferenceDataWithOptions<String> getPersonalityType() {
        return new PreferenceDataWithOptions<>(personalityType, categorical, new String[]{"Introverted", "Extroverted", "Ambivert"});
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    public PreferenceData<Integer> getSharedLivingSpaceUse() {
        return new PreferenceData<>(sharedLivingSpaceUse, scalar);
    }

    public void setSharedLivingSpaceUse(int sharedLivingSpaceUse) {
        this.sharedLivingSpaceUse = sharedLivingSpaceUse;
    }

    public PreferenceData<Integer> getRoomTemperaturePreference() {
        return new PreferenceData<>(roomTemperaturePreference, scalar);
    }

    public void setRoomTemperaturePreference(int roomTemperaturePreference) {
        this.roomTemperaturePreference = roomTemperaturePreference;
    }

    public PreferenceDataWithOptions<String> getDecoratingStyle() {
        return new PreferenceDataWithOptions<>(decoratingStyle, categorical, new String[]{"Minimalist", "Cozy", "Modern", "Traditional"});
    }

    public void setDecoratingStyle(String decoratingStyle) {
        this.decoratingStyle = decoratingStyle;
    }

    public void ValidateCharacteristics() throws InvalidPreferenceException{

            // call all getters
            getGenderPreference();
            getSmokingPreference();
            getAlcoholUsage();
            getCleanlinessLevel();
            getNoiseTolerance();
            getHygiene();
            getSleepSchedule();
            getGuestsVisitors();
            getWorkStudyFromHome();
            getPetTolerance();
            getSharedExpenses();
            getStudyHabits();
            getRoomPrivacy();
            getCookingFrequency();
            getFoodSharing();
            getExerciseFrequency();
            getPersonalityType();
            getSharedLivingSpaceUse();
            getRoomTemperaturePreference();
            getDecoratingStyle();
    }

}
