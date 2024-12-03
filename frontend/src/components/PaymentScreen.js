import React, { useState, useEffect } from 'react';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';

import { Container, TextField, Typography, Box, Grid, Button, Divider } from '@mui/material';

const PaymentScreen = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        address: '',
        city: '',
        state: '',
        zip: '',
        creditCardNumber: '',
        expirationMonth: '',
        expirationYear: '',
        cvv: '',
    });
    const [reservation, setReservation] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const storedReservation = JSON.parse(localStorage.getItem('reservation'));
        console.log('Retrieved reservation:', storedReservation);
        if (storedReservation) {
            setReservation(storedReservation);
        } else {
            alert("No reservation found. Please select a room.");
            navigate('/');
        }
    }, [navigate]);


    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!reservation) {
            alert('No reservation data found.');
            return;
        }

        const paymentData = {
            ...formData,
            reservation: {
                ...reservation,
                room: reservation.room,
            },
        };

        try {
            const response = await axios.post('http://localhost:8080/reservation/confirmPayment', paymentData, {
                headers: { 'Content-Type': 'application/json' },
                withCredentials: true,
            });

            if (response.status === 200) {
                // Clear reservation data from localStorage
                localStorage.removeItem('reservation');

                // Navigate to confirmation page
                navigate(`/reservation/confirmation/${response.data.id}`);
            } else {
                alert('Payment or reservation confirmation failed.');
            }
        } catch (error) {
            console.error('Error processing payment:', error);
            alert('Payment failed. Please try again.');
        }
    };

    return (
        <Container maxWidth="md">
            <Typography variant="h4" gutterBottom>
                Complete Your Booking
            </Typography>

            <Grid container spacing={2} mb={4}>
                <Grid item xs={12} md={8}>
                    <Box component="form" onSubmit={handleSubmit} noValidate>
                        <Typography variant="h6">Guest Information</Typography>

                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    label="First Name"
                                    name="firstName"
                                    value={formData.firstName}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    label="Last Name"
                                    name="lastName"
                                    value={formData.lastName}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    label="Email Address"
                                    name="email"
                                    type="email"
                                    value={formData.email}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    label="Address"
                                    name="address"
                                    value={formData.address}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    label="City"
                                    name="city"
                                    value={formData.city}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} sm={3}>
                                <TextField
                                    fullWidth
                                    label="State"
                                    name="state"
                                    value={formData.state}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} sm={3}>
                                <TextField
                                    fullWidth
                                    label="Zip Code"
                                    name="zip"
                                    value={formData.zip}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                        </Grid>

                        <Divider sx={{ my: 4 }} />

                        <Typography variant="h6">Payment Information</Typography>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    label="Credit/Debit Card Number"
                                    name="creditCardNumber"
                                    value={formData.creditCardNumber}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={6} sm={3}>
                                <TextField
                                    fullWidth
                                    label="Expiration Month"
                                    name="expirationMonth"
                                    value={formData.expirationMonth}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={6} sm={3}>
                                <TextField
                                    fullWidth
                                    label="Expiration Year"
                                    name="expirationYear"
                                    value={formData.expirationYear}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} sm={3}>
                                <TextField
                                    fullWidth
                                    label="CVV"
                                    name="cvv"
                                    value={formData.cvv}
                                    onChange={handleChange}
                                    required
                                />
                            </Grid>
                        </Grid>

                        <Button
                            variant="contained"
                            color="primary"
                            type="submit"
                            sx={{ mt: 3 }}
                            fullWidth
                        >
                            Complete Payment
                        </Button>
                    </Box>
                </Grid>

                <Grid item xs={12} md={4}>
                    <Box>
                        <Typography variant="h6">Booking Information</Typography>
                        <Typography variant="body1">Check-In Date: {reservation.startDate || '[Placeholder]'}</Typography>
                        <Typography variant="body1">Check-Out Date: {reservation.endDate || '[Placeholder]'}</Typography>
                        <Typography varient="body1"> Room Category: {reservation.room.qualityLevel === 2 ? 'Suite Style' : 'Comfort'}</Typography>
                        <Typography varient="body1">Room Type: {reservation.room.bedType || '[Not Specified]'}</Typography>
                        <Typography varient="body1">Room Rate: [Placeholder]</Typography>
                        {/* Add inputs for users to fill in the stay dates and total if needed */}
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
};

export default PaymentScreen;
