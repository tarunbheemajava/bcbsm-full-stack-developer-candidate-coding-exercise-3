import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { toast } from 'react-toastify';
import { createReviewAPI } from '../services/user';

const ReviewForm = ({ setReviews }) => {
  const [isLoading, setIsLoading] = useState(false);
  return (
    <div className='container'>
      <div className='row justify-content-center'>
        <div className='col-md-6'>
          <Formik
            initialValues={{ comment: '', rating: '' }}
            validationSchema={Yup.object({
              comment: Yup.string().required('Comment is required'),
              rating: Yup.number()
                .typeError('Please enter a valid number')
                .required('Rating is required')
                .min(1, 'Rating must be at least 1')
                .max(10, 'Rating must be at most 10'),
            })}
            onSubmit={async (values, { setSubmitting }) => {
              setSubmitting(false);
              setIsLoading(true);
              try {
                const resp = await createReviewAPI(values);
                setReviews((prev) => [resp.data, ...prev]);
                setIsLoading(false);
                toast.success('Review added successfully.');
              } catch (error) {
                console.error(error);
                toast.error('Something went wrong. please try again.');
                setIsLoading(false);
              }
            }}
          >
            {({ isSubmitting }) => (
              <Form>
                <div className='form-group row mt-4'>
                  <label htmlFor='comment' className='col-sm-3 col-form-label'>
                    Comment
                  </label>
                  <div className='col-sm-9'>
                    <Field
                      component='textarea'
                      name='comment'
                      className='form-control'
                      placeholder='Enter comment'
                    />
                    <ErrorMessage
                      name='comment'
                      component='div'
                      className='text-danger d-flex'
                    />
                  </div>
                </div>
                <div className='form-group row mt-4'>
                  <label htmlFor='rating' className='col-sm-3 col-form-label'>
                    Rating
                  </label>
                  <div className='col-sm-9'>
                    <Field
                      type='text'
                      name='rating'
                      className='form-control'
                      placeholder='Rating'
                    />
                    <ErrorMessage
                      name='rating'
                      component='div'
                      className='text-danger d-flex'
                    />
                  </div>
                </div>
                <div className='form-group row'>
                  <div className='col-sm-10 offset-sm-2 mt-4'>
                    <button
                      type='submit'
                      className='btn btn-primary'
                      disabled={isSubmitting || isLoading}
                    >
                      Submit
                    </button>
                  </div>
                </div>
              </Form>
            )}
          </Formik>
        </div>
      </div>
    </div>
  );
};

export default ReviewForm;
