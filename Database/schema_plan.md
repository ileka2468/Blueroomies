Database Schema Design Rev.1

![schema](./images/roomate-matcher-schema.png)

### **1. Users Table**:
- **Description**: Stores user account information for auth.
- **Key Fields**:
  - `user_id`: Unique identifier for each user (Primary Key).
  - `username`: The user's username.
  - `password`: The hashed password.
  - `enabled`: A boolean saying whether the user's account is active.
  
---

### **2. Roles Table**:
- **Description**: Defines different roles users can have. Determines what actions users can perform within the app.
- **Key Fields**:
  - `role_id`: Unique identifier for each role (Primary Key).
  - `role_name`: The name of the role (`ROLE_USER`, `ROLE_ADMIN` etc...).

---

### **3. User Roles Table**:
- **Description**: Many-to-many relationship between users and roles. assigns specific roles to users.
- **Key Fields**:
  - `user_id`: Foreign key referencing the `users` table.
  - `role_id`: Foreign key referencing the `roles` table.

---

### **4. Profiles Table**:
- **Description**: Contains additional information for each user related to matchmaking.
- **Key Fields**:
  - `profile_id`: Unique identifier for each profile (Primary Key).
  - `user_id`: Foreign key linking to the `users` table.
  - `is_actively_looking` - bool indicating whether a user is actively looking for a roomate and is in the matchmaking pool.  
  - `bio`: A short description or bio provided by the user.
  - `characteristics`: A JSON column containing the user's atttributes such as `budget`, `cleanliness_level`, `sleep_schedule etc...`

---

### **5. Preferences Table**:
- **Description**: Stores each user’s preferences for potential roommates. This table is used by the matching algorithm to find compatible roommates.
- **Key Fields**:
  - `preference_id`: Unique identifier for each preference set (Primary Key).
  - `user_id`: Foreign key linking to the `users` table.
  - `pref_characteristics`: A JSON column containing the user's preferred characteristics of their roommate.


---

### **6. Roommate Matches Table**:
- **Description**: This table stores the results of the matching algorithm. It records pairs of users who have been matched based on compatibility, along with a score indicating how well they match.
- **Key Fields**:
  - `match_id`: Unique identifier for each match (Primary Key).
  - `user_id_1` and `user_id_2`: Foreign keys referencing the `users` table, representing the two users involved in the match.
  - `match_score`: A numerical value representing how compatible the two users are.

---

### **7. Messages Table**:
- **Description**: This table stores messages exchanged between matched users. Each message is associated with a specific match.
- **Key Fields**:
  - `message_id`: Unique identifier for each message (Primary Key).
  - `match_id`: Foreign key linking to the `roommate_matches` table.
  - `sender_id` and `receiver_id`: Foreign keys linking to the `users` table, representing the sender and receiver of the message.
  - `content`: The text of the message.


---

### **8. Notifications Table (Optional)**:
- **Description**: Stores notifications for unread messages.
- **Key Fields**:
  - `notification_id`: Unique identifier for each notification (Primary Key).
  - `user_id`: Foreign key linking to the `users` table.
  - `message_id`: Foreign key linking to the `messages` table.
  - `is_read`: Boolean indicating whether the notification has been read.

---
