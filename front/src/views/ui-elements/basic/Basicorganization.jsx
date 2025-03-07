import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Table, Button, Dropdown, Form } from 'react-bootstrap';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Cookies from 'js-cookie';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});




const Basicorganization = () => {
  const [organizations, setOrganizations] = useState([]);
  const [formData, setFormData] = useState({
    orgId: '',
    organizationName: '',
    email: '',
    contactNo: '',
    domainName: '',
    isEnabled: false,
    orgDescription: '',
    orgLimit: '',
  });
  const [isCreatingOrganization, setIsCreatingOrganization] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchOrganizations = async () => {
      const token = Cookies.get('authToken');
      if (!token) {
        toast.error('No authentication token found. Please log in.');
        return;
      }
      try {
        const response = await axiosInstance.get('/organizations', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setOrganizations(response.data);
      } catch (error) {
        console.error('Error fetching organizations:', error);
        toast.error('Error fetching organizations');
      }
    };
    fetchOrganizations();
  }, []);

  const handleChange = (e) => {
    if (e.target.name === 'isEnabled') {
      setFormData({ ...formData, [e.target.name]: e.target.checked });
    } else {
      setFormData({ ...formData, [e.target.name]: e.target.value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    const token = Cookies.get('authToken');
    if (!token) {
      toast.error('Authentication token is missing. Please log in.');
      setIsSubmitting(false);
      return;
    }

    try {
      if (formData.orgId) {
        await axiosInstance.put(`/organizations/${formData.orgId}`, formData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setOrganizations((prev) =>
          prev.map((org) =>
            org.orgId === formData.orgId ? { ...org, ...formData } : org
          )
        );
        toast.success('Organization updated successfully!');
      } else {
        const response = await axiosInstance.post('/organizations', formData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setOrganizations((prev) => [...prev, response.data]);
        toast.success('Organization created successfully!');
        setIsCreatingOrganization(false);
      }
    } catch (error) {
      console.error('Error saving organization:', error);
      if (error.response && error.response.status === 400) {
        const errors = error.response.data;
  
        // Check for duplicate organization name error
        if (errors.organizationName === 'Organization already exists') {
          toast.error('Organization already exists. Please use a different name.');
        } else {
          // Show generic validation errors
          Object.values(errors).forEach((errMsg) => toast.error(errMsg));
        }
      } else {
        toast.error('Error saving organization');
      }

    } finally {
      // setIsCreatingOrganization(false);
      setIsSubmitting(false);
    }
  };

  const handleCreateOrganizationClick = () => {
    setIsCreatingOrganization(true);
    setFormData({
      orgId: '',
      organizationName: '',
      email: '',
      contactNo: '',
      domainName: '',
      isEnabled: false,
      orgDescription: '',
      orgLimit: '',
    });
  };

  const handleEditOrganizationClick = (org) => {
    setIsCreatingOrganization(true);
    setFormData({
      orgId: org.orgId,
      organizationName: org.organizationName,
      email: org.email,
      contactNo: org.contactNo,
      domainName: org.domainName,
      isEnabled: org.isEnabled,
      orgDescription: org.orgDescription,
      orgLimit: org.orgLimit,
    });
  };

  const handleDeleteOrganizationClick = async (id) => {
    const token = Cookies.get('authToken');
    if (!token) {
      toast.error('Authentication token is missing. Please log in.');
      return;
    }

    try {
      await axiosInstance.delete(`/organizations/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setOrganizations((prevOrganizations) =>
        prevOrganizations.filter((org) => org.orgId !== id)
      );

      toast.success('Organization deleted successfully!');
    } catch (error) {
      console.error('Error deleting organization:', error);
      toast.error('Error deleting organization');
    }
  };

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
  };


  const handleLogout = () => {
      Cookies.remove('authToken');
      toast.success('Logged out successfully!');
      setTimeout(() => {
        window.location.href = '/views/ui-elements/basic/Basicsignin';
      }, 1000);
    };

  return (
    <div className="basic-organization-container">
      <h3 className="mb-4">Organizations</h3>

      {/* Search bar displayed only when not creating or editing an organization */}
      {!isCreatingOrganization && (
        <Form.Control
          type="text"
          placeholder="Search organizations"
          value={searchTerm}
          onChange={handleSearchChange}
          className="mb-4"
        />
      )}

      {!isCreatingOrganization && (
        <Button
          variant="primary"
          className="mb-4"
          onClick={handleCreateOrganizationClick}
        >
          Create Organization
        </Button>
      )}

      {isCreatingOrganization ? (
        <div className="form-container mb-4">
          <div className="input-group mb-3">
            <Form.Control
              type="text"
              placeholder="Organization Name"
              name="organizationName"
              value={formData.organizationName}
              onChange={handleChange}
            />
          </div>
          <div className="input-group mb-3">
            <Form.Control
              type="email"
              placeholder="Email"
              name="email"
              value={formData.email}
              onChange={handleChange}
            />
          </div>
          <div className="input-group mb-3">
            <Form.Control
              type="tel"
              placeholder="Contact Number"
              name="contactNo"
              value={formData.contactNo}
              onChange={handleChange}
            />
          </div>
          <div className="input-group mb-3">
            <Form.Control
              type="text"
              placeholder="Domain Name"
              name="domainName"
              value={formData.domainName}
              onChange={handleChange}
            />
          </div>
          <Dropdown className="mb-3">
            <Dropdown.Toggle variant="light" className="form-control text-left">
              {formData.isEnabled ? 'Enabled' : 'Disabled'}
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Dropdown.Item
                onClick={() => setFormData({ ...formData, isEnabled: true })}
              >
                Enabled
              </Dropdown.Item>
              <Dropdown.Item
                onClick={() => setFormData({ ...formData, isEnabled: false })}
              >
                Disabled
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
          <div className="input-group mb-3">
            <Form.Control
              as="textarea"
              placeholder="Organization Description"
              name="orgDescription"
              value={formData.orgDescription}
              onChange={handleChange}
            />
          </div>
          <div className="input-group mb-3">
            <Form.Control
              type="number"
              placeholder="Limit"
              name="orgLimit"
              value={formData.orgLimit}
              onChange={handleChange}
            />
          </div>
          <div className="d-flex justify-content-between">
            <Button
              variant="primary"
              onClick={handleSubmit}
              disabled={isSubmitting}
            >
              {formData.orgId ? 'Update Organization' : 'Create Organization'}
            </Button>
            <Button
              variant="secondary"
              onClick={() => setIsCreatingOrganization(false)}
            >
              Cancel
            </Button>
          </div>
        </div>
      ) : (
        <>
          <h3 className="mb-4">Organization List</h3>
          <Table bordered hover responsive className="custom-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Contact No</th>
                <th>Domain Name</th>
                <th>Status</th>
                <th>Limit</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {organizations
                .filter((org) =>
                  org.organizationName
                    .toLowerCase()
                    .includes(searchTerm.toLowerCase())
                )
                .map((org) => (
                  <tr key={org.orgId}>
                    <td>{org.organizationName}</td>
                    <td>{org.email}</td>
                    <td>{org.contactNo}</td>
                    <td>{org.domainName}</td>
                    <td>{org.isEnabled ? 'Enabled' : 'Disabled'}</td>
                    <td>{org.orgLimit}</td>
                    <td>
                      <Button
                        variant="warning"
                        className="mr-2"
                        onClick={() => handleEditOrganizationClick(org)}
                      >
                        Edit
                      </Button>
                      <Button
                        variant="danger"
                        onClick={() => handleDeleteOrganizationClick(org.orgId)}
                      >
                        Delete
                      </Button>
                    </td>
                  </tr>
                ))}
            </tbody>
          </Table>
        </>
      )}

      <ToastContainer position="top-right" autoClose={5000} />
      <Button variant="danger" onClick={handleLogout}>Logout</Button>
    </div>
  );
};

export default Basicorganization;
