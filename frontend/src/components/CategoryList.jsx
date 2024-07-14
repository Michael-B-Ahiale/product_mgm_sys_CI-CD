import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Table, Button, Form, InputGroup } from 'react-bootstrap';

function CategoryList() {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await axios.get('http://localhost:8082/api/categories');
            console.log('API response:', response.data); // Debug log
            if (Array.isArray(response.data)) {
                setCategories(response.data);
            } else {
                console.error('API did not return an array:', response.data);
                setError('Unexpected data format received from server');
            }
        } catch (error) {
            console.error('Error fetching categories:', error);
            setError('Failed to fetch categories. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        try {
            await axios.delete(`http://localhost:8082/api/categories/${id}`);
            fetchCategories();
        } catch (error) {
            console.error('Error deleting category:', error);
            setError('Failed to delete category. Please try again.');
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    console.log('Categories state:', categories); // Debug log

    return (
        <div>
            <h1>Category List</h1>
            {Array.isArray(categories) && categories.length > 0 ? (
                <Table>
                    {categories.map((category) => (
                        <tr key={category.id}>
                            <td >{category.name}</td>
                            <Link to={`/categories/edit/${category.id}`} className="btn btn-warning m-2">Edit</Link>
                            <Button variant="outline-danger" size="small" onClick={() => handleDelete(category.id)}>Delete</Button>
                        </tr>
                    ))}
                </Table>
            ) : (
                <p>No categories found.</p>
            )}
        </div>
    );
}

export default CategoryList;