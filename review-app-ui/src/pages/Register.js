import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { toast } from 'react-toastify';
import { registerUserAPI, USER_ROLE } from '../services/user';

const Register = () => {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  return (
    <div className='container mt-5'>
      <h3>Register Page</h3>
      <div
        className='row justify-content-center'
        style={{ minHeight: '100vh' }}
      >
        <div className='col-md-6'>
          <Formik
            initialValues={{
              name: '',
              username: '',
              password: '',
              role: 'USER',
            }}
            validationSchema={Yup.object({
              name: Yup.string().required('Name is required'),
              username: Yup.string().required('Username is required'),
              password: Yup.string()
                .min(6, 'Password must be at least 6 characters')
                .required('Password is required'),
            })}
            onSubmit={async (values, { setSubmitting }) => {
              setSubmitting(false);
              setIsLoading(true);
              try {
                const resp = await registerUserAPI(values);
                console.log(resp.status);
                if (resp.status !== 200) {
                  setIsLoading(false);
                  return toast.error('User already exist.');
                }
                localStorage.setItem('user', JSON.stringify(resp.data?.user));
                localStorage.setItem('token', resp.data?.jwt);
                setIsLoading(false);
                toast.success('Register successfully.');
                navigate('/review');
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
                  <label htmlFor='name' className='col-sm-3 col-form-label'>
                    Name
                  </label>
                  <div className='col-sm-9'>
                    <Field
                      type='text'
                      name='name'
                      className='form-control'
                      placeholder='Enter name'
                    />
                    <ErrorMessage
                      name='name'
                      component='div'
                      className='text-danger d-flex'
                    />
                  </div>
                </div>
                <div className='form-group row mt-4'>
                  <label htmlFor='username' className='col-sm-3 col-form-label'>
                    Username
                  </label>
                  <div className='col-sm-9'>
                    <Field
                      type='text'
                      name='username'
                      className='form-control'
                      placeholder='Enter username'
                    />
                    <ErrorMessage
                      name='username'
                      component='div'
                      className='text-danger d-flex'
                    />
                  </div>
                </div>
                <div className='form-group row mt-4'>
                  <label htmlFor='password' className='col-sm-3 col-form-label'>
                    Password
                  </label>
                  <div className='col-sm-9'>
                    <Field
                      type='password'
                      name='password'
                      className='form-control'
                      placeholder='Password'
                    />
                    <ErrorMessage
                      name='password'
                      component='div'
                      className='text-danger d-flex'
                    />
                  </div>
                </div>
                <div className='text-end'>
                  <Link to={'/'}>Login here</Link>
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

export default Register;
