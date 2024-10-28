# **Roommate Finder App - Project Documentation**

## **1. Project Scope Area**

- **Objective**:  
  Our project is to develop a web application for DePaul University students to find and connect with potential roommates. The app will provide a platform for users to create profiles, input living preferences, and match with other students based on compatibility.
- **Tech Stack**:
  - Backend: Spring Boot
  - Database: PostgreSQL
  - Frontend: React

---

## **2. Initial Key Features (by Developer Focus Area and Feature Group)**

### **Feature Group: Messaging, Notifications, Agreements**

##### Developers: Cristian (Backend) and Matthew (Frontend/Database)

- Matthew - Frontend components for messaging page, agreement page, and notification system.
- Cristian - Backend, enabling messaging services between matched users, setting up notifications to alert users when new matches are found, etc. Creating agreement between users when establishing roommate partnership. Additional features as the project progresses.

### **Feature Group: Authentication, Roommate Matching, Search/Filtering**

##### Developers: William (Lead/Flex) and Nathan (Backend/Database)

- Nathan - Backend and database configurations/setup. Utilizing statistical models to determine an efficient roommate matching system, and write microservices for server to database interactions for search/filter operations to be utilized by frontend components.
- William - In addition to setting up requiremnts, documentation, and architecture for the project I'll be working on both the Spring backend and React frontend for authentication, roommate matching, and search/filtering features. For the backend, I’ll handle setting up authentication using JWT, and work with Nate on the matchmaking algo for roommate matching.

### **Profile Management, Preferences**

##### Developers: Harshita (Backend/Frontend/Database) and Saqib (Backend/Frontend/Database)

- Harshita: Backend, managing user preferences such as setting criteria (Ex: cleanliness, location, sleep schedules, etc); allow users to set, view, update preferences and that preferences are presented in an intuitive manner Frontend: home page + dashboard ui/design, the organization of preference form display
- Saqib: Backend: facilitate profile creation, updating, and retrieval. Frontend: implement intuitive forms, allowing profile details to be displayed and edited.

---

## **3. GitHub Location**

- **Project Repository**:  
  [GitHub Link to Repository](https://github.com/ileka2468/se452-group-project)
- **Repository Structure**:
  - `Frontend/`: Contains React components and frontend logic.
  - `Backend/`: Spring Boot code for API endpoints and business logic.
  - `Database/`: SQL seeding scripts and database schema.
  - `Documentation/`: Contains project and meeting documentation.

---

## **4. Conflict Resolution Strategy**

### **Code Conflicts**:

- **Feature Branch Workflow**: Each dev should work on their own feature branch, which will eventually be merged into the `main` branch through a pull request.
- **Pull Request Process**:
  1. Before opening a PR, developers must **pull the latest changes** from `main` into their feature branch to minimize potential conflicts:
     ```bash
     git checkout main
     git pull origin main
     git checkout feature/your-feature
     git merge main
     ```
  2. If conflicts arise during the merge, the developer is responsible for resolving them in their feature branch.
  3. Once conflicts are resolved, the developer updates the branch and pushes the changes to the remote repository:
     ```bash
     git add .
     git commit -m "Resolve merge conflicts"
     git push origin feature/your-feature
     ```
  4. The PR is then reviewed by other team members, and after approval, the feature branch is merged into `main`.

### **Disagreements on Code or Features**:

- If there is a disagreement regarding the implementation or functionality during the pull request review, the following steps will be taken:
  1. Discuss the issue in the PR comments or during the next scheduled meeting and solve it with a dicord poll.

---

## **5. Communication Mechanism**

- **Primary Tool**:
  - **Discord Server**: The team will use Discord for communication.
- **Scheduled Meetings**:
  - Weekly virtual meetings via '#meeting-voice'channel in discord server.
  - Random popup meetings can be called if something really really bad is occuring and decisions need to be made.

---

## **6. Meeting Journal**

We will have 2 meetings per week on Thursday(5pm) and Sunday(1pm).

- **Meeting 1**:
  - **Date**: 9/15/2024
  - **Agenda**: Brainstorming app ideas, role assignment, cloning repo, getting repo perms and access.
  - **Outcomes**: Decided on app concept, assigned roles, cloned the starter repo.
- **Meeting 2**:
  - **Date**: 9/19/2024
  - **Agenda**: New members introduction, discuss hybrid role system, go over feature groups, recommended IDEs/software, documentation walkthrough, code walkthrough, verify repo access and environment setup.
  - **Outcomes**: New members introduced, role structure clarified, IDEs/software recommended, basic code reviewed, repo access and environment verified.

More soon...

---

## **7. Key Decisions**

- **Editor**:  
  The team can use whatever IDE's or text-editors they are comfortable with but thre recommended editor will be Intelij **IDEA Ultimate Edition** for Java and Spring Boot code and **Visual Studio Code** for Frontend/React code.
- **Project Scope**:  
  The final project scope includes user authentication, profile management, roommate matching, and messaging.

---

## **8. Appendix**

- **Screenshot**:  
   Below is a screenshot of the initial clean compile of the project code.

  - Frontend
    ![Clean Compile Screenshot](https://github.com/ileka2468/website/blob/main/fcpi.png?raw=true)
    ![Clean Compile Screenshot](https://github.com/ileka2468/website/blob/main/front.png?raw=true)

  - Backend
    ![Clean Compile Screenshot](https://github.com/ileka2468/website/blob/main/back.png?raw=true)
