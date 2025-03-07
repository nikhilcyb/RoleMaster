// import React, { useState, useEffect, useCallback } from "react";
// import { Card, Row, Col, Dropdown, Button, Form, Table } from "react-bootstrap";
// import Breadcrumb from "../../../layouts/AdminLayout/Breadcrumb";
// import apiClient from "../../../api/axios";
// import Cookies from 'js-cookie';
// import axios from 'axios';
// import { toast, ToastContainer } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';



// const axiosInstance = axios.create({
//   baseURL: 'http://localhost:8080',
//   headers: {
//     'Content-Type': 'application/json',
//   },
// });
// const Basicoumapping = () => {
//   const [organizationName, setOrganizationName] = useState("");
//   const [organizationId, setOrganizationId] = useState(null);
//   const [isActive, setIsActive] = useState("");
//   const [username, setUsername] = useState("");
//   const [usernameSuggestions, setUsernameSuggestions] = useState([]);
//   const [organizations, setOrganizations] = useState([]);
//   const [users, setUsers] = useState([]);
//   const [mappings, setMappings] = useState([]);
//   const [loading, setLoading] = useState(false);
//   const [error, setError] = useState(null);
//   const [success, setSuccess] = useState(null);
//   const [isSubmitting, setIsSubmitting] = useState(false);
//   const [showForm, setShowForm] = useState(false);


//   useEffect(() => {
//     const fetchMapping = async () => {

//       const token = Cookies.get('authToken'); // Retrieve token from cookies
//       console.log("token is ",token)
//       if (!token) {
//         // toast.error('No authentication token found. Please log in.');
//         return;
//       }
//       try {
//         const orgresponse = await axiosInstance.get('/organizations', {
//           headers: {
//             Authorization: `Bearer ${token}`, // Corrected syntax
//           },
//         });

//         const userresponse = await axiosInstance.get('/users', {
//           headers: {
//             Authorization:`Bearer ${token}`, // Corrected syntax
//           },
//         });

//         console.log("hello world");
//         setOrganizations(orgresponse.data);
//         setUsers(userresponse.data);

//         const mappingResponse = await apiClient.get("/api/access/mappings", {
//           headers: { Authorization: `Bearer ${token}` }
//         });
//                 console.log("mappingResponse is : ", mappingResponse);

//         if (mappingResponse.data && Array.isArray(mappingResponse.data)) {
//           const processedMappings = mappingResponse.data.map((mapping) => {
//             const organizationName = mapping.organisationMaster ? mapping.organisationMaster.organizationName : 'Unknown Organization';
//             const userName = mapping.userDetails ? mapping.userDetails.userName : 'Unknown User';

//             return {
//               id: mapping.id,
//               organizationName,
//               userName,
//             };
//           });

//           console.log("mappingResponse is : ", mappingResponse);
//           setMappings(processedMappings);
//         } else {
//           setMappings([]);
//         }

//       } catch (error) {
//         console.error('Error fetching organizations:', error);
//         // toast.error('Error fetching organizations');
//       } finally {
//         setLoading(false);
//       }
//     };
//     fetchMapping();
//   }, []);



//   const handleUsernameChange = async (e) => {
//     const input = e.target.value;
//     setUsername(input);

//     if (input.length > 2) {
//       try {
//         const token = Cookies.get('authToken'); // Retrieve token from cookies
//         if (!token) {
//           console.error("No authentication token found.");
//           return;
//         }

//         const response = await apiClient.get("/api/access/search-username", {
//           params: { username: input },
//           headers: { Authorization: `Bearer ${token}` }, // Added Authorization header
//         });

//         setUsernameSuggestions(response.data);
//       } catch (err) {
//         console.error("Error fetching username suggestions:", err);
//       }
//     } else {
//       setUsernameSuggestions([]);
//     }
//   };




