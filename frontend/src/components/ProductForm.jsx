import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';

function ProductForm() {
    const [product, setProduct] = useState({ name: '', description: '', price: '', category: { id: '' } });
    const [categories, setCategories] = useState([]);
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetchCategories();
        if (id) {
            fetchProduct();
        }
    }, [id]);

    const fetchCategories = async () => {
        try {
            const response = await axios.get('http://localhost:8082/api/categories');
            setCategories(response.data);
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    };

    const fetchProduct = async () => {
        try {
            const response = await axios.get(`http://localhost:8082/api/products/${id}`);
            setProduct(response.data);
        } catch (error) {
            console.error('Error fetching product:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const productToSend = {
                ...product,
                price: parseFloat(product.price)
            };
            if (id) {
                await axios.put(`http://localhost:8082/api/products/${id}`, productToSend);
            } else {
                await axios.post('http://localhost:8082/api/products', productToSend);
            }
            navigate('/');
        } catch (error) {
            console.error('Error saving product:', error);
        }
    };

    const handleChange = (e) => {
        if (e.target.name === 'categoryId') {
            setProduct({ ...product, category: { id: e.target.value } });
        } else {
            setProduct({ ...product, [e.target.name]: e.target.value });
        }
    };

    return (
        <div>
            <h1 className="mb-4">{id ? 'Edit Product' : 'Add Product'}</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                        type="text"
                        name="name"
                        value={product.name}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        as="textarea"
                        name="description"
                        value={product.description}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Price</Form.Label>
                    <Form.Control
                        type="number"
                        name="price"
                        value={product.price}
                        onChange={handleChange}
                        required
                    />
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Category</Form.Label>
                    <Form.Select
                        name="categoryId"
                        value={product.category.id}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select a category</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </Form.Select>
                </Form.Group>
                <Button type="submit" variant="primary">{id ? 'Update' : 'Add'} Product</Button>
            </Form>
        </div>
    );
}

export default ProductForm;