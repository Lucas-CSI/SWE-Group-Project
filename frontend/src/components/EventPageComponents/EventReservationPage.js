import React, { useEffect, useState } from 'react';
import { Container, TextField, Typography, Button, Radio, RadioGroup, FormControlLabel, FormLabel, Grid, Box, MenuItem, Select, InputLabel } from '@mui/material';
import { useNavigate, useLocation } from "react-router-dom";
import axios from 'axios';
import './EventReservationPage.css';

const EventReservationPage = () => {
    const [arrivalDate, setArrivalDate] = useState(new Date().toISOString().split("T")[0]);
    const [departureDate, setDepartureDate] = useState(new Date().toISOString().split("T")[0]);
    const [availableVenues, setAvailableVenues] = useState([]);
    const [selectedVenueId, setSelectedVenueId] = useState(null);
    const [venueName, setVenueName] = useState("");
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
    const [errors, setErrors] = useState({});

    const navigate = useNavigate();
    const location = useLocation();
    const { selectedEventName, selectedFloor } = location.state || {};

    useEffect(() => {
        const fetchAvailableVenues = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/events/venues/floor/${selectedFloor}`);
                setAvailableVenues(response.data);
            } catch (error) {
                console.error('Error fetching venues:', error);
            }
        };
        fetchAvailableVenues();
    }, [selectedFloor]);

    const handleVenueChange = (event) => {
        const venueId = event.target.value;
        setSelectedVenueId(venueId);
        const selectedVenue = availableVenues.find(v => v.id === parseInt(venueId));
        if (selectedVenue) {
            setVenueName(selectedVenue.name || `Venue ${selectedVenue.id} - Floor ${selectedVenue.floorNumber}`);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        if ((name === 'numberOfAdults' || name === 'numberOfKids') && value < 0) return;
        setFormData({ ...formData, [name]: value });
    };

    const validateForm = () => {
        const newErrors = {};
        const { firstName, lastName, email, phoneNumber, numberOfAdults } = formData;
        if (!firstName) newErrors.firstName = 'First name is required';
        if (!lastName) newErrors.lastName = 'Last name is required';
        if (!email) newErrors.email = 'Email is required';
        else if (!email.includes('@')) newErrors.email = 'Enter a valid email';
        if (!phoneNumber) newErrors.phoneNumber = 'Phone number is required';
        if (numberOfAdults <= 0) newErrors.numberOfAdults = 'At least one adult is required';
        if (!selectedVenueId) newErrors.selectedVenueId = 'Please select a venue';
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleProceedToSummary = () => {
        if (!validateForm()) return;
        navigate(`/event-reservation-summary`, {
            state: {
                formData,
                arrivalDate,
                departureDate,
                selectedVenueId,
                selectedFloor,
                venueName: `${venueName}: ${selectedEventName}`,
                selectedEventName
            }
        });
    };

    const handleSubmit = (e) => e.preventDefault();

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
            <Container maxWidth="md" className="event-reservation-container">
                <Typography variant="h4" className="event-reservation-title">
                    Reserve Your Event
                </Typography>
                <Typography variant="h6" align="center" color="textSecondary" gutterBottom>
                    {selectedEventName} on Floor {selectedFloor}
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
                                className="event-form-field"
                                error={!!errors.firstName}
                                helperText={errors.firstName}
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
                                className="event-form-field"
                                error={!!errors.lastName}
                                helperText={errors.lastName}
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
                                className="event-form-field"
                                error={!!errors.email}
                                helperText={errors.email}
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
                                className="event-form-field"
                                error={!!errors.phoneNumber}
                                helperText={errors.phoneNumber}
                            />
                        </Grid>

                        {/* Date Fields */}
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Arrival Date"
                                type="date"
                                value={arrivalDate}
                                onChange={(e) => setArrivalDate(e.target.value)}
                                fullWidth
                                className="event-form-field"
                                InputLabelProps={{ shrink: true }}
                                required
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Departure Date"
                                type="date"
                                value={departureDate}
                                onChange={(e) => setDepartureDate(e.target.value)}
                                fullWidth
                                className="event-form-field"
                                InputLabelProps={{ shrink: true }}
                                required
                            />
                        </Grid>

                        {/* Venue Selection */}
                        <Grid item xs={12}>
                            <InputLabel id="venue-label" color="textSecondary">Select Venue</InputLabel>
                            <Select
                                labelId="venue-label"
                                value={selectedVenueId}
                                onChange={handleVenueChange}
                                fullWidth
                                className="event-form-field"
                                error={!!errors.selectedVenueId}
                            >
                                {availableVenues.map((venue) => (
                                    <MenuItem key={venue.id} value={venue.id}>
                                        {venue.name || `Venue ${venue.id}`}
                                    </MenuItem>
                                ))}
                            </Select>
                            {errors.selectedVenueId && (
                                <Typography color="error" variant="body2">
                                    {errors.selectedVenueId}
                                </Typography>
                            )}
                        </Grid>

                        {/* Other Fields */}
                        <Grid item xs={12} sm={6}>
                            <TextField
                                label="Number of Adults"
                                name="numberOfAdults"
                                type="number"
                                value={formData.numberOfAdults}
                                onChange={handleChange}
                                fullWidth
                                className="event-form-field"
                                inputProps={{ min: "0" }}
                                required
                                error={!!errors.numberOfAdults}
                                helperText={errors.numberOfAdults}
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
                                className="event-form-field"
                                inputProps={{ min: "0" }}
                            />
                        </Grid>

                        {/* Payment Method */}
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

                        {/* Special Requests */}
                        <Grid item xs={12}>
                            <TextField
                                label="Do you have any special requests?"
                                name="specialRequests"
                                value={formData.specialRequests}
                                onChange={handleChange}
                                fullWidth
                                className="event-form-field"
                                multiline
                                rows={4}
                            />
                        </Grid>

                        {/* Submit Button */}
                        <Grid item xs={12}>
                            <Button
                                type="button"
                                variant="contained"
                                onClick={handleProceedToSummary}
                                className="event-reservation-button"
                                fullWidth
                            >
                                Proceed To Summary
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Container>
        </Box>
    );
};

export default EventReservationPage;
