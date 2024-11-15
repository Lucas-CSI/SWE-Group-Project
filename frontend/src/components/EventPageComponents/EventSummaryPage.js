import React, { useState } from 'react';
import { Container, Typography, Button, Box, TextField, RadioGroup, FormControlLabel, Radio } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const EventReservationSummary = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const { formData, arrivalDate, departureDate, selectedVenueId, selectedFloor, venueName, selectedEventName } = location.state || {};

    // Payment and Booking State
    const [paymentMethod, setPaymentMethod] = useState("Credit/Debit Card");
    const [cardNumber, setCardNumber] = useState("");
    const [expirationDate, setExpirationDate] = useState("");
    const [cvv, setCvv] = useState("");
    const [billingAddress, setBillingAddress] = useState("");
    const [venueId, setVenueId] = useState("");
    const [isBooking, setIsBooking] = useState(false);

    // Step 1: Book the event
    const handleConfirmReservation = async () => {
        setIsBooking(true);
        try {
            // Step 1: Book the event
            console.log("Initiating event booking with data:");
            const bookingData = {
                venueId: selectedVenueId,
                eventDate: `${arrivalDate}T00:00:00`,
                eventName: venueName || 'Default Event Name',
                guestEmail: formData.email
            };
            console.log("Booking Data:", bookingData);

            // Check if critical values are defined
            if (!selectedVenueId || !arrivalDate || !formData.email) {
                console.error("Missing required booking data:", { selectedVenueId, arrivalDate, email: formData.email });
                alert("Missing required booking information. Please check your inputs.");
                return;
            }

            const bookingResponse = await axios.post(`http://localhost:8080/events/book`, bookingData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (bookingResponse.status === 200) {
                const eventBookingId = bookingResponse.data.id;
                console.log('Booking Response Data:', bookingResponse.data);
                console.log('Extracted Event Booking ID:', eventBookingId);

                if (!eventBookingId) {
                    console.error("Event Booking ID is missing from the response.");
                    alert("Event booking failed to return an ID. Please try again.");
                    return;
                }

                // Step 2: Process the payment if booking was successful
                await processPayment(eventBookingId);
            } else {
                console.error("Unexpected booking response status:", bookingResponse.status);
                alert("Booking failed. Please try again.");
            }
        } catch (error) {
            console.error('Error booking event:', error);
            alert('The selected venue is already booked or payment failed. Please try again.');
        } finally {
            setIsBooking(false);
        }
    };

// Step 2: Process the payment
    const processPayment = async (eventBookingId) => {
        try {
            const paymentData = {
                eventBookingId,  // Use the ID from the booking step
                paymentMethod,
                billingAddress,
                cardNumber,
                expirationDate,
                cvv,
                amount: 4000.00 // Replace with actual calculated amount if needed
            };
            console.log("Payment Data:", paymentData);

            const paymentResponse = await axios.post(`http://localhost:8080/payments/payEvent`, paymentData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (paymentResponse.status === 200) {
                console.log('Payment Response Data:', paymentResponse.data);

                // Redirect to confirmation page on successful payment
                navigate(`/event-confirmation/${eventBookingId}`, { state: { bookingDetails: paymentResponse.data } });
            } else {
                console.error("Unexpected payment response status:", paymentResponse.status);
                alert("Payment processing failed. Please try again.");
            }
        } catch (error) {
            console.error('Error processing payment:', error);
            alert('Payment processing failed. Please try again.');
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
                    {/* Display reservation details */}
                    <Typography variant="body1" gutterBottom>
                        <strong>Selected Venue:</strong> {venueName || 'Not Selected'}
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        <strong>Event Name:</strong> {formData?.specialRequests || selectedEventName || 'Not Selected'}
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

                    <Typography variant="h5" align="center" gutterBottom sx={{ mt: 3 }}>
                        Payment Information
                    </Typography>

                    {/* Payment Method Selection */}
                    <RadioGroup
                        row
                        value={paymentMethod}
                        onChange={(e) => setPaymentMethod(e.target.value)}
                    >
                        <FormControlLabel value="Credit/Debit Card" control={<Radio />} label="Credit/Debit Card" />
                        {/* Additional payment options if needed */}
                    </RadioGroup>

                    {/* Payment Details Form */}
                    {paymentMethod === "Credit/Debit Card" && (
                        <>
                            <TextField
                                label="Card Number"
                                variant="outlined"
                                fullWidth
                                margin="normal"
                                value={cardNumber}
                                onChange={(e) => setCardNumber(e.target.value)}
                            />
                            <TextField
                                label="Expiry Date (MM/YY)"
                                variant="outlined"
                                fullWidth
                                margin="normal"
                                value={expirationDate}
                                onChange={(e) => setExpirationDate(e.target.value)}
                            />
                            <TextField
                                label="CVV"
                                variant="outlined"
                                fullWidth
                                margin="normal"
                                value={cvv}
                                onChange={(e) => setCvv(e.target.value)}
                            />
                        </>
                    )}

                    <TextField
                        label="Billing Address"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={billingAddress}
                        onChange={(e) => setBillingAddress(e.target.value)}
                    />

                    <Button
                        variant="contained"
                        color="primary"
                        fullWidth
                        onClick={handleConfirmReservation}
                        sx={{ marginTop: '20px' }}
                        disabled={isBooking}
                    >
                        {isBooking ? 'Processing...' : 'Confirm Reservation'}
                    </Button>
                </Box>
            </Container>
        </Box>
    );
};

export default EventReservationSummary;
