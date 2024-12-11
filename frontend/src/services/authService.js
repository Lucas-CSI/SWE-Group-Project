import axios from 'axios';
import Cookies from 'js-cookie';

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

export const getLoginStatus = () => {
    return !(!Cookies.get('username') || Cookies.get('username') === "")
}
