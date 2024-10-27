import { useEffect, useState } from "react";
import useUser from "../../Security/hooks/useUser";
import apiClient from "../../Security/axios/apiClient";
import EditProfile from "./EditProfile"; // Assuming you've created this component

const Profile = () => {
    const [userData] = useUser();
    const [profileData, setProfileData] = useState(null);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false); // State to manage editing

    useEffect(() => {
        if (userData.username) {
            console.log("Fetching profile for username:", userData);
            apiClient.get(`/profiles/username/${userData.username}`)
                .then(response => {
                    setProfileData(response.data); // Set profile data from API response
                    console.log("Profile data retrieved:", response.data); // Log the retrieved profile data
                })
                .catch(err => {
                    setError(err.message); // Handle any errors from the API call
                })
                .finally(() => {
                    setIsLoading(false); // End loading state
                });
        } else {
            console.log("Waiting for username to be available.");
            setIsLoading(false); // End loading state if no username
        }
    }, [userData.username]); // Effect depends on the username from userData

    const handleEditToggle = () => {
        setIsEditing(!isEditing); // Toggle the editing state
    };

    const handleProfileUpdate = (updatedProfile) => {
        setProfileData(updatedProfile); // Update the profile data
        setIsEditing(false); // Close the edit form
    };

    if (error) {
        return <p>Error loading profile: {error}</p>; // Handle error state
    }

    if (isLoading) {
        return <p>Loading profile data...</p>; // Show loading message
    }

    return (
        <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
            <h2>Profile Page</h2>
            {isEditing ? (
                <EditProfile
                    profileData={profileData}
                    onUpdate={handleProfileUpdate}
                    onCancel={handleEditToggle}
                />
            ) : (
                <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '10px', background: '#f9f9f9' }}>
                    <h3>User Information</h3>
                    {profileData ? (  // Conditional rendering based on profileData
                        <>
                            <p><strong>Username:</strong> {userData.username}</p>
                            <p><strong>First Name:</strong> {userData.firstname}</p>
                            <p><strong>Last Name:</strong> {userData.lastname}</p>
                            <p><strong>Bio:</strong> {profileData.bio || "No bio available."}</p>
                        </>
                    ) : (
                        <p>No profile data available.</p> // Message if profileData is null
                    )}
                    <button onClick={handleEditToggle}>Edit</button> {/* Edit button */}
                </div>
            )}
        </div>
    );
};

export default Profile;
