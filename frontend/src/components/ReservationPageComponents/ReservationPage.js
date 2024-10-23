// src/components/ReservationForm.js

import React, { useState } from "react";
import axios from "axios";

const ReservationPage = () => {
    const [formData, setFormData] = useState({
        checkInDate: "",
        checkOutDate: "",
        guests: 1,
        roomType: "standard",
        specialRequests: "",
    });

    const [loading, setLoading] = useState(false); // Loading state
    const [error, setError] = useState(""); // Error state
    const [success, setSuccess] = useState(""); // Success state

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true); // Set loading state
        setError(""); // Clear previous errors
        setSuccess(""); // Clear success message

        // The URL where you want to send the data (replace this with your own API endpoint)
        const url = "http://localhost:8080/reservation/book";

        try {
            const response = await axios.post(url, formData);
            console.log("Response:", response.data);
            setSuccess("Reservation submitted successfully!");
        } catch (err) {
            console.error("Error:", err);
            setError("Failed to submit reservation. Please try again.");
        } finally {
            setLoading(false); // End loading state
        }
    };

    return (
        <div className="reservation-form">
            <h2>Make a Reservation</h2>
            {loading && <p>Submitting reservation...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            {success && <p style={{ color: "green" }}>{success}</p>}

            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="checkInDate">Check-In Date:</label>
                    <input
                        type="date"
                        id="checkInDate"
                        name="checkInDate"
                        value={formData.checkInDate}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="checkOutDate">Check-Out Date:</label>
                    <input
                        type="date"
                        id="checkOutDate"
                        name="checkOutDate"
                        value={formData.checkOutDate}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="guests">Number of Guests:</label>
                    <input
                        type="number"
                        id="guests"
                        name="guests"
                        value={formData.guests}
                        onChange={handleChange}
                        min="1"
                        max="10"
                        required
                    />
                </div>

                <div>
                    <label htmlFor="roomType">Room Type:</label>
                    <select
                        id="roomType"
                        name="roomType"
                        value={formData.roomType}
                        onChange={handleChange}
                        required
                    >
                        <option value="standard">Standard</option>
                        <option value="deluxe">Deluxe</option>
                        <option value="suite">Suite</option>
                    </select>
                </div>

                <div>
                    <label htmlFor="specialRequests">Special Requests:</label>
                    <textarea
                        id="specialRequests"
                        name="specialRequests"
                        value={formData.specialRequests}
                        onChange={handleChange}
                    />
                </div>

                <button type="submit" disabled={loading}>Submit Reservation</button>
            </form>
        </div>
    );
};

export default ReservationPage;
