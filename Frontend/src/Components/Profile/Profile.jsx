import { useEffect, useState } from "react";
import useUser from "../../Security/hooks/useUser";
import apiClient from "../../Security/axios/apiClient";
import EditProfile from "./EditProfile";

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
    }, [userData, userData.username]);

    const handleEditToggle = () => {
        setIsEditing(!isEditing);
    };

    const handleProfileUpdate = (updatedProfile) => {
        setProfileData(updatedProfile);
        setIsEditing(false);
    };

    if (error) {
        return <p>Error loading profile: {error}</p>;
    }

    if (isLoading) {
        return <p>Loading profile data...</p>;
    }

    if (!profileData) {
        return <p>No profile data available.</p>;
    } else {

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "flex-start", height: "auto" }}>
            {/* Left Box: Profile Picture, Username, Bio */}
            <div
                style={{
                    width: "500px",
                    padding: "20px",
                    border: "1px solid #ccc",
                    borderRadius: "10px",
                    backgroundColor: "#f9f9f9",
                    marginRight: "20px",
                    textAlign: "center",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    minHeight: "400px",
                    height: "auto",
                    overflow: "auto", 
                }}
            >
                {/* Profile Picture */}
                {<profileData className="pfpImage"></profileData> ? (
                    <img
                        src={profileData.pfpImage}
                        alt="Profile Picture"
                        style={{
                            width: "150px",
                            height: "150px",
                            borderRadius: "50%",
                            objectFit: "cover",
                            marginBottom: "10px",
                        }}
                    />
                ) : (
                    <div
                        style={{
                            width: "150px",
                            height: "150px",
                            borderRadius: "50%",
                            backgroundColor: "#ddd",
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            marginBottom: "10px",
                            fontSize: "36px",
                            color: "#555",
                        }}
                    >
                        {userData?.firstname?.[0]}{userData?.lastname?.[0]}
                    </div>
                )}

                {/* User Info */}
                
                <p><strong>Username:</strong> {userData.username}</p>
                <p><strong>First Name:</strong> {userData.firstname}</p>
                <p><strong>Last Name:</strong> {userData.lastname}</p>

                {/* Editable Bio */}
                {isEditing ? (
                    <EditProfile
                        profileData={profileData}
                        onUpdate={handleProfileUpdate}
                        onCancel={handleEditToggle}
                    />
                ) : (
                    <div style={{ marginBottom: "10px" }}>
                        <p><strong>Bio:</strong> {profileData.bio || "No bio available."}</p>
                        <button onClick={handleEditToggle} style={{ marginTop: '10px' }}>Edit Bio</button>
                    </div>
                )}
            </div>

            {/* Right Box: Characteristics */}
            <div
                style={{
                    flexGrow: 1,
                    padding: "20px",
                    border: "1px solid #ccc",
                    borderRadius: "10px",
                    backgroundColor: "#f9f9f9",
                }}
            >
                <h1>Preferences</h1>

                {profileData?.characteristics && Object.keys(profileData.characteristics).length > 0 ? (
                    <ul style={{ listStyleType: "none", padding: 0 }}>
                        {Object.entries(profileData.characteristics).map(([key, value]) => (
                            <li
                                key={key}
                                style={{
                                    marginBottom: "10px",
                                    padding: "10px",
                                    border: "1px solid #ddd",
                                    borderRadius: "5px",
                                    backgroundColor: "#fff",
                                }}
                            >
                                <strong>{key}:</strong> {value.toString()}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No characteristics available.</p>
                )}
            </div>
        </div>
    );}
};

export default Profile;
