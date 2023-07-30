import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import Login from './pages/Login';
import Register from './pages/Register';
import Review from './pages/Review';
import PrivateRoute from './PrivateRoute';

const CloseButton = ({ closeToast }) => (
  <i onClick={closeToast} className='la la-close notifications-close' />
);

function App() {
  return (
    <div className='App'>
      <ToastContainer
        position='bottom-left'
        autoClose={2000}
        hideProgressBar={false}
        closeButton={<CloseButton />}
      />
      <Router>
        <Routes>
          <Route path='/' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='/' element={<PrivateRoute />}>
            <Route path='review' element={<Review />} />
          </Route>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
