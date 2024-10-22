import React from "react";
import { Button } from "@nextui-org/react";
import { useAxios } from "../Security/axios/AxiosProvider";

const RoommateResults = ({ matches }) => {
  const apiClient = useAxios();
  console.log(matches);
  return (
    <div>
      {matches.length === 0 ? (
        <p>No matches found based on your provided filters.</p>
      ) : (
        matches.map((match) => (
          <div key={match.user_id} className="border p-4 mb-4 rounded">
            <h3 className="text-lg font-bold">{match.profile.name}</h3>
            <p>{match.profile.bio}</p>
            <p>Match Score: {match.match_score}%</p>
          </div>
        ))
      )}
      <Button
        onClick={async () => {
          const response = await apiClient.post("/matches/run");
          console.log(response);
        }}
      >
        Find Matches
      </Button>
    </div>
  );
};

export default RoommateResults;
