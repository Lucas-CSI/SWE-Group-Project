import React, { useState } from 'react';
import axios from 'axios';

const PaymentForm = ({ reservationId }) => {
    const [paymentMethod, setPaymentMethod] = useState('');
    const [billingAddress, setBillingAddress] = useState('');
    const [amount, setAmount] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('/payments/pay', {
            reservationId,
            paymentMethod,
            billingAddress,
            amount
        })
            .then(response => {
                alert('Payment Successful!');
            })
            .catch(error => {
                console.error("Payment Error: ", error);
            });
    };

    return (
        <form className="payment-form" onSubmit={handleSubmit}>
            <h2>Enter Payment Details</h2>
            <div>
                <label>Payment Method</label>
                <input
                    type="text"
                    value={paymentMethod}
                    onChange={(e) => setPaymentMethod(e.target.value)}
                    placeholder="Credit Card"
                    required
                />
            </div>
            <div>
                <label>Billing Address</label>
                <input
                    type="text"
                    value={billingAddress}
                    onChange={(e) => setBillingAddress(e.target.value)}
                    placeholder="123 Main St"
                    required
                />
            </div>
            <div>
                <label>Amount</label>
                <input
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    required
                />
            </div>
            <button type="submit">Submit Payment</button>
        </form>
    );
};

export default PaymentForm;
