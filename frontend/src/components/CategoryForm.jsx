import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap';

function CategoryForm() {
    const [category, setCategory] = useState({ name: '', parentId: '' });
    const [categories, setCategories] = useState([]);
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetchCategories();
        if (id) {
            fetchCategory();
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

    const fetchCategory = async () => {
        try {
            const response = await axios.get(`http://localhost:8082/api/categories/${id}`);
            setCategory(response.data);
        } catch (error) {
            console.error('Error fetching category:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (id) {
                await axios.put(`http://localhost:8082/api/categories/${id}`, category);
            } else {
                await axios.post('http://localhost:8082/api/categories', category);
            }
            navigate('/categories');
        } catch (error) {
            console.error('Error saving category:', error);
        }
    };

    const handleChange = (e) => {
        setCategory({ ...category, [e.target.name]: e.target.value });
    };

    return (
        <div>
            <h1>{id ? 'Edit Category' : 'Add Category'}</h1>
            <Form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="name"
                    value={category.name}
                    onChange={handleChange}
                    placeholder="Category Name"
                    required
                />

                <Button className="m-lg-2" variant="outline-primary" type="submit">{id ? 'Update' : 'Add'} Category</Button>
            </Form>
        </div>
    );
}

export default CategoryForm;