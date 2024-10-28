import React, { useState } from 'react';
import { Container, Typography, Button, Box } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const EventReservationSummary = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { formData, arrivalDate, departureDate, selectedVenueId, selectedFloor, venueName } = location.state || {};

    const handleConfirmReservation = async () => {
        try {
            const requestData = {
                venueId: selectedVenueId,
                eventDate: `${arrivalDate}T00:00:00`,
                eventName: formData.specialRequests || 'Default Event Name',
                guestEmail: formData.email
            };

            const response = await axios.post(`http://localhost:8080/events/book`, requestData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.status === 200) {
                const eventId = response.data.id;
                navigate(`/event-confirmation/${eventId}`, { state: { bookingDetails: response.data } });
            }
        } catch (error) {
            console.error('Error booking event:', error);
            alert('The selected venue is already booked. Please choose another one.');
            navigate('/event-reservation');
        }
    };

    return (
        <Box
            sx={{
                minHeight: '100vh',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                backgroundColor: '#f9f9f9',
            }}
        >
            <Container maxWidth="md" sx={{ mt: 5, mb: 5 }}>
                <Box
                    sx={{
                        padding: '20px',
                        borderRadius: '8px',
                        boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
                        backgroundColor: '#fff',
                    }}
                >
                    <Typography variant="h4" align="center" gutterBottom>
                        Reservation Summary
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Selected Venue:</strong> {venueName || 'Not Selected'}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Floor:</strong> {selectedFloor}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>First Name:</strong> {formData?.firstName || ''}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Last Name:</strong> {formData?.lastName || ''}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Email:</strong> {formData?.email || ''}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Phone Number:</strong> {formData?.phoneNumber || ''}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Number of Adults:</strong> {formData?.numberOfAdults || '0'}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Number of Kids:</strong> {formData?.numberOfKids || '0'}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Special Requests:</strong> {formData?.specialRequests || 'None'}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Arrival Date:</strong> {new Date(arrivalDate).toLocaleDateString()}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Departure Date:</strong> {new Date(departureDate).toLocaleDateString()}
                    </Typography>

                    <Button
                        variant="contained"
                        color="primary"
                        fullWidth
                        onClick={handleConfirmReservation}
                        sx={{ marginTop: '20px' }}
                    >
                        Confirm Reservation
                    </Button>
                </Box>
            </Container>
        </Box>
    );
};

export default EventReservationSummary;