//   const handleDelete = useCallback(async (mappingId) => {
//     try {
//       setLoading(true);
//       console.log("Deleting mapping with ID:", mappingId); // Debugging

//       const token = Cookies.get('authToken'); // Retrieve token from cookies
//       if (!token) {
//         console.error("No authentication token found.");
//         setError("No authentication token found.");
//         return;
//       }

//       // Delete the mapping
//       await apiClient.delete(`/api/access/mappings/${mappingId}`, {
//         headers: {
//           Authorization: `Bearer ${token}` // Add token in the Authorization header
//         }
//       });

//       toast.success("Mapping deleted successfully.");
//       setTimeout(() => setSuccess(null), 3000);

//       // Refresh the mappings after deletion
//       const mappingResponse = await apiClient.get(
//         "/api/access/mappings",
//         {
//           headers: {
//             Authorization: `Bearer ${token}` // Add token in the Authorization header for the mappings API
//           }
//         }
//       );

//       if (mappingResponse.data && Array.isArray(mappingResponse.data)) {
//         const processedMappings = mappingResponse.data.map((mapping) => ({
//           id: mapping.id,
//           organizationName: mapping.organisationMaster.organizationName,
//           userName: mapping.userDetails.userName,
//         }));
//         setMappings(processedMappings);
//       }
//     } catch (err) {
//       if (err.response && err.response.status === 404) {
//         setError("Mapping not found or already deleted.");
//       } else {
//         console.error(err); // Log the error for debugging
//         setError("Error deleting mapping. Please try again.");
//       }
//       setTimeout(() => setError(null), 3000);
//     } finally {
//       setLoading(false);
//     }
//   }, []);






//   const handleUsernameSelect = (suggestedUsername) => {
//     setUsername(suggestedUsername);
//     setUsernameSuggestions([]);
//   };



//   const handleSubmit = useCallback(async () => {
//     if (isSubmitting) return;

//     if (!organizationId) {
//       setError("Organization must be selected.");
//       return;
//     }
//     if (!username || !isActive) {
//       setError("All fields are required.");
//       return;
//     }

//     const selectedUser = users.find((user) => user.userName === username);
//     if (!selectedUser) {
//       setError("User not found.");
//       return;
//     }
//     const userId = selectedUser.userId;

//     try {
//       setIsSubmitting(true);

//       const token = Cookies.get('authToken'); // Retrieve token from cookies
//       if (!token) {
//         console.error("No authentication token found check-connected.");
//         setError("No authentication token found.");
//         return;
//       }

//       // API call to check if the user is connected
//       const checkConnectedResponse = await apiClient.get(
//         "/api/access/check-connected",
//         {
//           params: { userId: userId, organizationId },
//           headers: {
//             Authorization: `Bearer ${token}` // Add token in the Authorization header
//           }
//         }
//       );

//       if (
//         checkConnectedResponse.data === "User is already connected to the organization."
//       ) {
//         setError("User is already connected to this organization.");
//         setTimeout(() => setError(null), 3000);
//         setIsSubmitting(false);
//         return;
//       } else if (
//         checkConnectedResponse.data === "Access Granted." ||
//         checkConnectedResponse.data === "User and Organization are not connected. Now connected!"
//       ) {
//         toast.success("User successfully mapped to the organization!");
//         setError(null);
//         setTimeout(() => setSuccess(null), 3000);

//         // Fetch the mappings after successful connection
//         const mappingResponse = await apiClient.get(
//           "/api/access/mappings",
//           {
//             headers: {
//               Authorization: `Bearer ${token}` // Add token in the Authorization header for mappings API
//             }
//           }
//         );

//         if (mappingResponse.data && Array.isArray(mappingResponse.data)) {
//           const processedMappings = mappingResponse.data.map((mapping) => ({
//             id: mapping.id,
//             organizationName: mapping.organisationMaster.organizationName,
//             userName: mapping.userDetails.userName,
//           }));
//           setMappings(processedMappings);
//         }

