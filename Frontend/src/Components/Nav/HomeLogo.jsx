import React from "react";

const HomeLogo = () => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      xmlnsXlink="http://www.w3.org/1999/xlink"
      viewBox="0 0 213 30"
      width="213"
      height="30"
      fill="none"
    >
      <g opacity="100%" mask="url(#mask_9prwcnmmc)">
        <text
          x="35"
          y="27"
          fontFamily="Rubik"
          fontSize="26"
          fill="#000"
          letterSpacing="-0.6"
          fontWeight="normal"
          fontStyle="normal"
        >
          BlueRoomies
        </text>
        <defs>
          <mask id="mask_9prwcnmmc">
            <rect x="35" y="0" width="178" height="30" fill="white"></rect>
          </mask>
        </defs>
      </g>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 576 512"
        width="30"
        height="30"
        fill="#000"
        x="2.5"
        y="0"
        opacity="100%"
      >
        <path d="M280.37 148.26L96 300.11V464a16 16 0 0 0 16 16l112.06-.29a16 16 0 0 0 15.92-16V368a16 16 0 0 1 16-16h64a16 16 0 0 1 16 16v95.64a16 16 0 0 0 16 16.05L464 480a16 16 0 0 0 16-16V300L295.67 148.26a12.19 12.19 0 0 0-15.3 0zM571.6 251.47L488 182.56V44.05a12 12 0 0 0-12-12h-56a12 12 0 0 0-12 12v72.61L318.47 43a48 48 0 0 0-61 0L4.34 251.47a12 12 0 0 0-1.6 16.9l25.5 31A12 12 0 0 0 45.15 301l235.22-193.74a12.19 12.19 0 0 1 15.3 0L530.9 301a12 12 0 0 0 16.9-1.6l25.5-31a12 12 0 0 0-1.7-16.93z"></path>
      </svg>
      <defs>
        <filter
          id="filter_dshadow_0_0_0_00000014"
          colorInterpolationFilters="sRGB"
          filterUnits="userSpaceOnUse"
        >
          <feFlood floodOpacity="0" result="bg-fix"></feFlood>
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="alpha"
          ></feColorMatrix>
          <feOffset dx="0" dy="0"></feOffset>
          <feGaussianBlur stdDeviation="0"></feGaussianBlur>
          <feComposite in2="alpha" operator="out"></feComposite>
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.08 0"
          ></feColorMatrix>
          <feBlend
            mode="normal"
            in2="bg-fix"
            result="bg-fix-filter_dshadow_0_0_0_00000014"
          ></feBlend>
          <feBlend
            in="SourceGraphic"
            in2="bg-fix-filter_dshadow_0_0_0_00000014"
            result="shape"
          ></feBlend>
        </filter>
        <style id="google">
          @import
          url(https://fonts.googleapis.com/css2?family=Rubik:ital,wght@0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap);
        </style>
        <style id="brand"></style>
      </defs>
    </svg>
  );
};

export default HomeLogo;
