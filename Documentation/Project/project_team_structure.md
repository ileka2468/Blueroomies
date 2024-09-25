# Team Structure and Task Documentation

We decided to structure our project in feature groups, where each group is responsible for the entire feature from front-end to back-end. This approach allows us to take abstract concepts and break them down into clear tasks that are actionable and well-defined. Each member of a feature group works across the full stack but retains clear ownership of the work they contributed to. According to Agile principles, we are concerned that broad initiatives or business objects are too vague to act on directly. Initiatives like "Build everything related to the Roomate business object" are too large for one developer, and without breaking them down, it would result in confusion about where to start, what to prioritize, and who is responsible for what.

## Why We're Using Feature Groups:

- In a structure where memebers are responsible for business objects, it becomes too ambiguous what their responsibility is, especially at the intersections of front-end, back-end, and database work.
- We needed to convert high-level concepts into actual work and tasks, so our feature-based structure ensures that tasks are more focused and specific.
- Each feature group will document their contributions to the project.

## Documentation of Work:

- To ensure transparency and accountability, the **original author** and any **co-authors** will be documented at the top of each file in comments.
- The **author** is the team member who has done the most work on the file. If another team member makes contributions, they will be listed as a **co-author**.
- With each milestone submission we will run repo analysis tools and give a report on the authorship percentage of code for every file. This report will include a breakdown of contributions by author for each file, which will allow for more transparent grading and provide good contribution tracking while keeping our existing system.

### Example Authorship Analysis:

- ![image info](https://github.com/ileka2468/se452-group-project/blob/main/Documentation/Project/images/analysis.png?raw=true)

## Example of File Documentation:

```java
// Author: William
// Co-Author: Nathan
// Feature: Authentication Service

public class AuthService {
    // Code implementation...
}
```
