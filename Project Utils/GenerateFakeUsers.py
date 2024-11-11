import json
import random
from faker import Faker

fake = Faker()

def generate_user_data(num_users=10) -> list:
    users = []
    for _ in range(num_users):
        user = {
                "cleanliness_level": random.randint(1, 5),
                "gender_preference": random.choice(["Male", "Female"]),
                "smoking_preference": str(random.choice([True, False])).lower(),
                "alcohol_usage": str(random.choice([True, False])).lower()
                }
        users.append(user)
    return users

def save_users_to_json(users, filename="FakeUsers.json"):
    with open(filename, 'w') as outfile:
        json.dump(users, outfile, indent=4)

if __name__ == "__main__":
    num_users = 20
    users = generate_user_data(num_users)
    save_users_to_json(users)
    print(f"Generated {num_users} fake users and saved to FakeUsers.json")
