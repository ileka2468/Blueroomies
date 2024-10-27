import { StrictMode } from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import FindRoommatesPage from "./Pages/FindRoommatesPage.jsx";
import MessagesPage from "./Pages/MessagesPage.jsx";
import AgreementsPage from "./Pages/AgreementsPage.jsx";
import NotificationsPage from "./Pages/NotificationsPage.jsx";
import Root from "./Components/root/Root.jsx";
import "./index.css";
import { AxiosProvider } from "./Security/axios/AxiosProvider.jsx";
import Profile from "./Components/Profile/Profile.jsx";
import HomePage from "./Pages/HomePage.jsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    children: [{ path: "find-roommates", element: <FindRoommatesPage /> },
            { path: "messages", element: <MessagesPage />},
            { path: "profile", element: <Profile />},
            { path: "", element: <HomePage />},
            { path: "agreements", element: <AgreementsPage />},
            { path: "notifications", element: <NotificationsPage />},
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <StrictMode>
    <AxiosProvider>
      <RouterProvider router={router} />
    </AxiosProvider>
  </StrictMode>
);
