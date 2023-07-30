import React, { useEffect } from 'react';
import { Outlet, Navigate } from 'react-router-dom';
const PrivateRoute = () => {
  const isLoggedIn = !!localStorage.getItem('token');
  useEffect(() => {
    if (!isLoggedIn) {
      console.log('User not authenticated. Redirecting to login...');
    }
  }, [isLoggedIn]);
  return isLoggedIn ? <Outlet /> : <Navigate to='/login' />;
};
export default PrivateRoute;
