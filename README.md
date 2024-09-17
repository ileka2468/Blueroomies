

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
##### Developers: Cristian and Matthew
- [Project Focus Area Here (TBD)]
- [Project Focus Area Here (TBD)]

### **Feature Group: Authentication, Roommate Matching, Search/Filtering**
##### Developers: William and Nathan
- [Project Focus Area Here (TBD)]
- [Project Focus Area Here (TBD)]

### **Profile Management, Preferences**
##### Developers: Harshita and Saqib
- [Project Focus Area Here (TBD)]
- [Project Focus Area Here (TBD)]

---

## **3. GitHub Location**
- **Project Repository**:  
  [GitHub Link to Repository](https://github.com/ileka2468/se452-group-project)
  
- **Repository Structure**:  
  - `Frontend/`: Contains React components and frontend logic.
  - `Backend/`:  Spring Boot code for API endpoints and business logic.
  - `Database/`: SQL seeding scripts and database schema.
  - `DOcumentation/`: Contains project and meeting documentation.

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
  - Weekly virtual meetings via #weeklymeetings voice channel in server.
  - Random popup meetings can be called if something really really bad is occuring and decisions need to be made.

---

## **6. Meeting Journal**
We will have 2 meetings per week. Sundays and TBD.
- **Meeting 1**:  
  - **Date**: 9/15/2024
  - **Agenda**: Brainstorming app ideas, role assignment, cloning repo, getting repo perms and access.
  - **Outcomes**: Decided on app concept, assigned roles, cloned the starter repo.
  
- **Meeting 2**:  
  - **Date**: [TBD]
  - **Agenda**: Initial feature development progress, deciding database schema.
  - **Outcomes**: TBD

More soon...

---

## **7. Key Decisions**
- **Editor**:  
  The team can use whatever IDE's or text-editors they are comfortable with but thre recommended editor will be Intelij **IDEA Community Edition** for Java and Spring Boot code and Visual Studio Code for Frontend/React code.
  
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
