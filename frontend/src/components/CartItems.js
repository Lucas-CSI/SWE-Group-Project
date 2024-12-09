
import React, {createContext, useEffect, useState} from 'react';
import Cookies from 'js-cookie';
import {generateGetRequest, generatePostRequest} from "../services/apiService";

export const CartContext = createContext();

const getLoginStatus = () => {
    return !(!Cookies.get('username') || Cookies.get('username') === "")
}

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
        let response = await generateGetRequest("/getCart");
        let cart = response.data;
        let tempItems = [];

        for(let i in cart){
            tempItems = [...tempItems,{id: cart[i].id, name: cart[i].qualityLevel + " Style Room", price: cart[i].maxRate}];
            console.log(cart[i]);
            console.log(cart[i].id);
        }
        setCartItems(tempItems);
    }

    const handleRemoveFromCart = async (roomId) => {
        const response = await generatePostRequest(`/cart/remove/${roomId}`, {});
        if (response.status === 200) {
            alert("Room removed from cart.");
            setCartItems((prevItems) => prevItems.filter((item) => item.id !== roomId));
        } else {
            alert("Failed to remove room from cart.");
        }
    };

    useEffect(() => {
        if(getLoginStatus()) {
            getCart();
        }
        getCart();
    }, []);

    return (
        <CartContext.Provider value={{ cartItems, setCartItems, handleRemoveFromCart, getCart, clearCart, removeFromCart, addToCart }}>
            {children}
        </CartContext.Provider>
    );
};
