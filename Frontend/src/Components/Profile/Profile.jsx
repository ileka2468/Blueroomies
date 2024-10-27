import { useEffect, useState } from "react";
import useUser from "../../Security/hooks/useUser.js";
import apiClient from "../../Security/axios/apiClient.js";

const Profile = () => {
    const [userData] = useUser();  // Get user data from the useUser hook
    const [profileData, setProfileData] = useState(null);  // State for storing profile data
    const [error, setError] = useState(null);  // State for handling errors

    useEffect(() => {
        // Check if username is available before making the API call
        if (userData.username) {
            console.log("Fetching profile for username:", userData.username);  // Log the username

            // Use apiClient to call the profile API with the username
            apiClient.get(`/profiles/username/${userData.username}`)
                .then(response => {
                    setProfileData(response.data);  // Set profile data from API response
                    console.log("Profile data retrieved:", response.data);  // Log the retrieved profile data
                })
                .catch(err => {
                    setError(err.message);  // Handle any errors from the API call
                });
        } else {
            console.log("Waiting for username to be available.");  // Log waiting for username
        }
    }, [userData.username]);  // Effect depends on the username from userData

    // Handle error state
    if (error) {
        return <p>Error loading profile: {error}</p>;
    }

    // Render profile information once profileData is available
    return (
        <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
            <h2>Profile Page</h2>
            {profileData ? (
                <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '10px', background: '#f9f9f9' }}>
                    <h3>User Information</h3>
                    <p><strong>Username:</strong> {userData.username}</p>
                    <p><strong>First Name:</strong> {userData.firstname}</p>
                    <p><strong>Last Name:</strong> {userData.lastname}</p>
                    <p><strong>Bio:</strong> {profileData.bio}</p>
                    <p><strong>Joined:</strong> {profileData.joinedDate}</p>
                </div>
            ) : (
                <p>Loading profile data...</p> // Loading message while waiting for data
            )}
        </div>
    );
};

export default Profile;
