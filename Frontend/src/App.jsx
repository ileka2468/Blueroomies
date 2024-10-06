import { useState, useMemo } from "react";
import { useAxios } from "./Security/axios/AxiosProvider";
import Nav from "./Components/Dashboard/Nav/Nav";
import { useDisclosure } from "@nextui-org/react";
import LoginModal from "./Components/Authentication/LoginModal";
import RegisterModal from "./Components/Authentication/RegisterModal";
import useUser from "./Security/hooks/useUser";
import SideFilter from "./Components/Filter/SideFilter";
import RoommateResults from "./Components/RoommateResults";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Profile from './Components/Dashboard/Nav/Profile';




function App() {
 const apiClient = useAxios();
 const [userData, setUserData, isUser] = useUser();
 const [matches, setMatches] = useState([
   {
     user_id: 1,
     match_score: 85,
     profile: {
       name: "Alice Smith",
       bio: "Avid reader and coffee lover looking for a calm living environment.",
       gender_preference: 0,
       smoking_preference: 0,
       alcohol_usage: 1,
       cleanliness_level: 4,
       noise_tolerance: 3,
       hygiene: 4,
       sleep_schedule: 3,
       guests_visitors: 2,
       work_study_from_home: 1,
       pet_tolerance: 4,
       shared_expenses: 3,
       study_habits: 4,
       room_privacy: 4,
       cooking_frequency: 3,
       food_sharing: 1,
       exercise_frequency: 2,
       personality_type: 2,
       shared_living_space_use: 3,
       room_temperature_preference: 3,
       decorating_style: 2,
     },
   },
   {
     user_id: 2,
     match_score: 92,
     profile: {
       name: "Brian Johnson",
       bio: "Tech enthusiast and gamer seeking a like-minded roommate.",
       gender_preference: 0,
       smoking_preference: 1,
       alcohol_usage: 2,
       cleanliness_level: 3,
       noise_tolerance: 5,
       hygiene: 3,
       sleep_schedule: 2,
       guests_visitors: 4,
       work_study_from_home: 3,
       pet_tolerance: 2,
       shared_expenses: 4,
       study_habits: 2,
       room_privacy: 3,
       cooking_frequency: 4,
       food_sharing: 2,
       exercise_frequency: 3,
       personality_type: 4,
       shared_living_space_use: 4,
       room_temperature_preference: 2,
       decorating_style: 3,
     },
   },
   {
     user_id: 3,
     match_score: 78,
     profile: {
       name: "Catherine Lee",
       bio: "Yoga instructor and vegetarian, prefers a peaceful home.",
       gender_preference: 0,
       smoking_preference: 0,
       alcohol_usage: 0,
       cleanliness_level: 5,
       noise_tolerance: 2,
       hygiene: 5,
       sleep_schedule: 4,
       guests_visitors: 1,
       work_study_from_home: 2,
       pet_tolerance: 5,
       shared_expenses: 3,
       study_habits: 3,
       room_privacy: 5,
       cooking_frequency: 5,
       food_sharing: 1,
       exercise_frequency: 5,
       personality_type: 1,
       shared_living_space_use: 2,
       room_temperature_preference: 4,
       decorating_style: 4,
     },
   },
   {
     user_id: 4,
     match_score: 81,
     profile: {
       name: "David Martinez",
       bio: "Fitness enthusiast and early riser looking for a supportive roommate.",
       gender_preference: 0,
       smoking_preference: 0,
       alcohol_usage: 2,
       cleanliness_level: 4,
       noise_tolerance: 3,
       hygiene: 4,
       sleep_schedule: 1,
       guests_visitors: 2,
       work_study_from_home: 3,
       pet_tolerance: 3,
       shared_expenses: 4,
       study_habits: 2,
       room_privacy: 3,
       cooking_frequency: 4,
       food_sharing: 2,
       exercise_frequency: 5,
       personality_type: 3,
       shared_living_space_use: 3,
       room_temperature_preference: 3,
       decorating_style: 2,
     },
   },




{
 user_id: 5,
 match_score: 87,
 profile: {
   name: "Emily Davis",
   bio: "Artist and musician seeking a creative and inspiring living space.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 1,
   cleanliness_level: 3,
   noise_tolerance: 4,
   hygiene: 3,
   sleep_schedule: 3,
   guests_visitors: 3,
   work_study_from_home: 2,
   pet_tolerance: 4,
   shared_expenses: 3,
   study_habits: 4,
   room_privacy: 3,
   cooking_frequency: 2,
   food_sharing: 0,
   exercise_frequency: 3,
   personality_type: 4,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 5,
 },
},
{
 user_id: 6,
 match_score: 74,
 profile: {
   name: "Frank Thompson",
   bio: "Night owl and freelance writer looking for a flexible roommate.",
   gender_preference: 0,
   smoking_preference: 1,
   alcohol_usage: 3,
   cleanliness_level: 2,
   noise_tolerance: 5,
   hygiene: 2,
   sleep_schedule: 5,
   guests_visitors: 4,
   work_study_from_home: 4,
   pet_tolerance: 1,
   shared_expenses: 2,
   study_habits: 1,
   room_privacy: 2,
   cooking_frequency: 3,
   food_sharing: 1,
   exercise_frequency: 1,
   personality_type: 5,
   shared_living_space_use: 4,
   room_temperature_preference: 2,
   decorating_style: 1,
 },
},
{
 user_id: 7,
 match_score: 90,
 profile: {
   name: "Grace Kim",
   bio: "Graduate student in biology seeking a quiet and studious roommate.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 2,
   cleanliness_level: 5,
   noise_tolerance: 2,
   hygiene: 5,
   sleep_schedule: 2,
   guests_visitors: 1,
   work_study_from_home: 5,
   pet_tolerance: 4,
   shared_expenses: 4,
   study_habits: 5,
   room_privacy: 5,
   cooking_frequency: 3,
   food_sharing: 0,
   exercise_frequency: 2,
   personality_type: 2,
   shared_living_space_use: 2,
   room_temperature_preference: 4,
   decorating_style: 3,
 },
},
{
 user_id: 8,
 match_score: 68,
 profile: {
   name: "Henry Wilson",
   bio: "Outdoor enthusiast who enjoys hiking and camping, prefers an active household.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 3,
   cleanliness_level: 3,
   noise_tolerance: 4,
   hygiene: 3,
   sleep_schedule: 3,
   guests_visitors: 3,
   work_study_from_home: 2,
   pet_tolerance: 5,
   shared_expenses: 2,
   study_habits: 2,
   room_privacy: 3,
   cooking_frequency: 4,
   food_sharing: 1,
   exercise_frequency: 4,
   personality_type: 3,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 2,
 },
},
{
 user_id: 9,
 match_score: 83,
 profile: {
   name: "Isabella Brown",
   bio: "Recent graduate and aspiring writer looking for a supportive living environment.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 1,
   cleanliness_level: 4,
   noise_tolerance: 3,
   hygiene: 4,
   sleep_schedule: 3,
   guests_visitors: 2,
   work_study_from_home: 3,
   pet_tolerance: 3,
   shared_expenses: 3,
   study_habits: 4,
   room_privacy: 4,
   cooking_frequency: 2,
   food_sharing: 1,
   exercise_frequency: 2,
   personality_type: 2,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 3,
 },
},
{
 user_id: 10,
 match_score: 76,
 profile: {
   name: "Jack Taylor",
   bio: "Music producer looking for a creative and energetic roommate.",
   gender_preference: 0,
   smoking_preference: 1,
   alcohol_usage: 2,
   cleanliness_level: 3,
   noise_tolerance: 5,
   hygiene: 3,
   sleep_schedule: 2,
   guests_visitors: 4,
   work_study_from_home: 4,
   pet_tolerance: 2,
   shared_expenses: 3,
   study_habits: 1,
   room_privacy: 2,
   cooking_frequency: 3,
   food_sharing: 2,
   exercise_frequency: 1,
   personality_type: 4,
   shared_living_space_use: 4,
   room_temperature_preference: 2,
   decorating_style: 4,
 },
},
{
 user_id: 11,
 match_score: 88,
 profile: {
   name: "Karen White",
   bio: "Graphic designer and art lover seeking a stylish and organized home.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 1,
   cleanliness_level: 5,
   noise_tolerance: 3,
   hygiene: 4,
   sleep_schedule: 3,
   guests_visitors: 2,
   work_study_from_home: 3,
   pet_tolerance: 4,
   shared_expenses: 4,
   study_habits: 3,
   room_privacy: 4,
   cooking_frequency: 3,
   food_sharing: 1,
   exercise_frequency: 3,
   personality_type: 2,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 5,
 },
},
{
 user_id: 12,
 match_score: 70,
 profile: {
   name: "Liam Harris",
   bio: "College student majoring in engineering, enjoys quiet study time.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 2,
   cleanliness_level: 3,
   noise_tolerance: 3,
   hygiene: 3,
   sleep_schedule: 2,
   guests_visitors: 2,
   work_study_from_home: 4,
   pet_tolerance: 3,
   shared_expenses: 2,
   study_habits: 5,
   room_privacy: 4,
   cooking_frequency: 2,
   food_sharing: 1,
   exercise_frequency: 2,
   personality_type: 3,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 2,
 },
},
{
 user_id: 13,
 match_score: 95,
 profile: {
   name: "Mia Clark",
   bio: "Professional photographer seeking a creative and tidy roommate.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 1,
   cleanliness_level: 5,
   noise_tolerance: 2,
   hygiene: 5,
   sleep_schedule: 3,
   guests_visitors: 1,
   work_study_from_home: 4,
   pet_tolerance: 5,
   shared_expenses: 4,
   study_habits: 3,
   room_privacy: 5,
   cooking_frequency: 3,
   food_sharing: 0,
   exercise_frequency: 3,
   personality_type: 1,
   shared_living_space_use: 2,
   room_temperature_preference: 4,
   decorating_style: 4,
 },
},
{
 user_id: 14,
 match_score: 80,
 profile: {
   name: "Nathan Lewis",
   bio: "Entrepreneur and startup enthusiast looking for a motivated roommate.",
   gender_preference: 0,
   smoking_preference: 1,
   alcohol_usage: 2,
   cleanliness_level: 4,
   noise_tolerance: 4,
   hygiene: 4,
   sleep_schedule: 2,
   guests_visitors: 3,
   work_study_from_home: 5,
   pet_tolerance: 3,
   shared_expenses: 4,
   study_habits: 3,
   room_privacy: 3,
   cooking_frequency: 4,
   food_sharing: 1,
   exercise_frequency: 3,
   personality_type: 3,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 3,
 },
},
{
 user_id: 15,
 match_score: 73,
 profile: {
   name: "Olivia Walker",
   bio: "Budding chef who loves experimenting in the kitchen, seeks a food-friendly roommate.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 2,
   cleanliness_level: 3,
   noise_tolerance: 4,
   hygiene: 3,
   sleep_schedule: 3,
   guests_visitors: 3,
   work_study_from_home: 2,
   pet_tolerance: 4,
   shared_expenses: 2,
   study_habits: 2,
   room_privacy: 3,
   cooking_frequency: 5,
   food_sharing: 2,
   exercise_frequency: 2,
   personality_type: 4,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 2,
 },
},
{
 user_id: 16,
 match_score: 82,
 profile: {
   name: "Peter Young",
   bio: "Musician and DJ looking for a vibrant and dynamic living space.",
   gender_preference: 0,
   smoking_preference: 1,
   alcohol_usage: 3,
   cleanliness_level: 3,
   noise_tolerance: 5,
   hygiene: 3,
   sleep_schedule: 2,
   guests_visitors: 4,
   work_study_from_home: 4,
   pet_tolerance: 2,
   shared_expenses: 3,
   study_habits: 1,
   room_privacy: 2,
   cooking_frequency: 3,
   food_sharing: 2,
   exercise_frequency: 1,
   personality_type: 4,
   shared_living_space_use: 4,
   room_temperature_preference: 2,
   decorating_style: 4,
 },
},
{
 user_id: 17,
 match_score: 89,
 profile: {
   name: "Quinn Martinez",
   bio: "Marketing professional who enjoys social gatherings and collaborative living.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 2,
   cleanliness_level: 4,
   noise_tolerance: 4,
   hygiene: 4,
   sleep_schedule: 3,
   guests_visitors: 3,
   work_study_from_home: 3,
   pet_tolerance: 4,
   shared_expenses: 4,
   study_habits: 3,
   room_privacy: 3,
   cooking_frequency: 3,
   food_sharing: 1,
   exercise_frequency: 3,
   personality_type: 3,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 3,
 },
},
{
 user_id: 18,
 match_score: 77,
 profile: {
   name: "Rachel Garcia",
   bio: "Photographer and traveler seeking a flexible and adventurous roommate.",
   gender_preference: 0,
   smoking_preference: 1,
   alcohol_usage: 3,
   cleanliness_level: 3,
   noise_tolerance: 4,
   hygiene: 3,
   sleep_schedule: 2,
   guests_visitors: 3,
   work_study_from_home: 2,
   pet_tolerance: 3,
   shared_expenses: 2,
   study_habits: 2,
   room_privacy: 3,
   cooking_frequency: 4,
   food_sharing: 2,
   exercise_frequency: 2,
   personality_type: 4,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 2,
 },
},
{
 user_id: 19,
 match_score: 84,
 profile: {
   name: "Samuel King",
   bio: "Software developer who enjoys coding and quiet evenings at home.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 1,
   cleanliness_level: 4,
   noise_tolerance: 3,
   hygiene: 4,
   sleep_schedule: 3,
   guests_visitors: 2,
   work_study_from_home: 5,
   pet_tolerance: 4,
   shared_expenses: 4,
   study_habits: 4,
   room_privacy: 4,
   cooking_frequency: 3,
   food_sharing: 1,
   exercise_frequency: 2,
   personality_type: 2,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 3,
 },
},
{
 user_id: 20,
 match_score: 71,
 profile: {
   name: "Tina Lopez",
   bio: "Fashion designer with a love for modern aesthetics and organized spaces.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 2,
   cleanliness_level: 4,
   noise_tolerance: 3,
   hygiene: 4,
   sleep_schedule: 3,
   guests_visitors: 2,
   work_study_from_home: 3,
   pet_tolerance: 3,
   shared_expenses: 3,
   study_habits: 3,
   room_privacy: 4,
   cooking_frequency: 2,
   food_sharing: 1,
   exercise_frequency: 2,
   personality_type: 3,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 4,
 },
},
{
 user_id: 21,
 match_score: 86,
 profile: {
   name: "Umar Patel",
   bio: "Graduate student in chemistry, enjoys a structured and tidy living space.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 1,
   cleanliness_level: 5,
   noise_tolerance: 2,
   hygiene: 5,
   sleep_schedule: 3,
   guests_visitors: 1,
   work_study_from_home: 4,
   pet_tolerance: 4,
   shared_expenses: 4,
   study_habits: 5,
   room_privacy: 5,
   cooking_frequency: 3,
   food_sharing: 0,
   exercise_frequency: 3,
   personality_type: 2,
   shared_living_space_use: 2,
   room_temperature_preference: 4,
   decorating_style: 3,
 },
},
{
 user_id: 22,
 match_score: 69,
 profile: {
   name: "Victoria Rivera",
   bio: "Aspiring writer who enjoys late-night conversations and creative projects.",
   gender_preference: 0,
   smoking_preference: 1,
   alcohol_usage: 3,
   cleanliness_level: 3,
   noise_tolerance: 4,
   hygiene: 3,
   sleep_schedule: 4,
   guests_visitors: 3,
   work_study_from_home: 2,
   pet_tolerance: 3,
   shared_expenses: 2,
   study_habits: 3,
   room_privacy: 3,
   cooking_frequency: 4,
   food_sharing: 2,
   exercise_frequency: 2,
   personality_type: 4,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 2,
 },
},
{
 user_id: 23,
 match_score: 94,
 profile: {
   name: "William Scott",
   bio: "Architect with a passion for design and a preference for minimalist living.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 1,
   cleanliness_level: 5,
   noise_tolerance: 2,
   hygiene: 5,
   sleep_schedule: 3,
   guests_visitors: 1,
   work_study_from_home: 4,
   pet_tolerance: 4,
   shared_expenses: 4,
   study_habits: 4,
   room_privacy: 5,
   cooking_frequency: 3,
   food_sharing: 0,
   exercise_frequency: 3,
   personality_type: 2,
   shared_living_space_use: 2,
   room_temperature_preference: 4,
   decorating_style: 5,
 },
},
{
 user_id: 24,
 match_score: 79,
 profile: {
   name: "Xavier Adams",
   bio: "Graphic novelist seeking a creative and collaborative living space.",
   gender_preference: 0,
   smoking_preference: 1,
   alcohol_usage: 2,
   cleanliness_level: 3,
   noise_tolerance: 4,
   hygiene: 3,
   sleep_schedule: 2,
   guests_visitors: 3,
   work_study_from_home: 3,
   pet_tolerance: 3,
   shared_expenses: 3,
   study_habits: 3,
   room_privacy: 3,
   cooking_frequency: 4,
   food_sharing: 2,
   exercise_frequency: 2,
   personality_type: 4,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 3,
 },
},
{
 user_id: 25,
 match_score: 80,
 profile: {
   name: "Yvonne Nelson",
   bio: "Marketing specialist and book club member looking for a friendly roommate.",
   gender_preference: 0,
   smoking_preference: 0,
   alcohol_usage: 2,
   cleanliness_level: 4,
   noise_tolerance: 3,
   hygiene: 4,
   sleep_schedule: 3,
   guests_visitors: 2,
   work_study_from_home: 3,
   pet_tolerance: 4,
   shared_expenses: 3,
   study_habits: 3,
   room_privacy: 4,
   cooking_frequency: 3,
   food_sharing: 1,
   exercise_frequency: 2,
   personality_type: 3,
   shared_living_space_use: 3,
   room_temperature_preference: 3,
   decorating_style: 3,
 },
},


  
 ]);
 const [filterValues, setFilterValues] = useState({});
 //const location = useLocation();
 //const currentPath = location.pathname;


 // console.log(filterValues);


 // Fetch matches from backend


 const getMatches = async () => {
   try {
     const response = await apiClient.post("/matches/run");
     setMatches(response.data.matches);
   } catch (e) {
     console.log(e);
   }
 };


 // Apply filters to matches
 const applyFilters = (matches, filters) => {
   return matches.filter((match) => {
     const { profile } = match;


     // Gender Preference (Example: Allow both "No Preference" and the same gender)
     if (filters["Gender Preference"] && filters["Gender Preference"] != 2) {
       if (
         profile.gender_preference !== parseInt(filters["Gender Preference"])
       ) {
         return false;
       }
     }


     // Smoking Preference
     if (filters["Smoking Preference"] !== undefined) {
       if (
         profile.smoking_preference !== parseInt(filters["Smoking Preference"])
       ) {
         return false;
       }
     }


     // // Alcohol Usage
     // if (filters["Alcohol Usage"] !== undefined) {
     //   if (profile.alcohol_usage !== parseInt(filters["Alcohol Usage"])) {
     //     return false;
     //   }
     // }


     // Cleanliness Level within 1 level of preference
     if (filters["Cleanliness Level"] !== undefined) {
       if (
         Math.abs(
           profile.cleanliness_level - parseInt(filters["Cleanliness Level"])
         ) > 1
       ) {
         return false;
       }
     }


     // // Noise Tolerance within 1 level of preference
     // if (filters["Noise Tolerance"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.noise_tolerance - parseInt(filters["Noise Tolerance"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Hygiene (Allowing a range: within 1 level)
     // if (filters["Hygiene"] !== undefined) {
     //   if (Math.abs(profile.hygiene - parseInt(filters["Hygiene"])) > 1) {
     //     return false;
     //   }
     // }


     // // Sleep Schedule (Exact match required)
     // if (filters["Sleep Schedule"] !== undefined) {
     //   if (profile.sleep_schedule !== parseInt(filters["Sleep Schedule"])) {
     //     return false;
     //   }
     // }


     // // Guests/Visitors (Allowing a range: within 1 level)
     // if (filters["Guests/Visitors"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.guests_visitors - parseInt(filters["Guests/Visitors"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Work/Study From Home (Allowing a range)
     // if (filters["Work/Study From Home"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.work_study_from_home -
     //         parseInt(filters["Work/Study From Home"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Pet Tolerance (Allowing a range)
     // if (filters["Pet Tolerance"] !== undefined) {
     //   if (
     //     Math.abs(profile.pet_tolerance - parseInt(filters["Pet Tolerance"])) >
     //     1
     //   ) {
     //     return false;
     //   }
     // }


     // // Shared Expenses (Allowing a range)
     // if (filters["Shared Expenses"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.shared_expenses - parseInt(filters["Shared Expenses"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Study Habits (Allowing a range)
     // if (filters["Study Habits"] !== undefined) {
     //   if (
     //     Math.abs(profile.study_habits - parseInt(filters["Study Habits"])) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Room Privacy (Allowing a range)
     // if (filters["Room Privacy"] !== undefined) {
     //   if (
     //     Math.abs(profile.room_privacy - parseInt(filters["Room Privacy"])) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Cooking Frequency (Allowing a range)
     // if (filters["Cooking Frequency"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.cooking_frequency - parseInt(filters["Cooking Frequency"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Food Sharing - Not strict
     // if (filters["Food Sharing"] !== undefined) {
     //   const userPreference = parseInt(filters["Food Sharing"]);
     //   const matchPreference = profile.food_sharing;
     //   // Let some differences in food sharing pass, but could notify user on result card.
     // }


     // // Exercise Frequency (Allowing a range)
     // if (filters["Exercise Frequency"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.exercise_frequency - parseInt(filters["Exercise Frequency"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Personality Type (Allow +-1 flexibility)
     // if (filters["Personality Type"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.personality_type - parseInt(filters["Personality Type"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Shared Living Space Use (Allowing a range)
     // if (filters["Shared Living Space Use"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.shared_living_space_use -
     //         parseInt(filters["Shared Living Space Use"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Room Temperature Preference (Allow +-1 flexibility)
     // if (filters["Room Temperature Preference"] !== undefined) {
     //   if (
     //     Math.abs(
     //       profile.room_temperature_preference -
     //         parseInt(filters["Room Temperature Preference"])
     //     ) > 1
     //   ) {
     //     return false;
     //   }
     // }


     // // Decorating Style (Less strict)
     // if (filters["Decorating Style"] !== undefined) {
     //   if (
     //     profile.decorating_style !== parseInt(filters["Decorating Style"])
     //   ) {
     //     // Allow through, just notify in match card
     //   }
     // }


     return true;
   });
 };


 const filteredMatches = useMemo(
   () => applyFilters(matches, filterValues),
   [matches, filterValues]
 );


 // useDisclosure for Modals
 const {
   isOpen: isLoginOpen,
   onOpen: onLoginOpen,
   onOpenChange: onLoginOpenChange,
 } = useDisclosure();


 const {
   isOpen: isRegisterOpen,
   onOpen: onRegisterOpen,
   onOpenChange: onRegisterOpenChange,
 } = useDisclosure();


 return (
  
     <main className="h-full px-4 pt-16">




       {/* Login Modal */}
       <LoginModal
        isOpen={isLoginOpen}
         onOpen={onLoginOpen}
         onOpenChange={onLoginOpenChange}
         setUserData={setUserData}
         isUser={isUser}
       />
       {/* Register Modal */}
       <RegisterModal
         isOpen={isRegisterOpen}
         onOpen={onRegisterOpen}
         onOpenChange={onRegisterOpenChange}
         setUserData={setUserData}
       />
      
       <Nav
         onRegisterOpen={onRegisterOpen}
         onLoginOpen={onLoginOpen}
         userData={userData}
         setUserData={setUserData}
         isUser={isUser}
       />


       <Routes>
         <Route path="/profile" element={<Profile />} />
         <Route
           path="*"
           element={
           <div className="flex h-[calc(100vh-4rem)] overflow-y-auto">
           {/* Side Filter */}
           <div className="sticky top-0 h-full">
             <SideFilter
               filterValues={filterValues}
               setFilterValues={setFilterValues}
               userCharacteristics={userData?.characteristics}
             />
           </div>
           {/* Roommate Results */}
           <div className="flex-grow px-4">
             <h2 className="text-lg font-bold">Roommate Results</h2>
             <RoommateResults matches={filteredMatches} />
           </div>
         </div>
       }
       />
         {/* Add more routes as needed */}
       </Routes>
     
     </main>
  
 );
}


export default App;
