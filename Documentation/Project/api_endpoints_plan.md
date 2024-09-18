API Endpoint Plan Rev.1

## **1. Authentication and Authorization** 🔓

### **1.1. User Registration and Login**

- **POST** `/api/auth/register`
  - **Description**: Register a new user with username, password, first name, last nanme.
- **POST** `/api/auth/login`
  - **Description**: Authenticate a user and return an access token.
- **POST** `/api/auth/logout`
  - **Description**: Log out the current user and invalidate the session/token.
- **POST** `/api/auth/refresh-token`
  - **Description**: Refresh the authentication token.

---

## **2. User Profile Management** 🔒

### **2.1. Profile Retrieval and Update**

- **GET** `/api/profiles/me`
  - **Description**: Retrieve the profile information for the authenticated user.

- **PUT** `/api/profiles/me`
  - **Description**: Update the profile information for the authenticated user.

  ---

## **3. User Preferences Management** 🔒

### **3.1. Preferences Retrieval and Update**

- **GET** `/api/preferences/me`
  - **Description**: Retrieve the roommate preferences for the currently authed user.

- **PUT** `/api/preferences/me`
  - **Description**: Update the roommate preferences for the currently authed user.

---


## **4. Roommate Matching** 🔒

### **4.1. Trigger Matching Algorithm**

- **POST** `/api/matches/run`
  - **Description**: Triggers the matching algorithm for the currently authed user based on frontend preferences form.
  - **Request Body** (requires preferences to be sent as json data from frontend)
    ```json
    {
      "cleanliness": "high",
      "smoking": "non-smoker",
      "pets": "yes",
      "noise": "moderate",
      "proximity": "campus",
      etc..
    }
    ```

  - **Response**: Matches generated from the algorithm to the frontend for display.
  
  - **Response Example**:
    ```json
    {
      "matches": [
        {
          "user_id": 5,
          "match_score": 87,
          "profile": {
            "name": "John Doe",
            "bio": "Looking for a quiet roommate.",
            "budget": 900,
            "oncampus": false,
            "cleanliness": "high",
            "smoking": "non-smoker",
            "pets": "no",
            "noise": "quiet",
            "proximity": "campus"
          }
        },
        {
          "user_id": 8,
          "match_score": 82,
          "profile": {
            "name": "Jane Smith",
            "bio": "Night owl, loves pets.",
            "budget": 700,
            "oncampus": false,
            "cleanliness": "medium",
            "smoking": "non-smoker",
            "pets": "yes",
            "noise": "moderate",
            "proximity": "off-campus"
          }
        }
      ]
    }
    ```

### **4.2. Retrieve Stored Matches (Unfortunately with Pagination)**

- **POST** `/api/matches`
  - **Description**: Retrieve a paginated list of the stored matches from the `roommate_matches` table for the currently authed user. Filters can be applied.

- **Request Body**:
  ```json
  {
    "page": 1,
    "size": 20,
    "filters": {
      "budget": {
        "min": 500,
        "max": 1000
      },
      "cleanliness": "high",
      "smoking": "non-smoker"
    }
  }
  ```

- **Response**: A paginated list of matches for the current user.
  
  - **Response Example**:
    ```json
    {
      "page": 1,
      "size": 20,
      "totalPages": 5,
      "totalResults": 100,
      "matches": [
        {
          "user_id": 5,
          "match_score": 87,
          "match_run_time": "2024-09-18",
          "profile": {
            "name": "John Doe",
            "bio": "Looking for a quiet roommate.",
            "budget": 900,
            "oncampus": false,
            "cleanliness": "high",
            "smoking": "non-smoker",
            "pets": "no",
            "noise": "quiet",
            "proximity": "campus"
          }
        },
        {
          "user_id": 8,
          "match_score": 82,
          "match_run_time": "2024-09-18",
          "profile": {
            "name": "Jane Smith",
            "bio": "Night owl, loves pets.",
            "budget": 700,
            "oncampus": false,
            "cleanliness": "medium",
            "smoking": "non-smoker",
            "pets": "yes",
            "noise": "moderate",
            "proximity": "off-campus"
          }
        }
      ]
    }
    ```

---

## Key
- 🔓 = Unprotected route
- 🔒 = Protected route (requires authenticated user)