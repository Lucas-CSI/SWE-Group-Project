import React, { useEffect, useState } from 'react';
import { Container, TextField, Typography, Button, Radio, RadioGroup, FormControlLabel, FormLabel, Grid, Box, MenuItem, Select, InputLabel } from '@mui/material';
import { useNavigate } from "react-router-dom";
import axios from 'axios';

const EventReservationPage = () => {
    const [arrivalDate, setArrivalDate] = useState(new Date().toISOString().split("T")[0]);
    const [departureDate, setDepartureDate] = useState(new Date().toISOString().split("T")[0]);
    const [selectedFloor, setSelectedFloor] = useState(1); // Default to floor 1
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

    const navigate = useNavigate();

    // Fetch available venues based on the selected floor
    useEffect(() => {
        const fetchAvailableVenues = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/events/venues/floor/${selectedFloor}`);
                setAvailableVenues(response.data); // Set available venues from API response
            } catch (error) {
                console.error('Error fetching venues:', error);
            }
        };

        fetchAvailableVenues();
    }, [selectedFloor]);

    // Handle floor change
    const handleFloorChange = (event) => {
        setSelectedFloor(event.target.value);
        setSelectedVenueId(null); // Reset venue selection when floor changes
        setVenueName("");
    };

    // Handle venue change
    const handleVenueChange = (event) => {
        const venueId = event.target.value;
        setSelectedVenueId(venueId);

        const selectedVenue = availableVenues.find(v => v.id === parseInt(venueId));
        if (selectedVenue) {
            setVenueName(selectedVenue.name || `Venue ${selectedVenue.id} - Floor ${selectedVenue.floorNumber}`);
        }
    };

    // Handle input change
    const handleChange = (e) => {
        const { name, value } = e.target;

        if ((name === 'numberOfAdults' || name === 'numberOfKids') && value < 0) {
            return;
        }

        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleProceedToSummary = () => {
        if (!selectedVenueId) {
            alert('Please select a venue before proceeding to the summary.');
            return;
        }

        navigate(`/event-reservation-summary`, {
            state: {
                formData,
                arrivalDate,
                departureDate,
                selectedVenueId,
                selectedFloor,
                venueName // Use the venueName stored in the state
            }
        });

    };

    const handleSubmit = (e) => {
        e.preventDefault();
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

                            {/* Floor Selection */}
                            <Grid item xs={12}>
                                <InputLabel id="floor-label">Select Floor</InputLabel>
                                <Select
                                    labelId="floor-label"
                                    value={selectedFloor}
                                    onChange={handleFloorChange}
                                    fullWidth
                                    required
                                >
                                    <MenuItem value={1}>Floor 1</MenuItem>
                                    <MenuItem value={2}>Floor 2</MenuItem>
                                    <MenuItem value={3}>Floor 3</MenuItem>
                                </Select>
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
                                    {availableVenues.map((venue) => (
                                        <MenuItem key={venue.id} value={venue.id}>
                                            {venue.name || `Venue ${venue.id}`}
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
