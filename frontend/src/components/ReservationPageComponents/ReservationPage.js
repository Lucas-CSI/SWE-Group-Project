// src/components/ReservationForm.js

import React, { useState } from "react";
import axios from "axios";

const ReservationPage = () => {
    const [formData, setFormData] = useState({
        startDate: "",
        endDate: "",
        specialRequests: "",
        room: {
            theme: 1,
            qualityLevel: 1,
            smokingAllowed: false,
            bedType: "Suite",
            oceanView: 1,
        }
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
                    <label htmlFor="startDate">Check-In Date:</label>
                    <input
                        type="date"
                        id="startDate"
                        name="startDate"
                        value={formData.startDate}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="endDate">Check-Out Date:</label>
                    <input
                        type="date"
                        id="endDate"
                        name="endDate"
                        value={formData.endDate}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="theme">Theme:</label>
                    <input
                        type="number"
                        id="theme"
                        name="theme"
                        value={formData.room.theme}
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
                        value={formData.room.bedType}
                        onChange={handleChange}
                        required
                    >
                        <option value="Standard">Standard</option>
                        <option value="Deluxe">Deluxe</option>
                        <option value="Suite">Suite</option>
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