//         // Close the form after successful submission
//         setShowForm(false);
//       } else {
//         toast.error(`${checkConnectedResponse.data}`);
//         setTimeout(() => setError(null), 3000);
//         setIsSubmitting(false);
//         return;
//       }
//     } catch (err) {
//       console.error(err);
//       setError("An error occurred while mapping the user. Please try again.");
//       setTimeout(() => setError(null), 3000);
//     } finally {
//       setIsSubmitting(false);
//     }
//   }, [isSubmitting, users, organizationId, username, isActive]);


//    const handleLogout = () => {
//         Cookies.remove('authToken');
//         toast.success('Logged out successfully!');
//         setTimeout(() => {
//           window.location.href = '/views/ui-elements/basic/Basicsignin';
//         }, 1000);
//       };

//   return (
//     <React.Fragment>
//       <ToastContainer />

//       <Breadcrumb />
//       <div className="auth-wrapper">
//         <div
//           className="auth-content"
//           style={{
//             maxWidth: "80%",
//             width: "80%",
//             margin: "20px auto",
//             padding: "20px",
//           }}
//         >
//           <Card className="borderless">
//             <Card.Body>
//             {/* <Button variant="danger" onClick={handleLogout}>Logout</Button> */}
//               <h3 className="mb-4">Organization User Mapping</h3>

//               {error && <div className="alert alert-danger">{error}</div>}
//               {success && <div className="alert alert-success">{success}</div>}

//               {loading ? (
//                 <div>Loading...</div>
//               ) : (
//                 <>
//                   {!showForm ? (
//                     <>
//                       <Button
//                         className="mb-4"
//                         onClick={() => setShowForm(true)}
//                         variant="primary"
//                       >
//                         Create Mapping
//                       </Button>
//                       <h5 className="mb-3">Mapped Users to Organizations</h5>

//                       {mappings.length > 0 ? (
//                         <Table
//                           responsive
//                           bordered
//                           hover
//                           className="mapped-table"
//                           style={{
//                             backgroundColor: "#f8f9fa",
//                             border: "1px solid #ddd",
//                             borderRadius: "8px",
//                           }}
//                         >
//                           <thead>
//                             <tr
//                               style={{
//                                 backgroundColor: "#004085",
//                                 color: "white",
//                                 textAlign: "center",
//                               }}
//                             >
//                               <th>#</th>
//                               <th>Organization Name</th>
//                               <th>User Name</th>
//                               <th>Action</th>
//                             </tr>
//                           </thead>
//                           <tbody>
//                             {mappings.map((mapping, index) => (
//                               <tr key={mapping.id}>
//                                 <td style={{ textAlign: "center" }}>
//                                   {index + 1}
//                                 </td>
//                                 <td style={{ textAlign: "center" }}>
//                                   {mapping.organizationName}
//                                 </td>
//                                 <td style={{ textAlign: "center" }}>
//                                   {mapping.userName}
//                                 </td>
//                                 <td style={{ textAlign: "center" }}>
//                                   <Button
//                                     variant="danger"
//                                     size="sm"
//                                     onClick={() => handleDelete(mapping.id)}
//                                   >
//                                     Delete
//                                   </Button>
//                                 </td>
//                               </tr>
//                             ))}
//                           </tbody>

//                         </Table>

//                       ) : (
//                         <p className="text-muted">No mappings found.</p>
//                       )}
//                     </>
//                   ) : (
//                     <div>
//                       <Dropdown className="mb-3">
//                         <Dropdown.Toggle
//                           variant="light"
//                           className="form-control text-left"
//                         >
//                           {organizationName || "Select Organization"}
//                         </Dropdown.Toggle>
//                         <Dropdown.Menu>
//                           {organizations.map((org) => (
//                             <Dropdown.Item
//                               key={org.id}
//                               onClick={() => {
//                                 setOrganizationName(org.organizationName);
//                                 setOrganizationId(org.orgId);
//                               }}
//                             >
//                               {org.organizationName}
//                             </Dropdown.Item>
//                           ))}
//                         </Dropdown.Menu>
//                       </Dropdown>

