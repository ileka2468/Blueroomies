import React from "react";
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Button,
  Avatar,
} from "@nextui-org/react";
import { MessageCircle, FileText } from "react-feather";
import { useAxios } from "../Security/axios/AxiosProvider";

const RoommateResults = ({ matches }) => {
  const apiClient = useAxios();

  const handleSendMessage = (userId) => {
    console.log("Send message to user:", userId);
  };

  const handleCreateAgreement = (userId) => {
    console.log("Create agreement with user:", userId);
  };

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      {matches.length === 0 ? (
        <p className="col-span-full text-center text-gray-500">
          No matches found based on your provided filters.
        </p>
      ) : (
        matches.map((match) => (
          <Card
            key={match.user_id}
            className="hover:shadow-xl transition-shadow"
          >
            <CardHeader className="flex items-center space-x-4 bg-gray-50">
              <Avatar
                src={match.profile.avatarUrl}
                name={match.profile.name}
                size="lg"
                color="primary"
                className="border border-gray-200"
              />
              <div>
                <h3 className="text-xl font-semibold">{match.profile.name}</h3>
                <p className="text-gray-500">
                  Match Score: {match.match_score}%
                </p>
              </div>
            </CardHeader>

            {/* Card Body with Bio and Characteristics */}
            <CardBody className="py-4">
              <p className="mb-4 text-gray-700">{match.profile.bio}</p>
              <div className="grid grid-cols-2 gap-4 text-sm text-gray-600">
                <div>
                  <span className="font-medium">Budget:</span> $
                  {match.profile.budget}
                </div>
                <div>
                  <span className="font-medium">Cleanliness:</span>{" "}
                  {match.profile.cleanliness}
                </div>
                <div>
                  <span className="font-medium">Smoking:</span>{" "}
                  {match.profile.smoking}
                </div>
                <div>
                  <span className="font-medium">Pets:</span>{" "}
                  {match.profile.pets}
                </div>
                <div>
                  <span className="font-medium">Noise Level:</span>{" "}
                  {match.profile.noise}
                </div>
                <div>
                  <span className="font-medium">Proximity:</span>{" "}
                  {match.profile.proximity}
                </div>
              </div>
            </CardBody>

            {/* Card Footer with Action Buttons */}
            <CardFooter className="flex justify-end space-x-2">
              <Button
                variant="flat"
                color="primary"
                size="sm"
                startContent={<MessageCircle size={16} />}
                onClick={() => handleSendMessage(match.user_id)}
              >
                Message
              </Button>
              <Button
                color="success"
                size="sm"
                startContent={<FileText size={16} />}
                onClick={() => handleCreateAgreement(match.user_id)}
              >
                Create Agreement
              </Button>
            </CardFooter>
          </Card>
        ))
      )}
    </div>
  );
};

export default RoommateResults;
