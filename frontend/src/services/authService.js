import axios from 'axios';

export const login = async (username, password) => {
    try {
        const response = await axios.post('http://localhost:8080/login', {
            username,
            password,
        });
        return response.data;
    } catch (error) {
        throw new Error('Login failed');
    }
};
