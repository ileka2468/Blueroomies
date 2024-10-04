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
    return(
        
        
        /* Pulls test data from data.js and populates the Select component with User components. Updates visually via renderValue mapping */
        <div>
            <Card>
                <CardBody className="items-center">
                    Message Center
                </CardBody>
                <CardBody className="items-center">
                    <Select className="w-[500px] pb-5" items={users} placeholder="Select a User" renderValue={(items) => { return items.map((item) => (
                        <div key={item.key} className="flex items-center gap-2">
                            <User name={item.name} avatarProps={item.avatarProps}/>
                            <div className="flex flex-col">
                                <span>{item.data.name}</span>
                            </div>
                        </div>
                        
                    ));
                    }}
                    >   
                        {(user) => (
                            <SelectItem key={user.id}>
                                <User name={user.name} avatarProps={user.avatarProps}/>
                            </SelectItem>
                        )}
                    </Select>
                </CardBody>
            </Card>

            {/* All messages for selected chat show in historical order */}
            <Card>
                <CardBody className="items-center">
                    <ScrollShadow>
                        <Listbox className="w-[500px] h-[400px] size-fit py-8">
                    
                            <ListboxReceived>
                                So this is a test of a very long message with a lot of text inside of it to see how it overflows and to see if the text will continue to shrink even smaller every time a new line is added
                            </ListboxReceived>

                            

                            <ListboxSent>
                                Wow that is a lot of text i guess i should check to make sure it works for both variants of listboxitem
                            </ListboxSent>

                            <ListboxReceived>
                                So this is a test of a very long message with a lot of text inside of it to see how it overflows and to see if the text will continue to shrink even smaller every time a new line is added
                            </ListboxReceived>

                            

                            <ListboxSent>
                                Wow that is a lot of text i guess i should check to make sure it works for both variants of listboxitem
                            </ListboxSent>

                            <ListboxReceived>
                                So this is a test of a very long message with a lot of text inside of it to see how it overflows and to see if the text will continue to shrink even smaller every time a new line is added
                            </ListboxReceived>

                            

                            <ListboxSent>
                                Wow that is a lot of text i guess i should check to make sure it works for both variants of listboxitem
                            </ListboxSent>

                            <ListboxReceived>
                                So this is a test of a very long message with a lot of text inside of it to see how it overflows and to see if the text will continue to shrink even smaller every time a new line is added
                            </ListboxReceived>

                            

                            <ListboxSent>
                                Wow that is a lot of text i guess i should check to make sure it works for both variants of listboxitem
                            </ListboxSent>
                        
                        
                        </Listbox>
                    </ScrollShadow>
                </CardBody>
                <Divider />
                <CardBody className="items-center">
                    <Input className="w-[500px]" label="Message" placeholder="Enter a message..."/>
                </CardBody>
            </Card>
        </div>
    )
}

export default MessageBox;