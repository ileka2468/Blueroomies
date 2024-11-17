import { useEffect, useState } from "react";
import useUser from "../../Security/hooks/useUser";
import apiClient from "../../Security/axios/apiClient";
import EditProfile from "./EditProfile";
import { Button, Card } from "@nextui-org/react";  

const CustomLoadingSpinner = () => {
    return <div className="loading-spinner">Loading...</div>;
};

const Profile = () => {
    const [userData] = useUser();
    const [profileData, setProfileData] = useState(null);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);

    useEffect(() => {
        if (userData.username) {
            console.log("Fetching profile for username:", userData);
            apiClient.get(`/profiles/username/${userData.username}`)
                .then(response => {
                    setProfileData(response.data);
                    console.log("Profile data retrieved:", response.data);
                })
                .catch(err => {
                    setError(err.message);
                })
                .finally(() => {
                    setIsLoading(false);
                });
        } else {
            console.log("Waiting for username to be available.");
            setIsLoading(false);
        }
    }, [userData.username]);

    const handleEditToggle = () => {
        setIsEditing(!isEditing);
    };

    const handleProfileUpdate = (updatedProfile) => {
        setProfileData(updatedProfile);
        setIsEditing(false);
    };

    if (error) {
        return <p>Error loading profile: {error}</p>;  // Error message
    }

    if (isLoading) {
        return <CustomLoadingSpinner />; // Custom Loading spinner
    }

    return (
        <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
            <Card css={{ padding: '20px', borderRadius: '10px' }}>
                <h2 style={{ textAlign: 'center' }}>Profile Page</h2>
                {isEditing ? (
                    <EditProfile
                        profileData={profileData}
                        onUpdate={handleProfileUpdate}
                        onCancel={handleEditToggle}
                    />
                ) : (
                    <div>
                        {profileData ? (
                            <div>
                                <h3>User Information</h3>
                                <p><strong>Username:</strong> {userData.username}</p>
                                <p><strong>First Name:</strong> {userData.firstName}</p>
                                <p><strong>Last Name:</strong> {userData.lastName}</p>
                                <p><strong>Bio:</strong> {profileData.bio || "No bio available."}</p>
                            </div>
                        ) : (
                            <p>No profile data available.</p>
                        )}
                        <Button onClick={handleEditToggle} css={{ marginTop: '20px' }} color="primary">
                            Edit Profile
                        </Button>
                    </div>
                )}
            </Card>
        </div>
    );
};

export default Profile;
