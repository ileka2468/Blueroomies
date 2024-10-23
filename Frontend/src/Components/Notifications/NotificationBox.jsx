import React from "react";
import {
  User,
  Divider,
  Listbox,
  ListboxItem,
  Card,
  CardBody,
  ScrollShadow,
} from "@nextui-org/react";
import { newNotifications } from "./data";
import { pastNotifications } from "./data";

const NotificationBox = () => {
  return (
    <div>
      <Card>
        <CardBody className="items-center">Notifications</CardBody>
      </Card>

      <Card>
        <CardBody className="items-center">
          <ScrollShadow>
            <Listbox
              aria-label="idk"
              className="w-[500px] h-[600px] size-fit py-8"
            >
              <ListboxItem>
                <span className="flex justify-center text-lg">
                  New Notification(s)
                </span>
                <Divider />
              </ListboxItem>

              {/* All Unread Notifs */}
              {newNotifications.map((newNotification) => (
                <ListboxItem>
                  <User
                    name={newNotification.name}
                    description={newNotification.date}
                    avatarProps=""
                  ></User>
                  <span className="flex justify-end">
                    {newNotification.info}
                  </span>
                  <Divider />
                </ListboxItem>
              ))}

              <ListboxItem>
                <span className="flex justify-center text-lg">
                  Past Notification(s)
                </span>
                <Divider />
              </ListboxItem>

              {/* All Read Notifs */}
              {pastNotifications.map((pastNotification) => (
                <ListboxItem>
                  <User
                    name={pastNotification.name}
                    description={pastNotification.date}
                    avatarProps=""
                  ></User>
                  <span className="flex justify-end">
                    {pastNotification.info}
                  </span>
                  <Divider />
                </ListboxItem>
              ))}
            </Listbox>
          </ScrollShadow>
        </CardBody>
      </Card>
    </div>
  );
};

export default NotificationBox;
