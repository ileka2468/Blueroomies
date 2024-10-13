# Database Schema Design

## Rev. 1

![schema](https://github.com/ileka2468/se452-group-project/blob/main/Documentation/Project/images/roommate-matcher-schema.png?raw=true)

### **1. Users Table**:
- **Description**: Stores user account information for auth.
- **Key Fields**:
  - `user_id (SERIAL)`: Unique identifier for each user (Primary Key).
  - `first_name (VARCHAR)`: The user's first name.
  - `last_name (VARCHAR)`: The user's last name.
  - `username (VARCHAR)`: The user's username.
  - `password (VARCHAR)`: The hashed password.
  - `enabled (BOOLEAN)`: A boolean saying whether the user's account is active.
  
---

### **2. Roles Table**:
- **Description**: Defines different roles users can have. Determines what actions users can perform within the app.
- **Key Fields**:
  - `role_id (SERIAL)`: Unique identifier for each role (Primary Key).
  - `role_name (SERIAL)`: The name of the role (`ROLE_USER`, `ROLE_ADMIN` etc...).

---

### **3. User Roles Table**:
- **Description**: Many-to-many relationship between users and roles. assigns specific roles to users.
- **Key Fields**:
  - `user_id (INT)`: Foreign key referencing the `users` table.
  - `role_id (INT)`: Foreign key referencing the `roles` table.

---

### **4. Profiles Table**:
- **Description**: Contains additional information for each user for matchmaking.
- **Key Fields**:
  - `profile_id (SERIAL)`: Unique identifier for each profile (Primary Key).
  - `user_id (INT)`: Foreign key linking to the `users` table.
  - `is_actively_looking (BOOLEAN)` - bool indicating whether a user is actively looking for a roomate and is in the matchmaking pool.  
  - `bio (VARCHAR)`: A short description or bio provided by the user.
  - `characteristics (JSONB)`: A JSON column containing the user's atttributes such as `budget`, `cleanliness_level`, `sleep_schedule etc...`

---

### **5. Preferences Table**:
- **Description**: Stores each user’s preferences for **other** potential roommates. This table is used by the matching algorithm to find compatible roommates.
- **Key Fields**:
  - `preference_id (SERIAL)`: Unique identifier for each preference set (Primary Key).
  - `user_id (INT)`: Foreign key linking to the `users` table.
  - `pref_characteristics (JSONB)`: A JSON column containing the user's preferred characteristics of their roommate.


---

### **6. Roommate Matches Table**:
- **Description**: This table stores the results of the matching algo it records pairs of users who have been matched and how well they matched.
- **Key Fields**:
  - `match_id (SERIAL)`: Unique identifier for each match (Primary Key).
  - `user_id_1 (INT)` and `user_id_2 (INT)`: Foreign keys referencing the `users` table, representing the two users involved in the match.
  - `match_score (INT)`: A numerical value representing how compatible the two users are.
  - `match_ts (DATE)`: A date representing when the match was made.

---

### **7. Messages Table**:
- **Description**: This table stores messages exchanged between matched users. Each message is associated with a specific match.
- **Key Fields**:
  - `message_id (SERIAL)`: Unique identifier for each message (Primary Key).
  - `match_id (INT)`: Foreign key linking to the `roommate_matches` table.
  - `sender_id (INT)` and `receiver_id (INT)`: Foreign keys linking to the `users` table, representing the sender and receiver of the message.
  - `content (VARCHAR)`: The text of the message.
  - `date_sent (DATE)`: The date the message was sent.


---

### **8. Notifications Table**:
- **Description**: Stores notifications for unread messages.
- **Key Fields**:
  - `notification_id (SERIAL)`: Unique identifier for each notification (Primary Key).
  - `user_id (INT)`: Foreign key linking to the `users` table.
  - `message_id (INT)`: Foreign key linking to the `messages` table.
  - `agreement_id (INT)`: Foreign key linking to the `agreements` table.
  - `is_read (INT)`: Boolean indicating whether the notification has been read.

---

### **9. Agreements Table**:
- **Description**: Stores agreements made between matched users. Each agreement is associated with a specific match.
- **Key Fields**:
  - `agreement_id (SERIAL)`: Unique identifier for each agreement (Primary Key).
  - `match_id (INT)`: Foreign key linking to the `roommate_matches` table.
  - `sender_id (INT)` and `receiver_id (INT)`: Foreign keys linking to the `users` table, representing the sender and receiver of the agreement.
  - `content (VARCHAR)`: The text of the agreement.
  - `status (INT)`: Represents if the agreement is declined (0), accepted (1), or pending (2).
  - `date_sent (DATE)`: The date the agreement was sent.

  ---