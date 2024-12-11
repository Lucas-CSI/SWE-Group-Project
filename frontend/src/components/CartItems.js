import React, { createContext, useEffect, useState } from 'react';
import Cookies from 'js-cookie';
import { generateGetRequest, generatePostRequest } from '../services/apiService';

export const CartContext = createContext();

const getLoginStatus = () => {
    return !(!Cookies.get('username') || Cookies.get('username') === '');
};

export const CartProvider = ({ children }) => {
    const [cartItems, setCartItems] = useState([]);

    const addToCart = (item) => {
        setCartItems((prevItems) => [...prevItems, item]);
    };

    const removeFromCart = (itemId) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.id !== itemId));
    };

    const clearCart = () => {
        setCartItems([]);
    };

    const getCart = async () => {
        try {
            const response = await generateGetRequest('/getCart');
            const cart = response.data;
            const tempItems = cart.map((item) => ({
                id: item.id,
                name: `${item.qualityLevel} Style Room`,
                price: item.maxRate,
                theme: item.theme,
                qualityLevel: item.qualityLevel,
                oceanView: item.oceanView,
                smokingAllowed: item.smokingAllowed,
                bedType: item.bedType,
                roomNumber: item.roomNumber,
            }));
            setCartItems(tempItems);
        } catch (error) {
            console.error('Failed to fetch cart:', error);
        }
    };

    const handleRemoveFromCart = async (roomId) => {
        try {
            const response = await generatePostRequest(`/cart/remove/${roomId}`, {});
            if (response.status === 200) {
                alert('Room removed from cart.');
                setCartItems((prevItems) => prevItems.filter((item) => item.id !== roomId));
            } else {
                alert('Failed to remove room from cart.');
            }
        } catch (error) {
            console.error('Error removing room from cart:', error);
        }
    };

    useEffect(() => {
        if (getLoginStatus()) {
            getCart();
        }
    }, []);

    return (
        <CartContext.Provider
            value={{ cartItems, setCartItems, handleRemoveFromCart, getCart, clearCart, removeFromCart, addToCart }}
        >
            {children}
        </CartContext.Provider>
    );
};
