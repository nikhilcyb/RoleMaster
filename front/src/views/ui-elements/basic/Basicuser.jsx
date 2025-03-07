import React, { useState, useEffect } from 'react';
import { Table, Button, Form } from 'react-bootstrap';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';
import Cookies from 'js-cookie';
import './Basicuser.css';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

const Basicuser = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [selectedRoleId, setSelectedRoleId] = useState('');
  const [selectedPrivileges, setSelectedPrivileges] = useState([]);
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]);
  const [privileges, setPrivileges] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [isCreatingUser, setIsCreatingUser] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');


  const fetchData = async () => {

    const token = Cookies.get('authToken');
    if (!token) {
      toast.error('No authentication token found. Please log in.');
      return;
    }

    try {
      const [usersResponse, rolesResponse, privilegesResponse] = await Promise.all([
        axiosInstance.get('/users', { headers: { Authorization: `Bearer ${token}` } }),
        axiosInstance.get('/roles', { headers: { Authorization: `Bearer ${token}` } }),
        axiosInstance.get('/privileges', { headers: { Authorization: `Bearer ${token}` } }),
      ]);

      setUsers(usersResponse.data);
      setRoles(rolesResponse.data);
      setPrivileges(privilegesResponse.data);
    } catch (error) {
      console.error('Error fetching data:', error);
      toast.error('Failed to fetch data. Please check your connection or token.');
    }
  };
  
  useEffect(() => {
    fetchData();
  }, []);

  const handlePrivilegeChange = (privilegeId) => {
    setSelectedPrivileges((prev) =>
      prev.includes(privilegeId) ? prev.filter((id) => id !== privilegeId) : [...prev, privilegeId]
    );
  };

  const handleRoleChange = (roleId) => {
    setSelectedRoleId(roleId);

    const role = roles.find((r) => r.userRoleId === parseInt(roleId));
    if (role && role.privileges) {
      setSelectedPrivileges(role.privileges.map((priv) => priv.id));
    } else {
      setSelectedPrivileges([]);
    }
  };

  const handleEdit = (user) => {
    setSelectedUser(user);
    setUsername(user.userName || '');
    setEmail(user.email || '');
    setSelectedRoleId(user.userRoleMaster?.userRoleId || '');

    const role = roles.find((r) => r.userRoleId === user.userRoleMaster?.userRoleId);
    if (role && role.privileges) {
      setSelectedPrivileges(role.privileges.map((priv) => priv.id));
    } else {
      setSelectedPrivileges(user.privileges?.map((priv) => priv.id) || []);
    }

    setIsCreatingUser(true);
  };

  const handleDelete = async (userId) => {
    const token = Cookies.get('authToken');
    if (!token) {
      toast.error('No authentication token found. Please log in.');
      return;
    }

    try {
      await axiosInstance.delete(`/users/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setUsers((prevUsers) => prevUsers.filter((user) => user.userId !== userId));
      toast.success('User deleted successfully!');
    } catch (error) {
      console.error('Error deleting user:', error);
      toast.error('Failed to delete user. Please try again.');
    }
  };


  const validateUsername = (username) => {
    const usernameRegex = /^(?![_\.])[a-zA-Z0-9._]{5,20}(?<![_\.])$/;
    return usernameRegex.test(username);
  };

  // const handleSubmit = async (e) => {
  //   e.preventDefault();


  //   const token = Cookies.get('authToken');
  //   if (!token) {
  //     toast.error('No authentication token found. Please log in.');
  //     return;
  //   }


  // if (!validateUsername(username)) {
  //   toast.error('Invalid username. Must be 5-20 characters, no spaces, special characters, or consecutive underscores/dots.');
  //   return;
  // }

  //   const userData = {
  //     userName: username,
  //     email,
  //     password: selectedUser ? undefined : password, // Only send password when creating a new user
  //     userRoleMaster: {
  //       userRoleId: selectedRoleId,
  //     },
  //     privileges: selectedPrivileges.map((id) => ({ id })),
  //   };

  //   try {
  //     if (selectedUser) {
  //       const response = await axiosInstance.put(`/users/${selectedUser.userId}`, userData, {
  //         headers: { Authorization: `Bearer ${token}` },
  //       });
  //       setUsers((prevUsers) =>
  //         prevUsers.map((user) => (user.userId === selectedUser.userId ? response.data : user))
  //       );
  //       toast.success('User updated successfully!');
  //     } else {
  //       const response = await axiosInstance.post('/users', userData, {
  //         headers: { Authorization: `Bearer ${token}` },
  //       });
  //       setUsers((prevUsers) => [...prevUsers, response.data]);
  //       toast.success('User created successfully!');
  //     }

  //     resetForm();
  //     setIsCreatingUser(false);
  //   } catch (error) {
  //     console.error('Error submitting form:', error);
  //     toast.error('Failed to submit form. Please try again.');
  //   }
  // };

  // const handleSubmit = async (e) => {
  //   e.preventDefault();

  //   const token = Cookies.get('authToken');
  //   if (!token) {
  //     toast.error('No authentication token found. Please log in.');
  //     return;
  //   }

  //   if (!validateUsername(username)) {
  //     toast.error('Invalid username. Must be 5-20 characters, no spaces, special characters, or consecutive underscores/dots.');
  //     return;
  //   }

  //   const userData = {
  //     userName: username,
  //     email,
  //     password: selectedUser ? undefined : password, // Only send password when creating a new user
  //     userRoleMaster: { userRoleId: selectedRoleId },
  //     privileges: selectedPrivileges.map((id) => ({ id })),
  //   };

  //   try {
  //     let response;
  //     if (selectedUser) {
  //       // Update an existing user
  //       response = await axiosInstance.put(`/users/${selectedUser.userId}`, userData, {
  //         headers: { Authorization: `Bearer ${token}` },
  //       });
  //       setUsers((prevUsers) =>
  //         prevUsers.map((user) => (user.userId === selectedUser.userId ? response.data : user))
  //       );
  //       toast.success('User updated successfully!');
  //     } else {
  //       // Create a new user
  //       response = await axiosInstance.post('/users', userData, {
  //         headers: { Authorization: `Bearer ${token}` },
  //       });
  //       setUsers((prevUsers) => [...prevUsers, response.data]); // Directly add new user to the list
  //       toast.success('User created successfully!');
  //     }

  //     // Reset the form after submission
  //     resetForm();
  //     setIsCreatingUser(false);
  //   } catch (error) {
  //     console.error('Error submitting form:', error);
  //     toast.error('Failed to submit form. Please try again.');
  //   }
  // };


  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = Cookies.get('authToken');
    if (!token) {
      toast.error('No authentication token found. Please log in.');
      return;
    }

    if (!validateUsername(username)) {
      toast.error('Invalid username. Must be 5-20 characters, no spaces, special characters, or consecutive underscores/dots.');
      return;
    }

    const userData = {
      userName: username,
      email,
      password: selectedUser ? undefined : password, // Only send password when creating a new user
      userRoleMaster: { userRoleId: selectedRoleId },
      privileges: selectedPrivileges.map((id) => ({ id })),
    };

    try {
      let response;
      if (selectedUser) {
        // Update an existing user
        response = await axiosInstance.put(`/users/${selectedUser.userId}`, userData, {
          headers: { Authorization: `Bearer ${token}` },
        });

        setUsers((prevUsers) =>
          prevUsers.map((user) => (user.userId === selectedUser.userId ? response.data : user))
        );
        toast.success('User updated successfully!');
      } else {
        // Create a new user
        response = await axiosInstance.post('/users', userData, {
          headers: { Authorization: `Bearer ${token}` },
        });

        // Check if the response contains the created user
        if (response.data) {
          // Manually update the privileges if they are missing
          const createdUser = {
            ...response.data,
            privileges: selectedPrivileges.map((id) => ({ id })) // Ensure privileges are added
          };

          // Directly add the new user to the users list
          setUsers((prevUsers) => [...prevUsers, createdUser]);

          toast.success('User created successfully!');
        } else {
          toast.error('Failed to create user.');
        }
      }

      // Reset the form after submission
      resetForm();
      fetchData();
      setIsCreatingUser(false);
    } catch (error) {
      console.error('Error submitting form:', error);
      toast.error('Failed to submit form. Please try again.');
    }
  };




  const resetForm = () => {
    setSelectedUser(null);
    setUsername('');
    setEmail('');
    setPassword('');
    setSelectedRoleId('');
    setSelectedPrivileges([]);
  };


  const handleLogout = () => {
    Cookies.remove('authToken');
    toast.success('Logged out successfully!');
    setTimeout(() => {
      window.location.href = '/views/ui-elements/basic/Basicsignin';
    }, 1000);
  };
  console.log("users", users)
  const renderUserList = () => (
    <>
      <h3 className="mb-4">User List</h3>
      <Form.Control
        type="text"
        placeholder="Search users..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="mb-3"
      />
      <Button variant="primary" onClick={() => setIsCreatingUser(true)}>
        Create User
      </Button>
      <div className="table-container">
        <Table bordered hover responsive className="custom-table">
          <thead>
            <tr>
              <th>Username</th>
              <th>Email</th>
              <th>Role</th>
              <th>Privileges</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users
              .filter((user) =>
                user.userName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
                user.email?.toLowerCase().includes(searchTerm.toLowerCase())
              )
              .map((user) => (
                <tr key={user.userId}>
                  <td>{user.userName || 'N/A'}</td>
                  <td>{user.email || 'N/A'}</td>
                  <td>{user.userRoleMaster?.roleName || 'No Role'}</td>


                  <td>{user.privileges?.map((priv) => priv.name).join(', ') || 'No Privileges'}</td>
                  <td>
                    <Button variant="warning" className="action-button" onClick={() => handleEdit(user)}>
                      Edit
                    </Button>
                    <Button variant="danger" className="action-button" onClick={() => handleDelete(user.userId)}>
                      Delete
                    </Button>
                  </td>
                </tr>
              ))}
          </tbody>
        </Table>
      </div>
    </>
  );

  const renderUserForm = () => (
    <div className="form-container">
      <h3 className="mb-4">{selectedUser ? 'Edit User' : 'Create User'}</h3>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Enter password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required={!selectedUser}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Role</Form.Label>
          <Form.Select
            value={selectedRoleId}
            onChange={(e) => handleRoleChange(e.target.value)}
            required
          >
            <option value="">Select Role</option>
            {roles.map((role) => (
              <option key={role.userRoleId} value={role.userRoleId}>
                {role.roleName}
              </option>
            ))}
          </Form.Select>
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Privileges</Form.Label>
          <div>
            {privileges.map((privilege) => (
              <Form.Check
                key={privilege.id}
                type="checkbox"
                id={`privilege-${privilege.id}`}
                label={privilege.name}
                value={privilege.id}
                checked={selectedPrivileges.includes(privilege.id)}
                onChange={() => handlePrivilegeChange(privilege.id)}
              />
            ))}
          </div>
        </Form.Group>
        <Button variant="primary" type="submit">
          {selectedUser ? 'Update User' : 'Create User'}
        </Button>
        <Button variant="secondary" className="ms-2" onClick={() => setIsCreatingUser(false)}>
          Cancel
        </Button>
      </Form>
    </div>
  );

  return (
    <div className="container mt-5">
      <ToastContainer />
      {isCreatingUser ? renderUserForm() : renderUserList()}

      <Button variant="danger" onClick={handleLogout}>Logout</Button>
    </div>
  );
};

export default Basicuser;




