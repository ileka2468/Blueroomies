import React, { useState } from "react";
import apiClient from "../../Security/axios/apiClient.js";

const EditProfile = ({ profileData, onUpdate, onCancel }) => {
    const [bio, setBio] = useState(profileData.bio || ''); // Initialize with current bio

    const handleChange = (e) => {
        setBio(e.target.value); // Update bio state
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const updatedProfile = { ...profileData, bio }; // Create updated profile object
        const token = localStorage.getItem("accessToken"); // Retrieve access token
        console.log("Submitting updated profile:", updatedProfile); // Log the updated profile data
        console.log("Access Token before submitting:", token);
        // Send updated profile data to the backend
        apiClient.put(`/profiles/${profileData.id}`, updatedProfile)
            .then(response => {
                onUpdate(response.data); // Call the update function passed as prop
            })
            .catch(err => {
                console.error("Error updating profile:", err.message);
            });
    };

    return (
        <form onSubmit={handleSubmit} style={{ marginBottom: '20px' }}>
            <h3>Edit Profile</h3>
            <label>
                <strong>Bio:</strong>
                <textarea
                    name="bio"
                    value={bio}
                    onChange={handleChange}
                    rows="4"
                    style={{ width: '100%' }}
                />
            </label>
            <br />
            <button type="submit">Save Changes</button>
            <button type="button" onClick={onCancel}>Cancel</button>
        </form>
    );
};

export default EditProfile;
