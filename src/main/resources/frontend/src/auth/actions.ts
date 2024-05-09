import {setToken} from "../util";

const host = 'http://localhost:8081'

export const login = async (username: string, password: string) => {
    return makeRequest('/auth/login', {
        username: username,
        password: password,
    });
}

export const register = (username: string, password: string, email: string) => {
    return makeRequest('/auth/register', {
        username: username,
        password: password,
        email: email,
    });
}

const makeRequest = async (endpoint: string, body: object) => {
    try {
        const response = await fetch(host + endpoint, {
            method: 'POST',
            body: JSON.stringify(body),
        });
        const data = await response.json();
        if (!response.ok) {
            if (response.status === 500) return null;
        } else {
            setToken(data.token)
        }
        return data
    } catch (error) {
        return null;
    }
}