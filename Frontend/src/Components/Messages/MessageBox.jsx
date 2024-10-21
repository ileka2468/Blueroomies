import React from "react";
import {
  User,
  Input,
  Divider,
  Listbox,
  Card,
  CardBody,
  Select,
  SelectItem,
  ScrollShadow,
} from "@nextui-org/react";
import { ListboxReceived } from "./ListboxReceived";
import { ListboxSent } from "./ListboxSent";
import { users } from "./data";

const MessageBox = () => {
  return (
    /* Pulls test data from data.js and populates the Select component with User components. Updates visually via renderValue mapping */
    <div>
      <Card>
        <CardBody className="items-center">Message Center</CardBody>
        <CardBody className="items-center">
          <Select
            aria-label="idk"
            className="w-[500px] pb-5"
            items={users}
            placeholder="Select a User"
            renderValue={(items) => {
              return items.map((item) => (
                <div key={item.key} className="flex items-center gap-2">
                  <User name={item.name} avatarProps={item.avatarProps} />
                  <div className="flex flex-col">
                    <span>{item.data.name}</span>
                  </div>
                </div>
              ));
            }}
          >
            {(user) => (
              <SelectItem aria-label="idk" textValue="idk" key={user.id}>
                <User name={user.name} avatarProps={user.avatarProps} />
              </SelectItem>
            )}
          </Select>
        </CardBody>
      </Card>

      {/* All messages for selected chat show in historical order */}
      <Card>
        <CardBody className="items-center">
          <ScrollShadow>
            <Listbox
              aria-label="idk"
              className="w-[500px] h-[400px] size-fit py-8"
            >
              <ListboxReceived>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Curabitur at rhoncus nisl, sit amet sodales dolor.
              </ListboxReceived>

              <ListboxSent>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Curabitur at rhoncus nisl, sit amet sodales dolor. Ut eu euismod
                sem. Vestibulum quam orci, maximus at nisl vitae, faucibus
                tempor metus.
              </ListboxSent>

              <ListboxReceived>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Curabitur at rhoncus nisl, sit amet sodales dolor.
              </ListboxReceived>

              <ListboxSent>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Curabitur at rhoncus nisl, sit amet sodales dolor. Ut eu euismod
                sem. Vestibulum quam orci, maximus at nisl vitae, faucibus
                tempor metus.
              </ListboxSent>

              <ListboxReceived>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Curabitur at rhoncus nisl, sit amet sodales dolor.
              </ListboxReceived>

              <ListboxSent>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Curabitur at rhoncus nisl, sit amet sodales dolor.
              </ListboxSent>

              <ListboxReceived>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Curabitur at rhoncus nisl, sit amet sodales dolor. Ut eu euismod
                sem. Vestibulum quam orci, maximus at nisl vitae, faucibus
                tempor metus.
              </ListboxReceived>

              <ListboxSent>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                Curabitur at rhoncus nisl, sit amet sodales dolor.
              </ListboxSent>
            </Listbox>
          </ScrollShadow>
        </CardBody>
        <Divider />
        <CardBody className="items-center">
          <Input
            className="w-[500px]"
            label="Message"
            placeholder="Enter a message..."
          />
        </CardBody>
      </Card>
    </div>
  );
};

export default MessageBox;
