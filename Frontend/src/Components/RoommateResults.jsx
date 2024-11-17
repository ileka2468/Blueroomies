import React, { useState, useEffect } from "react";
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Button,
  Avatar,
  Spinner,
  Progress,
  Chip,
} from "@nextui-org/react";
import {
  MessageCircle,
  FileText,
  RefreshCw,
  Home,
  Clock,
  Heart,
  Coffee,
  Music,
  Book,
  Users,
} from "lucide-react";
import { useAxios } from "../Security/axios/AxiosProvider";

const userDetailsCache = new Map();

const characteristicCategories = {
  lifestyle: {
    title: "Lifestyle",
    icon: <Home size={18} />,
    characteristics: [
      "smoking_preference",
      "alcohol_usage",
      "sleep_schedule",
      "exercise_frequency",
    ],
  },
  social: {
    title: "Social Preferences",
    icon: <Users size={18} />,
    characteristics: [
      "guests_visitors",
      "personality_type",
      "shared_living_space_use",
    ],
  },
  habits: {
    title: "Daily Habits",
    icon: <Coffee size={18} />,
    characteristics: [
      "cleanliness_level",
      "hygiene",
      "cooking_frequency",
      "food_sharing",
    ],
  },
  academic: {
    title: "Work & Study",
    icon: <Book size={18} />,
    characteristics: ["work_study_from_home", "study_habits"],
  },
  living: {
    title: "Living Preferences",
    icon: <Heart size={18} />,
    characteristics: [
      "noise_tolerance",
      "pet_tolerance",
      "room_privacy",
      "room_temperature_preference",
      "decorating_style",
      "shared_expenses",
    ],
  },
};

