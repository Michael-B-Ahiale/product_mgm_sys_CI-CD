import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Container } from 'react-bootstrap';
import Navbar from './components/Navbar.jsx';
import ProductList from './components/ProductList.jsx';
import ProductForm from './components/ProductForm.jsx';
import CategoryList from './components/CategoryList.jsx';
import CategoryForm from './components/CategoryForm.jsx';


function App() {
    return (
        <Router>
            <div className="App">
                <Navbar />
                <Container className="mt-4">
                    <Routes>
                        <Route path="/" element={<ProductList />} />
                        <Route path="/products/new" element={<ProductForm />} />
                        <Route path="/products/edit/:id" element={<ProductForm />} />
                        <Route path="/categories" element={<CategoryList />} />
                        <Route path="/categories/new" element={<CategoryForm />} />
                        <Route path="/categories/edit/:id" element={<CategoryForm />} />
                    </Routes>
                </Container>
            </div>
        </Router>
    );
}

export default App;