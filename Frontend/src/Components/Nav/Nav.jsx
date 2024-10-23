import React from "react";
import PropTypes from "prop-types";
import { Badge } from "@nextui-org/react";
import {
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
  DropdownItem,
  DropdownTrigger,
  Dropdown,
  DropdownMenu,
  Avatar,
  NavbarMenuItem,
  NavbarMenuToggle,
  Button,
  NavbarMenu,
} from "@nextui-org/react";

import { Link } from "react-router-dom";
import HomeLogo from "./HomeLogo";
import { useAxios } from "../../Security/axios/AxiosProvider";
import Cookies from "js-cookie";
import { useLocation } from "react-router-dom";
import { NotificationIcon } from "../Dashboard/Icons/NotificationIcon";
import { MailIcon } from "../Authentication/MailIcon";

const Nav = ({
  onLoginOpen,
  onRegisterOpen,
  userData = {},
  setUserData,
  isUser,
}) => {
  const [isMenuOpen, setIsMenuOpen] = React.useState(false);
  const apiClient = useAxios();
  const location = useLocation(); // Get the current location

  const logout = async () => {
    const response = await apiClient.post("/auth/logout");

    if (response.status === 200) {
      Cookies.remove("refresh_token");
      localStorage.removeItem("accessToken");
      setUserData({});
      console.log("Logged out");
    }
  };

  const isAsynFunction = (func) => {
    return func.constructor.name == "AsyncFunction";
  };

  const invokeMenuItemAction = async (action) => {
    if (isAsynFunction(action)) {
      await action();
    } else {
      action();
    }
  };

  const links = [
    { path: "/find-roommates", label: "Find Roommates" },
    { path: "/agreements", label: "Agreements" },
    { path: "/past-matches", label: "Past Matches" },
    { path: "/messages", label: "Messages" },
    { path: "/notifications", label: "Notifications" },
  ];

  const menuItems = [
    { label: "Login", action: onLoginOpen },
    { label: "Register", action: onRegisterOpen },
    { label: "Profile", path: "/profile" },
    ...links,
    { label: "Log Out", action: logout },
  ];

  return (
    <Navbar
      className="fixed top-0 left-0 w-full z-50"
      position="sticky"
      maxWidth="xl"
      onMenuOpenChange={setIsMenuOpen}
    >
      <NavbarContent>
        <NavbarMenuToggle
          aria-label={isMenuOpen ? "Close menu" : "Open menu"}
          className="sm:hidden"
        />
        <Link to={"/"}>
          <NavbarBrand>
            <HomeLogo />
          </NavbarBrand>
        </Link>
      </NavbarContent>

      <NavbarContent className="hidden sm:flex gap-4" justify="center">
        {links.map((link) => (
          <NavbarItem key={link.path}>
            <Link
              color="foreground"
              to={link.path}
              aria-current={
                location.pathname === link.path ? "page" : undefined
              }
              className={`font-medium ${
                location.pathname === link.path ? "text-blue-500 font-bold" : ""
              }`}
            >
              {link.label}
            </Link>
          </NavbarItem>
        ))}
      </NavbarContent>
      <NavbarContent justify="end">
        <NavbarItem className="hidden lg:flex">
          {!isUser && (
            <>
              <Button className="mx-3" color="primary" onPress={onLoginOpen}>
                Login
              </Button>

              <Button
                onPress={onRegisterOpen}
                color="primary"
                href="signup"
                variant="flat"
              >
                Sign Up
              </Button>
            </>
          )}
        </NavbarItem>

        {userData.username && (
          <>
            <Badge color="primary" content={5} shape="circle">
              <Link to="/messages">
                <MailIcon size="1.5em" />
              </Link>
            </Badge>

            <Badge color="danger" content={1} shape="circle">
              <Link to="/notifications" /* This will do notifications stuff*/>
                <NotificationIcon />
              </Link>
            </Badge>

            <Dropdown placement="bottom-end" backdrop="blur">
              <DropdownTrigger>
                <Avatar
                  isBordered
                  as="button"
                  className="transition-transform"
                  color="primary"
                  name={userData.username}
                  size="sm"
                  src={userData.pfp}
                />
              </DropdownTrigger>
              <DropdownMenu aria-label="Profile Actions" variant="flat">
                <DropdownItem
                  key="profile"
                  className="h-14 gap-2"
                  textValue={`Signed in as ${userData.username}`}
                >
                  <p className="font-semibold">Signed in as</p>
                  <p className="font-semibold">{userData.username}</p>
                </DropdownItem>

                <DropdownItem
                  key="manage_profile"
                  as={Link} // Use the Link component
                  to="/profile" // Set the path to /profile
                >
                  Manage Profile
                </DropdownItem>
                <DropdownItem
                  onPress={() => logout()}
                  key="logout"
                  color="danger"
                >
                  Log Out
                </DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </>
        )}
      </NavbarContent>

      <NavbarMenu>
        {menuItems.map((item, index) => {
          if (item.hasOwnProperty("action")) {
            return (
              <NavbarMenuItem key={`${item.label}-${index}`}>
                <Link
                  className="w-full"
                  size="lg"
                  onClick={() => invokeMenuItemAction(item.action)}
                >
                  {item.label}
                </Link>
              </NavbarMenuItem>
            );
          } else {
            return (
              <NavbarMenuItem key={`${item.label}-${index}`}>
                <Link className="w-full" size="lg" to={item.path}>
                  {item.label}
                </Link>
              </NavbarMenuItem>
            );
          }
        })}
      </NavbarMenu>
    </Navbar>
  );
};

Nav.propTypes = {
  onLoginOpen: PropTypes.func,
  onRegisterOpen: PropTypes.func,
  userData: PropTypes.shape({
    username: PropTypes.string,
    pfp: PropTypes.string,
  }),
  setUserData: PropTypes.func.isRequired,
  isUser: PropTypes.bool.isRequired,
};

export default Nav;
