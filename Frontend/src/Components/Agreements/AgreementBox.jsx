import React from "react";
import {
  User,
  Input,
  Divider,
  Listbox,
  ListboxItem,
  Card,
  CardBody,
  Select,
  SelectItem,
  ScrollShadow,
  Button,
} from "@nextui-org/react";
import { users } from "./data";

const AgreementBox = () => {
  return (
    <div>
      <Card>
        <CardBody className="items-center">Agreement Center</CardBody>
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
              <SelectItem aria-label="idk" key={user.id}>
                <User name={user.name} avatarProps={user.avatarProps} />
              </SelectItem>
            )}
          </Select>
        </CardBody>
      </Card>

      <Card>
        <CardBody className="items-center">
          <ScrollShadow>
            <Listbox aria-label="idk" className="w-[500px] h-[400px] size-fit py-8">
              <ListboxItem>This is an agreement.</ListboxItem>
            </Listbox>
          </ScrollShadow>
        </CardBody>
        <Divider />
        <CardBody className="items-center">
          <div className="grid grid-cols-3">
            <Button variant="solid" color="success">Accept</Button>
            <div></div>
            <Button variant="solid" color="danger">Decline</Button>
          </div>
        </CardBody>
      </Card>
    </div>
  );
};

export default AgreementBox;
