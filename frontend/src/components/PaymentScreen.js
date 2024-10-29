import React, { useState } from 'react';
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

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission, for example, send the data to your backend
        console.log('Form data submitted:', formData);
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
                        <Typography variant="body1">Stay Dates: [Placeholder]</Typography>
                        <Typography variant="body1">Total Stay: [Placeholder]</Typography>
                        {/* Add inputs for users to fill in the stay dates and total if needed */}
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
};

export default PaymentScreen;
