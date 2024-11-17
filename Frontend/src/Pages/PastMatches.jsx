import React, { useState, useEffect } from "react";
import { useAxios } from "../Security/axios/AxiosProvider";
import { Button, Spinner } from "@nextui-org/react";
import { History } from "lucide-react";
import RoommateResults from "../Components/RoommateResults";

// Cache
const characteristicsCache = new Map();

const PastMatchesPage = () => {
  const apiClient = useAxios();
  const [matches, setMatches] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const fetchCharacteristics = async (userId) => {
    if (characteristicsCache.has(userId)) {
      return characteristicsCache.get(userId);
    }

    try {
      const response = await apiClient.get(
        `/profiles/characteristics/${userId}`
      );
      const characteristics = response.data;
      characteristicsCache.set(userId, characteristics);
      return characteristics;
    } catch (error) {
      console.error(
        `Error fetching characteristics for user ${userId}:`,
        error
      );
      return null;
    }
  };

  const fetchPastMatches = async () => {
    try {
      setIsLoading(true);
      const response = await apiClient.get("/matches/me");

      // Fetch characteristics for each match
      const matchData = response.data.matches;
      const matchPromises = matchData.map(async (match) => {
        const characteristics = await fetchCharacteristics(match.id);

        return {
          user_id: match.id,
          match_score: Number(match.matchscore),
          time_stamp: match.timestamp,
          profile: {
            ...characteristics,
            name: `${match.firstname} ${match.lastname}`,
            username: match.username,
            avatarUrl: match.pfp,
          },
        };
      });

      const transformedMatches = await Promise.all(matchPromises);

      // Sort matches by timestamp, most recent first
      const sortedMatches = transformedMatches.sort(
        (a, b) => new Date(b.time_stamp) - new Date(a.time_stamp)
      );

      setMatches(sortedMatches);
    } catch (error) {
      console.error("Error fetching past matches:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchPastMatches();
  }, []);

  return (
    <div className="p-6">
      <div className="mb-6">
        <div className="flex items-center gap-4">
          <History className="w-8 h-8 text-primary" />
          <h1 className="text-2xl font-bold">Past Matches History</h1>
        </div>
        <p className="text-gray-600 mt-2">
          View all your previous roommate matches and their details.
        </p>
      </div>

      {isLoading ? (
        <div className="flex justify-center items-center h-64">
          <Spinner size="lg" />
        </div>
      ) : (
        <>
          <div className="mb-4 flex justify-between items-center">
            <span className="text-gray-600">
              {matches.length} total matches found
            </span>
            <Button
              color="primary"
              onClick={fetchPastMatches}
              startContent={<History size={16} />}
              isLoading={isLoading}
            >
              Refresh History
            </Button>
          </div>

          <RoommateResults
            matches={matches}
            isLoading={isLoading}
            onRefresh={fetchPastMatches}
            isPastMatches={true}
          />
        </>
      )}
    </div>
  );
};

export default PastMatchesPage;