//                       <div className="input-group mb-3 position-relative">
//                         <Form.Control
//                           type="text"
//                           placeholder="Username"
//                           value={username}
//                           onChange={handleUsernameChange}
//                         />
//                         {usernameSuggestions.length > 0 && (
//                           <div
//                             className="position-absolute w-100"
//                             style={{
//                               top: "100%",
//                               left: 0,
//                               zIndex: 10,
//                               backgroundColor: "white",
//                               border: "1px solid #ddd",
//                               borderRadius: "4px",
//                             }}
//                           >
//                             <Table
//                               responsive
//                               className="username-suggestions-table"
//                             >
//                               <thead>
//                                 <tr>
//                                   <th>User Name</th>
//                                 </tr>
//                               </thead>
//                               <tbody>
//                                 {usernameSuggestions.map((user) => (
//                                   <tr
//                                     key={user.userId}
//                                     style={{ cursor: "pointer" }}
//                                     onClick={() =>
//                                       handleUsernameSelect(user.userName)
//                                     }
//                                   >
//                                     <td>{user.userName}</td>
//                                   </tr>
//                                 ))}
//                               </tbody>
//                             </Table>
//                           </div>
//                         )}
//                       </div>

//                       <Dropdown className="mb-4">
//                         <Dropdown.Toggle
//                           variant="light"
//                           className="form-control text-left"
//                         >
//                           {isActive || "Select Active Status"}
//                         </Dropdown.Toggle>
//                         <Dropdown.Menu>
//                           <Dropdown.Item onClick={() => setIsActive("Yes")}>
//                             Yes
//                           </Dropdown.Item>
//                           <Dropdown.Item onClick={() => setIsActive("No")}>
//                             No
//                           </Dropdown.Item>
//                         </Dropdown.Menu>
//                       </Dropdown>

//                       <Button
//                         className="btn btn-primary mb-4"
//                         onClick={handleSubmit}
//                         disabled={isSubmitting}
//                       >
//                         Submit
//                       </Button>
//                       <Button
//                         className="btn btn-primary mb-4"
//                         onClick={() => setShowForm(false)}
//                         disabled={isSubmitting}
//                       >
//                         Cancel
//                       </Button>
//                     </div>
//                   )}
//                 </>
//               )}
//             </Card.Body>
//           </Card>
//           <Button variant="danger" onClick={handleLogout}>Logout</Button>
//         </div>
//       </div>
//     </React.Fragment>
//   );
// };

// export default Basicoumapping;


















import React, { useState, useEffect, useCallback } from "react";
import { Card, Row, Col, Dropdown, Button, Form, Table } from "react-bootstrap";
import Breadcrumb from "../../../layouts/AdminLayout/Breadcrumb";
import apiClient from "../../../api/axios";
import Cookies from 'js-cookie';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';



