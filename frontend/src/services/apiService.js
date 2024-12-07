import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const getMessages = async () => {
    try {
        const response = await axios.get(`${API_URL}/hello`);
        return response.data;
    } catch (error) {
        console.error('Error fetching messages:', error);
        throw error;
    }
};


export const generateGetRequest = async(endpoint, headers = {}) => {
    let successful;
    try {
        if(endpoint.at(0) === '/') endpoint = endpoint.substring(1, endpoint.length);
        successful = await axios.get('http://localhost:8080/' + endpoint, {...headers, withCredentials: true});
    }catch (e) {
        successful = e
    }
    return successful;
}



export const generatePostRequest = async(endpoint, params, headers = {}) => {
    let successful;
    try {
        if(endpoint.at(0) === '/') endpoint = endpoint.substring(1, endpoint.length);
        successful = await axios.post('http://localhost:8080/' + endpoint, params, {...headers, withCredentials: true});
    }catch (e) {
        successful = e
    }
    return successful;
}

export const saveMessage = async (content) => {
    try {
        const response = await axios.post(`${API_URL}/hello`, { content });
        return response.data;
    } catch (error) {
        console.error('Error saving message:', error);
        throw error;
    }
};