import React, { useEffect, useRef, useState } from "react";
import { useOutletContext } from "react-router-dom";
import PreferencesForm from "../Components/Preferences/PreferenceForm.jsx"; 
import heroImage from "../assets/homepageimg.png";
import formSvg from "../assets/form.svg";
import findroommateSvg from "../assets/findroommate.svg";
import connectSvg from "../assets/connect.svg";
import Dashboard from "../Pages/Dashboard.jsx";

const HomePage = () => {
  const { isUser } = useOutletContext();
  
  // State for steps visibility
  const [stepsVisible, setStepsVisible] = useState(false);
  const stepsRef = useRef();

  useEffect(() => {
    const stepsObserver = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setStepsVisible(true);
          stepsObserver.disconnect(); // Stop observing after it becomes visible
        }
      },
      { threshold: 0.1 }
    );

    if (stepsRef.current) {
      stepsObserver.observe(stepsRef.current);
    }

    return () => {
      if (stepsRef.current) {
        stepsObserver.unobserve(stepsRef.current);
      }
    };
  }, []);

  const steps = [
    {
      title: "Fill Out the Preferences Form",
      description: "Complete the form to specify your roommate preferences and requirements.",
      graphic: formSvg,
    },
    {
      title: "Find Roommates",
      description: "Our algorithm searches for and displays potential matches based on your preferences.",
      graphic: findroommateSvg,
    },
    {
      title: "Connect and Agree",
      description: "Communicate and finalize agreements with your future roommate.",
      graphic: connectSvg,
    },
  ];

  if (isUser) {
    return <Dashboard />; // Render the Dashboard component if the user is logged in
  }

  return (
    <div className="flex flex-col items-center justify-center" style={{ paddingTop: '5px' }}>
      {/* Hero Image */}
      <div className="relative w-full">
        <img 
          src={heroImage} 
          alt="Hero" 
          className="w-full h-auto" 
          style={{ maxHeight: '400px', objectFit: 'cover', opacity: 0.5 }} // image opacity
        />
  
        {/* Blue Filter Overlay */}
        <div 
          className="absolute inset-0" 
          style={{ backgroundColor: 'rgba(0, 111, 238, 0.1)'}} 
        ></div>
  
        {/* Centered Text Over Hero */}
        <div className="absolute inset-0 flex items-center justify-center">
          <h1
            className="text-4xl font-bold text-center"
            style={{
              fontFamily: 'Lora', 
              color: '#161717',
              textShadow: '0 0 5px rgba(255, 255, 255, 0.7), 0 0 10px rgba(255, 255, 255, 0.5)',
              animation: 'textMove 2s ease-in-out infinite alternate'
            }}
          >
            Discover connections that just click—with a click!
          </h1>
        </div>
      </div>
  
      <div className="flex flex-col items-center mt-8 p-4 text-center">
        <h2 className="text-xl font-bold mb-4">About RoommateFinder</h2>
        <p className="mb-4">
          At <strong>RoommateFinder</strong>, we believe that finding the perfect roommate should 
          be a seamless and enjoyable experience. Our mission is to connect individuals based on 
          their preferences, lifestyles, and personalities to create harmonious living arrangements.
        </p>
        <p className="mb-4">
          <strong>Join Us</strong><br />
          Want to see how easy it is to find your ideal roommate with RoommateFinder?<br />
          Sign up today and take the first step towards a more fulfilling living experience!
        </p>
      </div>
      
      {/* Steps Section */}
      <div className="flex flex-col items-center mt-8" ref={stepsRef}>
        <h2 className="text-xl font-bold mb-4" style={{ fontFamily: 'Rubik' }}>How It Works:</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 w-full max-w-4xl">
          {steps.map((step, index) => (
            <div
              key={index}
              className={`border border-gray-300 p-6 rounded-lg shadow-md transition-transform duration-700 ${stepsVisible ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'}`}
              style={{ height: '300px' }} // Adjust the height as needed
            >
              <img src={step.graphic} alt={step.title} className="h-16 w-16 mx-auto mb-4" />
              <p className="text-center text-lg font-bold" style={{ fontFamily: 'Rubik' }}>{step.title}</p>
              <p className="text-center text-sm mt-2" style={{ fontFamily: 'Rubik' }}>{step.description}</p>
            </div>
          ))}
        </div>
      </div>

      {/* Google Fonts Import */}
      <style>
        {`
          @import url('https://fonts.googleapis.com/css2?family=Lora:wght@400&family=Rubik:wght@300;400;500;600;700&display=swap');
        `}
      </style>
    </div>
  );
};

export default HomePage;
