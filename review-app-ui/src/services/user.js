import axios from 'axios';

const baseURL = 'http://localhost:8092';

export const USER_ROLE = 'USER';

export const getUser = () => {
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  if (user) {
    return user;
  } else {
    return {};
  }
};

const authHeader = () => {
  const token = localStorage.getItem('token');
  if (token) {
    return { Authorization: 'Bearer ' + token };
  } else {
    return {};
  }
};

export const registerUserAPI = (data) => {
  return axios.post(`${baseURL}/api/v1/auth/register`, data);
};

export const loginUserAPI = (data) => {
  return axios.post(`${baseURL}/api/v1/auth/authenticate`, data);
};

export const createReviewAPI = (payload) => {
  return axios.post(`${baseURL}/review`, payload, { headers: authHeader() });
};

export const getReviewAPI = () => {
  return axios.get(`${baseURL}/review`, { headers: authHeader() });
};

export const logoutUser = () => {
  localStorage.clear();
};
