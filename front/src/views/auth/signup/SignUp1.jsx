import React, { useState } from 'react';
import { Card, Row, Col, Dropdown, Button } from 'react-bootstrap';
import Breadcrumb from '../../../layouts/AdminLayout/Breadcrumb';

const SignUp1 = () => {
  const [organizationName, setOrganizationName] = useState('');
  const [isActive, setIsActive] = useState('');

  const organizations = [
    'Organization A',
    'Organization B',
    'Organization C',
  ];

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
                    <i className="feather icon-user-plus auth-icon" />
                  </div>
                  <h3 className="mb-4">Organization User Mapping</h3>

                  {/* Organization Dropdown */}
                  <Dropdown className="mb-3">
                    <Dropdown.Toggle variant="light" className="form-control text-left">
                      {organizationName || 'Select Organization'}
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                      {organizations.map((org, index) => (
                        <Dropdown.Item
                          key={index}
                          onClick={() => setOrganizationName(org)}
                        >
                          {org}
                        </Dropdown.Item>
                      ))}
                    </Dropdown.Menu>
                  </Dropdown>

                  <div className="input-group mb-3">
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Username"
                    />
                  </div>

                  {/* Active Dropdown */}
                  <Dropdown className="mb-4">
                    <Dropdown.Toggle variant="light" className="form-control text-left">
                      {isActive || 'Select Active Status'}
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                      <Dropdown.Item onClick={() => setIsActive('Yes')}>Yes</Dropdown.Item>
                      <Dropdown.Item onClick={() => setIsActive('No')}>No</Dropdown.Item>
                    </Dropdown.Menu>
                  </Dropdown>

                  <button className="btn btn-primary mb-4">Submit</button>
                </Card.Body>
              </Col>
            </Row>
          </Card>
        </div>
      </div>
    </React.Fragment>
  );
};

export default SignUp1;
