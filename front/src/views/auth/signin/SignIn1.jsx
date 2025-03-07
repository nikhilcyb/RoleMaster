// import React from 'react';
// import { Card, Button, Alert } from 'react-bootstrap';
// import { NavLink, Link } from 'react-router-dom';

// import Breadcrumb from '../../../layouts/AdminLayout/Breadcrumb';

// import { CopyToClipboard } from 'react-copy-to-clipboard';

// import AuthLogin from './JWTLogin';

// const Signin1 = () => {
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
//           <Card className="borderless text-center">
//             <Card.Body>
//               <div className="mb-4">
//                 <i className="feather icon-unlock auth-icon" />
//               </div>
//               <AuthLogin />
//               <p className="mb-2 text-muted">
//                 Forgot password?{' '}
//                 <NavLink to={'#'} className="f-w-400">
//                   Reset
//                 </NavLink>
//               </p>
//               <p className="mb-0 text-muted">
//                 Don’t have an account?{' '}
//                 <NavLink to="/auth/signup-1" className="f-w-400">
//                   {/* User */}
//                   Organization User Mapping

//                 </NavLink>
//               </p>
//               <Alert variant="primary" className="text-start mt-3">
//                 User:
//                 <CopyToClipboard text="info@codedthemes.com">
//                   <Button variant="outline-primary" as={Link} to="#" className="badge mx-2 mb-2" size="sm">
//                     <i className="fa fa-user" /> info@codedthemes.com
//                   </Button>
//                 </CopyToClipboard>
//                 <br />
//                 Password:
//                 <CopyToClipboard text="123456">
//                   <Button variant="outline-primary" as={Link} to="#" className="badge mx-2" size="sm">
//                     <i className="fa fa-lock" /> 123456
//                   </Button>
//                 </CopyToClipboard>
//               </Alert>
//             </Card.Body>
//           </Card>
//         </div>
//       </div>
//     </React.Fragment>
//   );
// };

// export default Signin1;
// import React, { useState } from 'react';
// import { Card, Row, Col } from 'react-bootstrap';
// import { NavLink, useNavigate } from 'react-router-dom';
// import axios from 'axios';
// import Cookies from 'js-cookie';  // Make sure you have installed js-cookie

// import Breadcrumb from '../../../layouts/AdminLayout/Breadcrumb';

// const SignIn = () => {
//   const [username, setUsername] = useState('');
//   const [password, setPassword] = useState('');
//   const [error, setError] = useState('');
//   const navigate = useNavigate();

//   // Handle form submission
//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     try {
//       const response = await axios.post('http://localhost:8080/login', {
//         username: username,
//         password: password,
//       });

//       // Store the token in cookies if login is successful
//       if (response.data.token) {
//         Cookies.set('authToken', response.data.token, { expires: 1 }); // Store token in cookies for 1 day
//         // Redirect user to another page (for example, dashboard)
//         navigate('/dashboard/default');
//       }
//     } catch (err) {
//       setError('Invalid username or password');
//       console.error('Error during login', err);
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
//                   {error && <div className="alert alert-danger">{error}</div>} {/* Show error message */}
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
//                     <button   className="btn btn-primary mb-4" style={{ minWidth: '300px' }} type="submit">
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