const RoommateResults = ({ matches, isLoading, onRefresh }) => {
  const apiClient = useAxios();
  const [userDetails, setUserDetails] = useState({});
  const [loadingDetails, setLoadingDetails] = useState(false);
  const [expandedCard, setExpandedCard] = useState(null);

  useEffect(() => {
    const fetchUserDetails = async () => {
      if (!matches || matches.length === 0) return;

      setLoadingDetails(true);
      try {
        const detailsPromises = matches.map(async (match) => {
          // Check cache first
          if (userDetailsCache.has(match.user_id)) {
            return {
              userId: match.user_id,
              details: userDetailsCache.get(match.user_id),
            };
          }

          // Fetch if not in cache
          try {
            const response = await apiClient.get(
              `/profiles/user-details/${match.user_id}`
            );
            const details = response.data;
            userDetailsCache.set(match.user_id, details);
            return { userId: match.user_id, details };
          } catch (error) {
            console.error(
              `Error fetching details for user ${match.user_id}:`,
              error
            );
            return { userId: match.user_id, details: null };
          }
        });

        const results = await Promise.all(detailsPromises);

        // Update state
        const newDetails = {};
        results.forEach(({ userId, details }) => {
          if (details) {
            newDetails[userId] = details;
          }
        });

        setUserDetails(newDetails);
      } catch (error) {
        console.error("Error fetching user details:", error);
      } finally {
        setLoadingDetails(false);
      }
    };

    // fetch when matches change
    fetchUserDetails();
  }, [matches]);

  const getCharacteristicLabel = (key) => {
    return key
      .split("_")
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
      .join(" ");
  };

  const renderCharacteristicValue = (key, value) => {
    const numericKeys = [
      "cleanliness_level",
      "noise_tolerance",
      "hygiene",
      "guests_visitors",
      "work_study_from_home",
      "pet_tolerance",
      "shared_expenses",
      "study_habits",
      "room_privacy",
      "cooking_frequency",
      "exercise_frequency",
      "shared_living_space_use",
      "room_temperature_preference",
    ];

    if (numericKeys.includes(key)) {
      return (
        <div className="w-full">
          <div className="flex justify-between text-sm mb-1">
            <span>{getCharacteristicLabel(key)}</span>
            <span className="text-primary">{value}/5</span>
          </div>
          <Progress
            value={value * 20}
            size="sm"
            color="primary"
            className="max-w-md"
          />
        </div>
      );
    }

    if (typeof value === "boolean") {
      return (
        <div className="flex justify-between items-center">
          <span>{getCharacteristicLabel(key)}</span>
          <Chip color={value ? "success" : "default"} variant="flat" size="sm">
            {value ? "Yes" : "No"}
          </Chip>
        </div>
      );
    }

    return (
      <div className="flex justify-between items-center">
        <span>{getCharacteristicLabel(key)}</span>
        <span className="text-primary">{value}</span>
      </div>
    );
  };

  const renderCharacteristicGroup = (category, characteristics) => {
    const relevantCharacteristics = Object.entries(characteristics).filter(
      ([key]) => category.characteristics.includes(key)
    );

    if (relevantCharacteristics.length === 0) return null;

    return (
      <div className="mb-6 last:mb-0">
        <div className="flex items-center gap-2 mb-3">
          {category.icon}
          <h4 className="text-md font-semibold">{category.title}</h4>
        </div>
        <div className="space-y-4">
          {relevantCharacteristics.map(([key, value]) => (
            <div key={key}>{renderCharacteristicValue(key, value)}</div>
          ))}
        </div>
      </div>
    );
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <Button
          color="primary"
          startContent={<RefreshCw size={16} />}
          onClick={onRefresh}
          isLoading={loadingDetails}
        >
          {loadingDetails ? "Loading Details..." : "Refresh Matches"}
        </Button>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {!matches || matches.length === 0 ? (
          <p className="col-span-full text-center text-gray-500">
            No matches found. Click "Find Matches" on the Find Roomates page to
            start searching.
          </p>
        ) : (
          matches.map((match, index) => {
            const profile = match.profile || {};
            const details = userDetails[match.user_id] || {};
            const fullName =
              details.firstName && details.lastName
                ? `${details.firstName} ${details.lastName}`
                : `Match #${index + 1}`;
            const isExpanded = expandedCard === index;

            return (
              <Card
                key={index}
                className={`hover:shadow-xl transition-shadow ${
                  isExpanded ? "col-span-2" : ""
                }`}
                isPressable
                onPress={() => setExpandedCard(isExpanded ? null : index)}
              >
                <CardHeader className="flex items-center space-x-4 bg-gradient-to-r from-primary-100 to-primary-50">
                  <Avatar
                    src={details.pfp || `/api/placeholder/150/150`}
                    name={fullName}
                    size="lg"
                    className="border-2 border-white"
                  />
                  <div className="flex-grow">
                    <h3 className="text-xl font-semibold">{fullName}</h3>
                    <div className="flex items-center gap-2">
                      <Progress
                        value={Math.round(match.match_score * 100)}
                        size="sm"
                        color="success"
                        className="max-w-md"
                        label="Match Score"
                        showValueLabel={true}
                      />
                    </div>
                  </div>
                  {match.time_stamp && (
                    <Chip
                      startContent={<Clock size={14} />}
                      variant="flat"
                      size="sm"
                      className="hidden sm:flex"
                    >
                      {new Date(match.time_stamp).toLocaleDateString()}
                    </Chip>
                  )}
                </CardHeader>

                <CardBody className="py-4">
                  {Object.entries(characteristicCategories).map(
                    ([key, category]) => (
                      <div key={key}>
                        {renderCharacteristicGroup(category, profile)}
                      </div>
                    )
                  )}
                </CardBody>

                <CardFooter className="flex justify-end space-x-2 border-t">
                  <Button
                    variant="flat"
                    color="primary"
                    size="sm"
                    startContent={<MessageCircle size={16} />}
                  >
                    Message
                  </Button>
                  <Button
                    color="success"
                    size="sm"
                    startContent={<FileText size={16} />}
                  >
                    Create Agreement
                  </Button>
                </CardFooter>
              </Card>
            );
          })
        )}
      </div>
    </div>
  );
};

export default RoommateResults;
