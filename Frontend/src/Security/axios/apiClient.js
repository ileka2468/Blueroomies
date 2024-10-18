import axios from "axios";

const apiClient = axios.create({
  baseURL: "https://blueroomies.com/api",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

const axiosclient = axios.create({
  baseURL: "https://blueroomies.com/api",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

// see if a request is already refreshing
let isRefreshing = false;
let refreshSubscribers = [];

const subscribeTokenRefresh = (callback) => {
  refreshSubscribers.push(callback);
};

const onRefreshed = (newToken) => {
  refreshSubscribers.map((callback) => callback(newToken));
  refreshSubscribers = [];
};

// Interceptor for adding access token to outoging requests.
apiClient.interceptors.request.use(
  (config) => {
    const noTokenPaths = ["/auth/login", "/auth/refresh-token"];
    const shouldExclude = noTokenPaths.some((path) =>
      config.url.includes(path)
    );

    if (!shouldExclude) {
      const token = localStorage.getItem("accessToken");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor for constantly checking and setting access token from auth response header
// apiClient.interceptors.response.use(
//   (response) => {
//     const newToken = response.headers["authorization"];

//     if (newToken) {
//       console.log("Set New Acess Token:" + response.headers);
//       const tokenPart = newToken.split(" ")[1];
//       localStorage.setItem("accessToken", tokenPart);
//     }
//     return response;
//   },
//   (error) => {
//     return Promise.reject(error);
//   }
// );

// Interceptor for refreshing expired access tokens
apiClient.interceptors.response.use(null, async (error) => {
  const originalRequest = error.config;

  if (error.response.status === 401 && !originalRequest._retry) {
    if (isRefreshing) {
      return new Promise((resolve) => {
        subscribeTokenRefresh((newToken) => {
          originalRequest.headers.Authorization = `Bearer ${newToken}`;
          resolve(apiClient(originalRequest));
        });
      });
    }

    originalRequest._retry = true;
    isRefreshing = true;

    try {
      const refreshTokenResponse = await axiosclient.post(
        "/auth/refresh-token"
      );

      const newToken = refreshTokenResponse.headers["authorization"];

      if (newToken) {
        console.log("Set New Acess Token:" + newToken);
        const tokenPart = newToken.split(" ")[1];
        localStorage.setItem("accessToken", tokenPart);
        onRefreshed(tokenPart);

        //  retry original request with the new token
        originalRequest.headers.Authorization = `Bearer ${tokenPart}`;
        return apiClient(originalRequest);
      }
    } catch (refreshError) {
      localStorage.removeItem("accessToken");
      return Promise.reject(refreshError);
    } finally {
      isRefreshing = false;
    }
  }
  return Promise.reject(error);
});

export default apiClient;
