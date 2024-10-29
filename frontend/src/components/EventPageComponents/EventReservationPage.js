import React, { useState } from 'react';
import { Container, TextField, Typography, Button, Radio, RadioGroup, FormControlLabel, FormLabel, Grid, Box, MenuItem, Select, InputLabel } from '@mui/material';
import { useNavigate } from "react-router-dom";

const venues = [
    { id: 1, name: "Wedding Reception", description: "Celebrate your special day with us." },
    { id: 2, name: "Corporate Meeting", description: "Professional and elegant spaces for your business needs." },
    { id: 3, name: "Gala Dinner", description: "Host a grand dinner for your guests in our luxurious venue." }
];

const EventReservationPage = () => {
    const [arrivalDate, setArrivalDate] = useState(new Date().toISOString().split("T")[0]);
    const [departureDate, setDepartureDate] = useState(new Date().toISOString().split("T")[0]);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        numberOfAdults: '0',
        numberOfKids: '0',
        paymentMethod: 'credit-card',
        specialRequests: ''
    });

    const [selectedVenueId, setSelectedVenueId] = useState(null);
    const [selectedVenue, setSelectedVenue] = useState(null);

    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;

        // Prevent negative numbers for adults and kids
        if ((name === 'numberOfAdults' || name === 'numberOfKids') && value < 0) {
            return;
        }

        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleVenueChange = (event) => {
        const venueId = event.target.value;
        const venue = venues.find(v => v.id === venueId);
        setSelectedVenueId(venueId);
        setSelectedVenue(venue);
    };

    const handleProceedToSummary = () => {
        navigate(`/event-reservation-summary/${selectedVenueId}`, {
            state: { formData, arrivalDate, departureDate, selectedVenue }
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission logic here if needed
    };

    return (
        <Box
            sx={{
                minHeight: '100vh',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                backgroundImage: 'url("/EventReservationPageBackground.jpg")',
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat',
                backgroundAttachment: 'fixed',
            }}
        >
            <Container maxWidth="md" sx={{ mt: 5, mb: 5 }}>
                <Box
                    sx={{
                        padding: '20px',
                        borderRadius: '8px',
                        boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
                        backgroundColor: 'rgba(255, 255, 255, 0.85)',
                    }}
                >
                    <Typography variant="h4" align="center" gutterBottom>
                        Event Reservation
                    </Typography>
                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={3}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    label="First Name"
                                    name="firstName"
                                    value={formData.firstName}
                                    onChange={handleChange}
                                    fullWidth
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    label="Last Name"
                                    name="lastName"
                                    value={formData.lastName}
                                    onChange={handleChange}
                                    fullWidth
                                    required
                                />
                            </Grid>

                            <Grid item xs={12} sm={6}>
                                <TextField
                                    label="Email"
                                    name="email"
                                    type="email"
                                    value={formData.email}
                                    onChange={handleChange}
                                    fullWidth
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    label="Phone Number"
                                    name="phoneNumber"
                                    value={formData.phoneNumber}
                                    onChange={handleChange}
                                    fullWidth
                                    required
                                />
                            </Grid>

                            {/* Arrival Date */}
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    label="Arrival Date"
                                    name="arrivalDate"
                                    type="date"
                                    value={arrivalDate}
                                    onChange={(e) => setArrivalDate(e.target.value)}
                                    fullWidth
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                />
                            </Grid>

                            {/* Departure Date */}
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    label="Departure Date"
                                    name="departureDate"
                                    type="date"
                                    value={departureDate}
                                    onChange={(e) => setDepartureDate(e.target.value)}
                                    fullWidth
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                />
                            </Grid>

                            {/* Venue Selection */}
                            <Grid item xs={12}>
                                <InputLabel id="venue-label">Select Venue</InputLabel>
                                <Select
                                    labelId="venue-label"
                                    value={selectedVenueId}
                                    onChange={handleVenueChange}
                                    fullWidth
                                    required
                                >
                                    {venues.map((venue) => (
                                        <MenuItem key={venue.id} value={venue.id}>
                                            {venue.name}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </Grid>

                            <Grid item xs={12} sm={6}>
                                <TextField
                                    label="Number of Adults"
                                    name="numberOfAdults"
                                    type="number"
                                    value={formData.numberOfAdults}
                                    onChange={handleChange}
                                    fullWidth
                                    inputProps={{ min: "0" }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    label="Number of Kids (If any)"
                                    name="numberOfKids"
                                    type="number"
                                    value={formData.numberOfKids}
                                    onChange={handleChange}
                                    fullWidth
                                    inputProps={{ min: "0" }}
                                />
                            </Grid>

                            <Grid item xs={12}>
                                <FormLabel component="legend">Payment Method</FormLabel>
                                <RadioGroup
                                    row
                                    aria-label="payment-method"
                                    name="paymentMethod"
                                    value={formData.paymentMethod}
                                    onChange={handleChange}
                                >
                                    <FormControlLabel value="credit-card" control={<Radio />} label="Credit/Debit Card" />
                                </RadioGroup>
                            </Grid>

                            <Grid item xs={12}>
                                <TextField
                                    label="Do you have any special requests?"
                                    name="specialRequests"
                                    value={formData.specialRequests}
                                    onChange={handleChange}
                                    fullWidth
                                    multiline
                                    rows={4}
                                />
                            </Grid>

                            <Grid item xs={12}>
                                <Button
                                    type="button"
                                    variant="contained"
                                    color="primary"
                                    fullWidth
                                    onClick={handleProceedToSummary}
                                    sx={{
                                        padding: '12px',
                                        fontSize: '16px',
                                        backgroundColor: '#1976d2',
                                        '&:hover': {
                                            backgroundColor: '#115293',
                                        },
                                    }}
                                >
                                    Proceed To Summary
                                </Button>
                            </Grid>
                        </Grid>
                    </form>
                </Box>
            </Container>
        </Box>
    );
};

export default EventReservationPage;
