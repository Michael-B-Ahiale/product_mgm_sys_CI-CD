import React from 'react';
import { Navbar as BootstrapNavbar, Nav, Container } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';

function Navbar() {
    return (
        <BootstrapNavbar bg="dark" variant="dark" expand="lg">
            <Container>
                <BootstrapNavbar.Brand as={NavLink} to="/">E-commerce Manager</BootstrapNavbar.Brand>
                <BootstrapNavbar.Toggle aria-controls="basic-navbar-nav" />
                <BootstrapNavbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link as={NavLink} to="/" end>Products</Nav.Link>
                        <Nav.Link as={NavLink} to="/products/new">Add Product</Nav.Link>
                        <Nav.Link as={NavLink} to="/categories">Categories</Nav.Link>
                        <Nav.Link as={NavLink} to="/categories/new">Add Category</Nav.Link>
                    </Nav>
                </BootstrapNavbar.Collapse>
            </Container>
        </BootstrapNavbar>
    );
}

export default Navbar;