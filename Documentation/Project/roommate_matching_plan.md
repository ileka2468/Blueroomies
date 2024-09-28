# Roommate Matching Plan Rev.1

## Problem definition
The goal of the roommate matching application is to help DePaul students find other potential roommates based on compatability and preferences. The challenge is to create a matching algorithm that can handle a dynamic and growing user base while ensuring accuracy and scalability. The model should accomodate new users seamlessly and adapt as more data becomes available, improving over time. 

## Approach
The proposed method to achieve this goal is to utilize a modified K-Nearest-Neighbors (KNN) algorithm, adapted for roommate matching based on several living preferences. 

In the original KNN algorithm, the distance between data points is calculated using Euclidean distance, or any other distance formula that treats each feature (in this context, living preference) equally. Such that,

$$d(u_1, u_2) = \sqrt{(x_1 - y_1)^2 + (x_2 - y_2)^2 + \dots}$$

where $u_1, \dots, u_n$ represents users, $x$ refers to preferences of the first user and $y$ representing the preferences of the second user. The issue with applying the original KNN is that we need to utilize known labels (whether a user is a good or bad match based on past data) for training, and for *each* user. Additionally, it would need to be re-calculated each time a user joins the application for the model to learn. This is inefficient and would not help us achieve our goal. 

For a better fitting, we will be modifying the KNN model such that there are no predefined labels such as 'good' or 'bad' matches. Instead of intensively calulating such matches, we will find the **most similar** users based on their preferences; the model will focus on similarity-based matching rather than utilizing prediction or classification. It is also a more **real-time** approach since we purely compare user preferences and output a list of nearest neighbors for a user without needing to reference any labels or historical data. We modify KNN such that,

$$d(u_1, u_2) = \sqrt{w_1 \cdot (x_1 - y_1)^2 + w_2 \cdot (x_2-y_2)^2 + \dots}$$ 

where we scale each difference in preference with a weight $w_n$. Weights allow us to accomodate for user prioritized preferences, for example, smoking habits may be more important than sleeping habit alignment. 