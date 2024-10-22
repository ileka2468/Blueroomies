import React from "react";

const RoommateResults = ({ matches }) => {
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
    </div>
  );
};

export default RoommateResults;
