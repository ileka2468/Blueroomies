import React from "react";
import PropTypes from "prop-types";


import {
 Navbar,
 NavbarBrand,
 NavbarContent,
 NavbarItem,
 Link as NULink,
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


import { Link } from 'react-router-dom';
import HomeLogo from "./HomeLogo";
import { useAxios } from "../../../Security/axios/AxiosProvider";
import Cookies from "js-cookie";
import { useLocation } from "react-router-dom";
//import LoginModal from '/Users/hambati/Desktop/se452groupproject/se452-group-project/Frontend/src/Components/Authentication/LoginModal'; // Import your modal component
//import RegisterModal from '/Users/hambati/Desktop/se452groupproject/se452-group-project/Frontend/src/Components/Authentication/RegisterModal';


const Nav = ({
 onLoginOpen,
 onRegisterOpen,
 userData = {},
 setUserData,
 isUser,
}) => {
 const [isMenuOpen, setIsMenuOpen] = React.useState(false);
 //const [isLoginModalOpen, setIsLoginModalOpen] = React.useState(false);
 //const [isRegisterModalOpen, setIsRegisterModalOpen] = React.useState(false);
 const apiClient = useAxios();
 //const navigate = useNavigate();


 const menuItems = [
   "Login",
   "Register",
   "Profile",
   "Find Roommates",
   "Agreements",
   "Past Matches",
   "Log Out",
 ];


 const location = useLocation(); // Get the current location


 // Define the links in an array for easier mapping
 const links = [
   { path: '/find-roommates', label: 'Find Roommates' },
   { path: '/agreements', label: 'Agreements' },
   { path: '/past-matches', label: 'Past Matches' },
 ];


 const logout = async () => {
   const response = await apiClient.post("/auth/logout");


   if (response.status === 200) {
     Cookies.remove("refresh_token");
     localStorage.removeItem("accessToken");
     setUserData({});
     console.log("Logged out");
   }
 };


 /*const handleLoginClick = () => {
   setIsLoginModalOpen(true);
 };


 const handleRegisterClick = () => {
   setIsRegisterModalOpen(true);
 };*/




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
       <NavbarBrand>
         <HomeLogo />
       </NavbarBrand>
     </NavbarContent>


     <NavbarContent className="hidden sm:flex gap-4" justify="center">
     {links.map((link) => (
       <NavbarItem key={link.path}>
         <NULink
           color="foreground"
           href={link.path}
           aria-current={location.pathname === link.path ? 'page' : undefined}
           className={`font-medium ${location.pathname === link.path ? 'text-blue-500 font-bold' : ''}`}
         >
           {link.label}
         </NULink>
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
       )}
     </NavbarContent>


     <NavbarMenu>
       {menuItems.map((item, index) => (
         <NavbarMenuItem key={`${item}-${index}`}>
           <Link
             color={
               index === 2
                 ? "primary"
                 : index === menuItems.length - 1
                 ? "danger"
                 : "foreground"
             }
             className="w-full"
             href="#"
             size="lg"
           >
             {item}
           </Link>
         </NavbarMenuItem>
       ))}
     </NavbarMenu>
   </Navbar>
 );
};


Nav.propTypes = {
 onLoginOpen: PropTypes.bool,
 onRegisterOpen: PropTypes.bool,
 userData: PropTypes.shape({
   username: PropTypes.string,
   pfp: PropTypes.string,
 }), 
 setUserData: PropTypes.func.isRequired, 
 isUser: PropTypes.bool.isRequired,
};


export default Nav;
