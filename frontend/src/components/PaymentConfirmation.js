import React from "react";
import { useParams } from "react-router-dom";

const PaymentConfirmation = () => {
    const { reservationId } = useParams();

    return (
        <div className="confirmation-page">
            <h2>Payment Confirmation</h2>
            <p>Thank you for your payment! Your reservation ID is {reservationId}.</p>
            <p>An email has been sent to you with the reservation details.</p>
        </div>
    );
};

export default PaymentConfirmation;