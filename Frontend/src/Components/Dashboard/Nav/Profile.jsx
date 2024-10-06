
const Profile = () => {
  const userData = {
    username: "Test",
    email: "Test@example.com",
    bio: "A passionate software developer with interests in web technologies and machine learning.",
    joinedDate: "January 2022"
  };
    return (
      <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <h2>Profile Page</h2>
      
      <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '10px', background: '#f9f9f9' }}>
        <h3>User Information</h3>
        <p><strong>Username:</strong> {userData.username}</p>
        <p><strong>Email:</strong> {userData.email}</p>
        <p><strong>Bio:</strong> {userData.bio}</p>
        <p><strong>Joined:</strong> {userData.joinedDate}</p>
      </div>

      <div style={{ marginTop: '20px', textAlign: 'center' }}>
        <button>Edit Profile</button>
      </div>
    </div>
  );
};
  
  export default Profile;