const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});
const Basicoumapping = () => {
  const [organizationName, setOrganizationName] = useState("");
  const [organizationId, setOrganizationId] = useState(null);
  const [isActive, setIsActive] = useState("");
  const [username, setUsername] = useState("");
  const [usernameSuggestions, setUsernameSuggestions] = useState([]);
  const [organizations, setOrganizations] = useState([]);
  const [users, setUsers] = useState([]);
  const [mappings, setMappings] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [showForm, setShowForm] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");

  useEffect(() => {
    const fetchMapping = async () => {

      const token = Cookies.get('authToken'); // Retrieve token from cookies
      console.log("token is ", token)
      if (!token) {
        // toast.error('No authentication token found. Please log in.');
        return;
      }
      try {
        const orgresponse = await axiosInstance.get('/organizations', {
          headers: {
            Authorization: `Bearer ${token}`, // Corrected syntax
          },
        });

        const userresponse = await axiosInstance.get('/users', {
          headers: {
            Authorization: `Bearer ${token}`, // Corrected syntax
          },
        });

        console.log("hello world");
        setOrganizations(orgresponse.data);
        setUsers(userresponse.data);

        const mappingResponse = await apiClient.get("/api/access/mappings", {
          headers: { Authorization: `Bearer ${token}` }
        });
        console.log("mappingResponse is : ", mappingResponse);

        if (mappingResponse.data && Array.isArray(mappingResponse.data)) {
          const processedMappings = mappingResponse.data.map((mapping) => {
            const organizationName = mapping.organisationMaster ? mapping.organisationMaster.organizationName : 'Unknown Organization';
            const userName = mapping.userDetails ? mapping.userDetails.userName : 'Unknown User';

            return {
              id: mapping.id,
              organizationName,
              userName,
            };
          });

          console.log("mappingResponse is : ", mappingResponse);
          setMappings(processedMappings);
        } else {
          setMappings([]);
        }

      } catch (error) {
        console.error('Error fetching organizations:', error);
        // toast.error('Error fetching organizations');
      } finally {
        setLoading(false);
      }
    };
    fetchMapping();
  }, []);



  const handleUsernameChange = async (e) => {
    const input = e.target.value;
    setUsername(input);

    if (input.length > 2) {
      try {
        const token = Cookies.get('authToken'); // Retrieve token from cookies
        if (!token) {
          console.error("No authentication token found.");
          return;
        }

        const response = await apiClient.get("/api/access/search-username", {
          params: { username: input },
          headers: { Authorization: `Bearer ${token}` }, // Added Authorization header
        });

        setUsernameSuggestions(response.data);
      } catch (err) {
        console.error("Error fetching username suggestions:", err);
      }
    } else {
      setUsernameSuggestions([]);
    }
  };




  const handleDelete = useCallback(async (mappingId) => {
    try {
      setLoading(true);
      console.log("Deleting mapping with ID:", mappingId); // Debugging

      const token = Cookies.get('authToken'); // Retrieve token from cookies
      if (!token) {
        console.error("No authentication token found.");
        setError("No authentication token found.");
        return;
      }

      // Delete the mapping
      await apiClient.delete(`/api/access/mappings/${mappingId}`, {
        headers: {
          Authorization: `Bearer ${token}` // Add token in the Authorization header
        }
      });

      toast.success("Mapping deleted successfully.");
      setTimeout(() => setSuccess(null), 3000);

      // Refresh the mappings after deletion
      const mappingResponse = await apiClient.get(
        "/api/access/mappings",
        {
          headers: {
            Authorization: `Bearer ${token}` // Add token in the Authorization header for the mappings API
          }
        }
      );

      if (mappingResponse.data && Array.isArray(mappingResponse.data)) {
        const processedMappings = mappingResponse.data.map((mapping) => ({
          id: mapping.id,
          organizationName: mapping.organisationMaster.organizationName,
          userName: mapping.userDetails.userName,
        }));
        setMappings(processedMappings);
      }
    } catch (err) {
      if (err.response && err.response.status === 404) {
        setError("Mapping not found or already deleted.");
      } else {
        console.error(err); // Log the error for debugging
        setError("Error deleting mapping. Please try again.");
      }
      setTimeout(() => setError(null), 3000);
    } finally {
      setLoading(false);
    }
  }, []);






  const handleUsernameSelect = (suggestedUsername) => {
    setUsername(suggestedUsername);
    setUsernameSuggestions([]);
  };



  const handleSubmit = useCallback(async () => {
    if (isSubmitting) return;

    if (!organizationId) {
      setError("Organization must be selected.");
      return;
    }
    if (!username || !isActive) {
      setError("All fields are required.");
      return;
    }

    const selectedUser = users.find((user) => user.userName === username);
    if (!selectedUser) {
      setError("User not found.");
      return;
    }
    const userId = selectedUser.userId;

    try {
      setIsSubmitting(true);

      const token = Cookies.get('authToken'); // Retrieve token from cookies
      if (!token) {
        console.error("No authentication token found check-connected.");
        setError("No authentication token found.");
        return;
      }

      // API call to check if the user is connected
      const checkConnectedResponse = await apiClient.get(
        "/api/access/check-connected",
        {
          params: { userId: userId, organizationId },
          headers: {
            Authorization: `Bearer ${token}` // Add token in the Authorization header
          }
        }
      );

      if (
        checkConnectedResponse.data === "User is already connected to the organization."
      ) {
        setError("User is already connected to this organization.");
        setTimeout(() => setError(null), 3000);
        setIsSubmitting(false);
        return;
      } else if (
        checkConnectedResponse.data === "Access Granted." ||
        checkConnectedResponse.data === "User and Organization are not connected. Now connected!"
      ) {
        toast.success("User successfully mapped to the organization!");
        setError(null);
        setTimeout(() => setSuccess(null), 3000);

        // Fetch the mappings after successful connection
        const mappingResponse = await apiClient.get(
          "/api/access/mappings",
          {
            headers: {
              Authorization: `Bearer ${token}` // Add token in the Authorization header for mappings API
            }
          }
        );

        if (mappingResponse.data && Array.isArray(mappingResponse.data)) {
          const processedMappings = mappingResponse.data.map((mapping) => ({
            id: mapping.id,
            organizationName: mapping.organisationMaster.organizationName,
            userName: mapping.userDetails.userName,
          }));
          setMappings(processedMappings);
        }

        // Close the form after successful submission
        setShowForm(false);
      } else {
        toast.error(`${checkConnectedResponse.data}`);
        setTimeout(() => setError(null), 3000);
        setIsSubmitting(false);
        return;
      }
    } catch (err) {
      console.error(err);
      setError("An error occurred while mapping the user. Please try again.");
      setTimeout(() => setError(null), 3000);
    } finally {
      setIsSubmitting(false);
    }
  }, [isSubmitting, users, organizationId, username, isActive]);


  const handleLogout = () => {
    Cookies.remove('authToken');
    toast.success('Logged out successfully!');
    setTimeout(() => {
      window.location.href = '/views/ui-elements/basic/Basicsignin';
    }, 1000);
  };


  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const filteredMappings = mappings.filter(mapping =>
    mapping.organizationName.toLowerCase().includes(searchQuery.toLowerCase()) ||
    mapping.userName.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <React.Fragment>
      <ToastContainer />

      <Breadcrumb />
      <div className="auth-wrapper">
        <div
          className="auth-content"
          style={{
            maxWidth: "80%",
            width: "80%",
            margin: "20px auto",
            padding: "20px",
          }}
        >
          <Card className="borderless">
            <Card.Body>
              {/* <Button variant="danger" onClick={handleLogout}>Logout</Button> */}
              <h3 className="mb-4">Organization User Mapping</h3>

              {error && <div className="alert alert-danger">{error}</div>}
              {success && <div className="alert alert-success">{success}</div>}

              {loading ? (
                <div>Loading...</div>
              ) : (
                <>
                  {!showForm ? (
                    <>
                      <Button
                        className="mb-4"
                        onClick={() => setShowForm(true)}
                        variant="primary"
                      >
                        Create Mapping
                      </Button>
                      <h5 className="mb-3">Mapped Users to Organizations</h5>

                      <Form.Control
                        type="text"
                        placeholder="Search by Organization or User"
                        value={searchQuery}
                        onChange={handleSearchChange}
                        className="mb-3"
                      />


                      {filteredMappings.length > 0 ? (
                        <Table
                          responsive
                          bordered
                          hover
                          className="mapped-table"
                          style={{
                            backgroundColor: "#f8f9fa",
                            border: "1px solid #ddd",
                            borderRadius: "8px",
                          }}
                        >
                          <thead>
                            <tr
                              style={{
                                backgroundColor: "#004085",
                                color: "white",
                                textAlign: "center",
                              }}
                            >
                              <th>#</th>
                              <th>Organization Name</th>
                              <th>User Name</th>
                              <th>Action</th>
                            </tr>
                          </thead>
                          <tbody>
                            {filteredMappings.map((mapping, index) => (<tr key={mapping.id}>
                              <td style={{ textAlign: "center" }}>
                                {index + 1}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                {mapping.organizationName}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                {mapping.userName}
                              </td>
                              <td style={{ textAlign: "center" }}>
                                <Button
                                  variant="danger"
                                  size="sm"
                                  onClick={() => handleDelete(mapping.id)}
                                >
                                  Delete
                                </Button>
                              </td>
                            </tr>
                            ))}
                          </tbody>

                        </Table>

                      ) : (
                        <p className="text-muted">No mappings found.</p>
                      )}
                    </>
                  ) : (
                    <div>
                      <Dropdown className="mb-3">
                        <Dropdown.Toggle
                          variant="light"
                          className="form-control text-left"
                        >
                          {organizationName || "Select Organization"}
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                          {organizations.map((org) => (
                            <Dropdown.Item
                              key={org.id}
                              onClick={() => {
                                setOrganizationName(org.organizationName);
                                setOrganizationId(org.orgId);
                              }}
                            >
                              {org.organizationName}
                            </Dropdown.Item>
                          ))}
                        </Dropdown.Menu>
                      </Dropdown>

                      <div className="input-group mb-3 position-relative">
                        <Form.Control
                          type="text"
                          placeholder="Username"
                          value={username}
                          onChange={handleUsernameChange}
                        />
                        {usernameSuggestions.length > 0 && (
                          <div
                            className="position-absolute w-100"
                            style={{
                              top: "100%",
                              left: 0,
                              zIndex: 10,
                              backgroundColor: "white",
                              border: "1px solid #ddd",
                              borderRadius: "4px",
                            }}
                          >
                            <Table
                              responsive
                              className="username-suggestions-table"
                            >
                              <thead>
                                <tr>
                                  <th>User Name</th>
                                </tr>
                              </thead>
                              <tbody>
                                {usernameSuggestions.map((user) => (
                                  <tr
                                    key={user.userId}
                                    style={{ cursor: "pointer" }}
                                    onClick={() =>
                                      handleUsernameSelect(user.userName)
                                    }
                                  >
                                    <td>{user.userName}</td>
                                  </tr>
                                ))}
                              </tbody>
                            </Table>
                          </div>
                        )}
                      </div>

                      <Dropdown className="mb-4">
                        <Dropdown.Toggle
                          variant="light"
                          className="form-control text-left"
                        >
                          {isActive || "Select Active Status"}
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                          <Dropdown.Item onClick={() => setIsActive("Yes")}>
                            Yes
                          </Dropdown.Item>
                          <Dropdown.Item onClick={() => setIsActive("No")}>
                            No
                          </Dropdown.Item>
                        </Dropdown.Menu>
                      </Dropdown>

                      <Button
                        className="btn btn-primary mb-4"
                        onClick={handleSubmit}
                        disabled={isSubmitting}
                      >
                        Submit
                      </Button>
                      <Button
                        className="btn btn-primary mb-4"
                        onClick={() => setShowForm(false)}
                        disabled={isSubmitting}
                      >
                        Cancel
                      </Button>
                    </div>
                  )}
                </>
              )}
            </Card.Body>
          </Card>
          <Button variant="danger" onClick={handleLogout} style={{ margin: '15px' }}>
            Logout
          </Button>      
            </div>
      </div>
    </React.Fragment>
  );
};

export default Basicoumapping;








