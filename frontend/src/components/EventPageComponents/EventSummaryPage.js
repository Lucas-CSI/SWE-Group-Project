import React, {useState} from 'react';
import { Container, Typography, Button, Box } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';  // Import Axios for API calls

const EventReservationSummary = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { formData, arrivalDate, departureDate } = location.state || {};  // Access form data and dates

    const handleConfirmReservation = async () => {
        try {
            const params = new URLSearchParams({
                venueId: formData.venueId,
                eventDate: arrivalDate.toISOString(),
                eventName: formData.specialRequests || 'Default Event Name',
                guestEmail: formData.email,
            });

            const response = await axios.post(`http://localhost:8080/events/book`, params, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            if (response.status === 200) {
                const eventId = response.data.id;  // Assuming the response contains event booking id
                navigate(`/event-confirmation/${eventId}`, { state: { bookingDetails: response.data } });
            }
        } catch (error) {
            console.error('Error booking event:', error);
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
