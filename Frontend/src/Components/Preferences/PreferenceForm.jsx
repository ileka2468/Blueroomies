import React, { useState } from 'react';
import { Input, Checkbox, Button, Slider } from "@nextui-org/react";

const PreferencesForm = () => {
    const [formData, setFormData] = useState({
        genderPreference: '',
        smokingPreference: false,
        alcoholUsage: false,
        cleanlinessLevel: 3,
        noiseTolerance: 3,
        hygiene: 3,
        sleepSchedule: '',
        guestsVisitors: 3,
        workStudyFromHome: 3,
        petTolerance: 3,
        sharedExpenses: 3,
        studyHabits: 3,
        roomPrivacy: 3,
        cookingFrequency: 3,
        foodSharing: false,
        exerciseFrequency: 3,
        personalityType: '',
        sharedLivingSpaceUse: 3,
        roomTemperaturePreference: 3,
        decoratingStyle: ''
    });

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: type === 'checkbox' ? checked : value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Submit form data to the API endpoint (POST /api/preferences/create)
        console.log(formData); 
    };

    return (
        <div style={{ 
            display: 'flex', 
            justifyContent: 'center', 
            alignItems: 'center', 
            minHeight: '100vh', 
            padding: '20px' 
        }}>
            <form onSubmit={handleSubmit} style={{ 
                display: 'flex', 
                flexDirection: 'column', 
                gap: '10px', 
                width: '100%', 
                maxWidth: '600px', 
                backgroundColor: '#fff', 
                padding: '20px', 
                borderRadius: '8px', 
                boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)' 
            }}>
                 <label htmlFor="genderPreference">Gender Preference:</label>
            <select
                name="genderPreference"
                id="genderPreference"
                value={formData.genderPreference}
                onChange={handleChange}
                style={{ padding: '8px', borderRadius: '4px' }}
            >
                <option value="">Select Gender</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
                <option value="nonbinary">Nonbinary</option>
                <option value="no-preference">No Preference</option>
            </select>
            
            <label htmlFor="smokingPreference">Smoking Preference:</label>
            <select
                name="smokingPreference"
                id="smokingPreference"
                value={formData.smokingPreference}
                onChange={handleChange}
                style={{ padding: '8px', borderRadius: '4px' }}
            >
                <option value="">Select Smoking Preference</option>
                <option value="smoking">Smoking</option>
                <option value="no-smoking">No Smoking</option>
                <option value="no-preference">No Preference</option>
            </select>

            <label htmlFor="alcoholUsage">Alcohol Usage Preference:</label>
            <select
                name="alcoholUsage"
                id="alcoholUsage"
                value={formData.alcoholUsage}
                onChange={handleChange}
                style={{ padding: '8px', borderRadius: '4px' }}
            >
                <option value="">Select Alcohol Preference</option>
                <option value="alcohol">Drinks Alcohol</option>
                <option value="no-alcohol">No Alcohol</option>
                <option value="no-preference">No Preference</option>
            </select>
            
                <Slider
                    name="cleanlinessLevel"
                    value={formData.cleanlinessLevel}
                    min={1}
                    max={5}
                    step={1}

                    onChange={(value) => setFormData(prev => ({ ...prev, cleanlinessLevel: value }))}
                    label="Cleanliness Level"
                />
                <Slider
                    name="noiseTolerance"
                    value={formData.noiseTolerance}
                    min={1}
                    max={5}
                    step={1}
                    onChange={(value) => setFormData(prev => ({ ...prev, noiseTolerance: value }))}
                    label="Noise Tolerance"
                />
                <Slider
                    name="hygiene"
                    value={formData.hygiene}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, hygiene: value }))}
                    label="Hygiene Level"
                />
            <label htmlFor="sleepSchedule">Sleep Schedule Preference:</label>
            <select
                name="sleepSchedule"
                id="sleepSchedule"
                value={formData.sleepSchedule}
                onChange={handleChange}
                style={{ padding: '8px', borderRadius: '4px' }}
            >
                <option value="">Select Sleep Schedule Preference</option>
                <option value="alcohol">Early Bird</option>
                <option value="no-alcohol">Night Owl</option>
                <option value="no-preference">No Preference</option>
            </select>
                <Slider
                    name="guestsVisitors"
                    value={formData.guestsVisitors}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, guestsVisitors: value }))}
                    label="Guests/Visitors Tolerance"
                />
                <Slider
                    name="workStudyFromHome"
                    value={formData.workStudyFromHome}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, workStudyFromHome: value }))}
                    label="Work/Study from Home Preference"
                />
                <Slider
                    name="petTolerance"
                    value={formData.petTolerance}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, petTolerance: value }))}
                    label="Pet Tolerance"
                />
                <Slider
                    name="sharedExpenses"
                    value={formData.sharedExpenses}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, sharedExpenses: value }))}
                    label="Shared Expenses Preference"
                />
                <Slider
                    name="studyHabits"
                    value={formData.studyHabits}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, studyHabits: value }))}
                    label="Study Habits"
                />
                <Slider
                    name="roomPrivacy"
                    value={formData.roomPrivacy}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, roomPrivacy: value }))}
                    label="Room Privacy Preference"
                />
                <Slider
                    name="cookingFrequency"
                    value={formData.cookingFrequency}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, cookingFrequency: value }))}
                    label="Cooking Frequency"
                />
            <label htmlFor="foodSharing">Food Sharing Preference:</label>
            <select
                name="foodSharing"
                id="foodSharing"
                value={formData.foodSharing}
                onChange={handleChange}
                style={{ padding: '8px', borderRadius: '4px' }}
            >
                <option value="">Select Food Sharing Preference</option>
                <option value="alcohol">Ok with food sharing</option>
                <option value="no-alcohol">I do not like to share my food</option>
                <option value="no-preference">No Preference</option>
            </select>
                <Slider
                    name="exerciseFrequency"
                    value={formData.exerciseFrequency}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, exerciseFrequency: value }))}
                    label="Exercise Frequency"
                />
                <Input
                    label="Personality Type"
                    name="personalityType"
                    value={formData.personalityType}
                    onChange={handleChange}
                    fullWidth
                />
                <Slider
                    name="sharedLivingSpaceUse"
                    value={formData.sharedLivingSpaceUse}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, sharedLivingSpaceUse: value }))}
                    label="Shared Living Space Use"
                />
                <Slider
                    name="roomTemperaturePreference"
                    value={formData.roomTemperaturePreference}
                    min={1}
                    max={5}
                    onChange={(value) => setFormData(prev => ({ ...prev, roomTemperaturePreference: value }))}
                    label="Room Temperature Preference"
                />
                <Input
                    label="Decorating Style"
                    name="decoratingStyle"
                    value={formData.decoratingStyle}
                    onChange={handleChange}
                    fullWidth
                />
                <Button type="submit">Submit</Button>
            </form>
        </div>
    );
};

export default PreferencesForm;
