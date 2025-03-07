import React, { useState } from 'react';
import { Card, Row, Col } from 'react-bootstrap';
import { NavLink, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';  // Make sure you have installed js-cookie

import Breadcrumb from '../../../layouts/AdminLayout/Breadcrumb';

const SignIn = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Username:", username, "Password:", password);
  
    if (!username || !password) {
      setError("Username and password are required");
      return;
    }
  
    try {
      const response = await axios.post(
        'http://localhost:8080/auth/login',
        { username, password },
        { headers: { 'Content-Type': 'application/json' } }
      );
  
      if (response.data.token) {
        Cookies.set('authToken', response.data.token, { expires: 1 });
        navigate('/dashboard'); // Fix: Ensure it redirects correctly
        window.location.reload();  // Force page reload

      }
    } catch (err) {
      setError('Invalid username or password');
      console.error('Error during login:', err);
    }
  };
  
  return (
    <React.Fragment>
      <Breadcrumb />
      <div className="auth-wrapper">
        <div className="auth-content">
          <div className="auth-bg">
            <span className="r" />
            <span className="r s" />
            <span className="r s" />
            <span className="r" />
          </div>
          <Card className="borderless">
            <Row className="align-items-center">
              <Col>
                <Card.Body className="text-center">
                  <div className="mb-4">
                    <i className="feather icon-lock auth-icon" />
                  </div>
                  <h3 className="mb-4">Sign In</h3>
                  {error && <div className="alert alert-danger">{error}</div>} {/* Show error message */}
                  <form onSubmit={handleSubmit}>
                    <div className="input-group mb-3">
                      <input
                        type="text"
                        className="form-control"
                        placeholder="Username"
                        style={{ minWidth: '300px' }}
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                      />
                    </div>
                    <div className="input-group mb-4">
                      <input
                        type="password"
                        className="form-control"
                        placeholder="Password"
                        style={{ minWidth: '300px' }}
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                      />
                    </div>
                    <button className="btn btn-primary mb-4" style={{ minWidth: '300px' }} type="submit">
                      Sign In
                    </button>
                  </form>
                  <p className="mb-2">
                    Don't have an account?{' '}
                    <NavLink to={'/basic/user'} className="f-w-400">
                      Sign Up
                    </NavLink>
                  </p>
                </Card.Body>
              </Col>
            </Row>
          </Card>
        </div>
      </div>
    </React.Fragment>
  );
};

export default SignIn;







// import React, { useState } from 'react';
// import { Card, Row, Col } from 'react-bootstrap';
// import { NavLink, useNavigate } from 'react-router-dom';
// import axios from 'axios';
// import Cookies from 'js-cookie';

// import Breadcrumb from '../../../layouts/AdminLayout/Breadcrumb';

// const SignIn = () => {
//   const [username, setUsername] = useState('');
//   const [password, setPassword] = useState('');
//   const [error, setError] = useState('');
//   const navigate = useNavigate();

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     console.log("Username:", username, "Password:", password); // Debugging

//     if (!username || !password) {
//       setError("Username and password are required");
//       return;
//     }

//     try {
//       const response = await axios.post(
//         'http://localhost:8080/login',
//         { username, password },
//         { headers: { 'Content-Type': 'application/json' } }
//       );

//       if (response.data.token) {
//         Cookies.set('authToken', response.data.token, { expires: 1 });
//         navigate('/views/ui-elements/basic/Basicoumapping'); // Redirect to dashboard where Breadcrumb is shown
//       }
//     } catch (err) {
//       setError("Invalid username or password");
//       console.error("Error during login:", err);
//     }
//   };

//   return (
//     <React.Fragment>
//       <Breadcrumb />
//       <div className="auth-wrapper">
//         <div className="auth-content">
//           <div className="auth-bg">
//             <span className="r" />
//             <span className="r s" />
//             <span className="r s" />
//             <span className="r" />
//           </div>
//           <Card className="borderless">
//             <Row className="align-items-center">
//               <Col>
//                 <Card.Body className="text-center">
//                   <div className="mb-4">
//                     <i className="feather icon-lock auth-icon" />
//                   </div>
//                   <h3 className="mb-4">Sign In</h3>
//                   {error && <div className="alert alert-danger">{error}</div>}
//                   <form onSubmit={handleSubmit}>
//                     <div className="input-group mb-3">
//                       <input
//                         type="text"
//                         className="form-control"
//                         placeholder="Username"
//                         style={{ minWidth: '300px' }}
//                         value={username}
//                         onChange={(e) => setUsername(e.target.value)}
//                       />
//                     </div>
//                     <div className="input-group mb-4">
//                       <input
//                         type="password"
//                         className="form-control"
//                         placeholder="Password"
//                         style={{ minWidth: '300px' }}
//                         value={password}
//                         onChange={(e) => setPassword(e.target.value)}
//                       />
//                     </div>
//                     <button className="btn btn-primary mb-4" style={{ minWidth: '300px' }} type="submit">
//                       Sign In
//                     </button>
//                   </form>
//                   <p className="mb-2">
//                     Don't have an account?{' '}
//                     <NavLink to={'/basic/user'} className="f-w-400">
//                       Sign Up
//                     </NavLink>
//                   </p>
//                 </Card.Body>
//               </Col>
//             </Row>
//           </Card>
//         </div>
//       </div>
//     </React.Fragment>
//   );
// };

// export default SignIn;
