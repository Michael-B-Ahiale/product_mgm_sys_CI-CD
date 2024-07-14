import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Table, Button, Form, InputGroup } from 'react-bootstrap';

function ProductList() {
    const [products, setProducts] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        fetchProducts();
    }, [currentPage]);

    const fetchProducts = async () => {
        try {
            const response = await axios.get(`http://localhost:8082/api/products?page=${currentPage}&size=10`);
            setProducts(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    const handleSearch = async () => {
        try {
            const response = await axios.get(`http://localhost:8082/api/products/search?query=${searchTerm}&page=${currentPage}&size=10`);
            setProducts(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error('Error searching products:', error);
        }
    };

    const handleDelete = async (id) => {
        try {
            await axios.delete(`http://localhost:8082/api/products/${id}`);
            fetchProducts();
        } catch (error) {
            console.error('Error deleting product:', error);
        }
    };

    return (
        <div>
            <h1 className="mb-4">Product List</h1>
            <Form className="mb-3">
                <InputGroup>
                    <Form.Control
                        type="text"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder="Search products"
                    />
                    <Button variant="primary" onClick={handleSearch}>Search</Button>
                </InputGroup>
            </Form>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {products.map((product) => (
                    <tr key={product.id}>
                        <td>{product.name}</td>
                        <td>${product.price}</td>
                        <td>
                            <Link to={`/products/edit/${product.id}`} className="btn btn-sm btn-warning me-2">Edit</Link>
                            <Button variant="danger" size="sm" onClick={() => handleDelete(product.id)}>Delete</Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <div className="d-flex justify-content-between align-items-center">
                <Button onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 0}>Previous</Button>
                <span>Page {currentPage + 1} of {totalPages}</span>
                <Button onClick={() => setCurrentPage(currentPage + 1)} disabled={currentPage === totalPages - 1}>Next</Button>
            </div>
        </div>
    );
}

export default ProductList;