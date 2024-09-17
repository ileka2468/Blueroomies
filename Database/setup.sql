-- setup.sql
-- run after database has been created

-- create Users table
CREATE TABLE Users (
  user_id SERIAL PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(50) NOT NULL, -- HASHED ONLY
  enabled BOOLEAN NOT NULL
);

-- create Roles table
CREATE TABLE Roles (
  role_id SERIAL PRIMARY KEY,
  role_name VARCHAR(50) UNIQUE NOT NULL 
);

-- create User_Roles table
CREATE TABLE User_Roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE
);

-- create Profiles table
CREATE TABLE Profiles (
  profile_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  is_actively_looking BOOLEAN NOT NULL,
  bio VARCHAR(500),
  characteristics JSONB NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- create Preferences table
CREATE TABLE Preferences (
  preference_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  pref_characteristics JSONB NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- create Roommate_Matches table
CREATE TABLE Roommate_Matches (
  match_id SERIAL PRIMARY KEY,
  user_id_1 INT NOT NULL,
  user_id_2 INT NOT NULL,
  match_score NUMERIC NOT NULL,
  FOREIGN KEY (user_id_1) REFERENCES Users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (user_id_2) REFERENCES Users(user_id) ON DELETE CASCADE,
  CHECK (user_id_1 <> user_id_2) -- does not allow someone to be matched with themself
);

-- create Messages table
CREATE TABLE Messages (
  message_id SERIAL PRIMARY KEY,
  match_id INT NOT NULL,
  sender_id INT NOT NULL,
  receiver_id INT NOT NULL,
  content VARCHAR(500) NOT NULL,
  FOREIGN KEY (match_id) REFERENCES Roommate_Matches(match_id) ON DELETE CASCADE,
  FOREIGN KEY (sender_id) REFERENCES Users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (receiver_id) REFERENCES Users(user_id) ON DELETE CASCADE,
  CHECK (sender_id <> receiver_id) -- does not allow messages to be sent to themself
);

-- create Notifications table
CREATE TABLE Notifications (
  notification_id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  message_id INT NOT NULL,
  is_read BOOLEAN NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (message_id) REFERENCES Messages(message_id) ON DELETE CASCADE
);


