import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { USER_ROLE, getUser, logoutUser, getReviewAPI } from '../services/user';
import ReviewForm from './ReviewForm';

const Review = () => {
  const navigate = useNavigate();
  const { name, role } = getUser();
  const [reviews, setReviews] = useState([]);

  const fetchReview = async () => {
    try {
      const resp = await getReviewAPI();
      setReviews(resp.data ?? []);
    } catch (error) {
      console.log(error);
      setReviews([]);
    }
  };

  useEffect(() => {
    fetchReview();
  }, []);

  const handleLogout = () => {
    logoutUser();
    navigate('/');
  };

  function formatDate(dateTimeString) {
    const dateObj = new Date(dateTimeString);
    const options = {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: 'numeric',
      minute: 'numeric',
      second: 'numeric',
      fractionalSecondDigits: 2,
    };
    return new Intl.DateTimeFormat('en-US', options).format(dateObj);
  }

  return (
    <div className='container'>
      <div className='text-end'>
        <div className='flex  items-center justify-center'>
          <span className='mx-1 text-capitalize'>Hey, {name}</span>
          <button className='btn btn-danger' onClick={handleLogout}>
            Logout
          </button>
        </div>
      </div>
      <h3>Review</h3>
      {role === USER_ROLE && (
        <div className='text-end'>
          <ReviewForm setReviews={setReviews} />
        </div>
      )}
      <div className='row justify-content-center mt-4'>
        {reviews.length ? (
          reviews.map((review) => (
            <div key={review.id} className='col-md-8 my-2'>
              <div className='card'>
                <p>Rating: {review.rating}</p>
                <p>Comment: {review.comment}</p>
                <p>Date: {formatDate(review.createdAt)}</p>
                {role !== USER_ROLE && <p>Username: {review.username}</p>}
              </div>
            </div>
          ))
        ) : (
          <p>No Review Found</p>
        )}
      </div>
    </div>
  );
};

export default Review;
