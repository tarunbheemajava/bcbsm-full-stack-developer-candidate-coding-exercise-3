import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { toast } from 'react-toastify';
import { loginUserAPI } from '../services/user';

const Login = () => {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  return (
    <div className='container mt-5'>
      <h3>Login Page</h3>

      <div
        className='row justify-content-center'
        style={{ minHeight: '100vh' }}
      >
        <div className='col-md-6'>
          <Formik
            initialValues={{ username: '', password: '' }}
            validationSchema={Yup.object({
              username: Yup.string().required('Username is required'),
              password: Yup.string()
                .min(6, 'Password must be at least 6 characters')
                .required('Password is required'),
            })}
            onSubmit={async (values, { setSubmitting }) => {
              setSubmitting(false);
              setIsLoading(true);
              try {
                const resp = await loginUserAPI(values);
                if (resp.status !== 200) {
                  setIsLoading(false);
                  return toast.error('Username or password is invalid.');
                }
                localStorage.setItem('user', JSON.stringify(resp.data?.user));
                localStorage.setItem('token', resp.data?.jwt);
                setIsLoading(false);
                toast.success('Login successfully.');
                navigate('/review');
              } catch (error) {
                console.error(error);
                toast.error('username or password incorrect . please try again.');
                setIsLoading(false);
              }
            }}
          >
            {({ isSubmitting }) => (
              <Form>
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
                  <Link to={'/register'}>Create a new account</Link>
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

export default Login;
