import React, { useState } from 'react';
import { TextField, MenuItem, FormControlLabel, Checkbox, Button, Typography, Container, Box } from '@mui/material';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

// Enums for the select fields
const themes = ['Beach', 'Mountain', 'City', 'Forest'];
const qualityLevels = ['Economy', 'Standard', 'Premium', 'Luxury'];
const themeMap = {};
const qualityMap = {};

for(let i = 0; i < themes.length; ++i){
    themeMap[themes[i]] = i;
    qualityMap[qualityLevels[i]] = i;
}

export default function ReservationPage() {
    const navigate = useNavigate();
    const [reservation, setReservation] = useState({
        startDate: '',
        endDate: '',
        room: {
            bedType: '',
            theme: '',
            qualityLevel: '',
            smokingAllowed: false,
            oceanView: false,
        }
    });

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        const isRoomProperty = ['bedType', 'theme', 'qualityLevel', 'smokingAllowed', 'oceanView'].includes(name);

        setReservation((prev) => ({
            ...prev,
            ...(isRoomProperty ? { room: { ...prev.room, [name]: type === 'checkbox' ? checked : value } } : { [name]: type === 'checkbox' ? checked : value })
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(reservation)
            reservation.room.theme = themeMap[reservation.room.theme];
            reservation.room.qualityLevel = qualityMap[reservation.room.qualityLevel];
            await axios.post('http://localhost:8080/reservation/book', reservation, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            navigate(`/reservation/confirmation`);
        } catch (error) {
            console.error('Error submitting reservation', error);
            alert('There was an error submitting your reservation.');
        }
    };

    return (
        <Container maxWidth="sm">
            <Box mt={4} mb={2}>
                <Typography variant="h4" gutterBottom>Book Your Reservation</Typography>
            </Box>
            <form onSubmit={handleSubmit}>
                <TextField
                    label="Check-in Date"
                    type="date"
                    name="startDate"
                    value={reservation.startDate}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <TextField
                    label="Check-out Date"
                    type="date"
                    name="endDate"
                    value={reservation.endDate}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <TextField
                    label="Bed Type"
                    name="bedType"
                    value={reservation.room.bedType}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="Theme"
                    name="theme"
                    select
                    value={reservation.room.theme}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                >
                    {themes.map((option) => (
                        <MenuItem key={option} value={option}>
                            {option}
                        </MenuItem>
                    ))}
                </TextField>
                <TextField
                    label="Quality Level"
                    name="qualityLevel"
                    select
                    value={reservation.room.qualityLevel}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                >
                    {qualityLevels.map((option) => (
                        <MenuItem key={option} value={option}>
                            {option}
                        </MenuItem>
                    ))}
                </TextField>
                <FormControlLabel
                    control={
                        <Checkbox
                            name="smokingAllowed"
                            checked={reservation.room.smokingAllowed}
                            onChange={handleChange}
                        />
                    }
                    label="Smoking Allowed"
                />
                <FormControlLabel
                    control={
                        <Checkbox
                            name="oceanView"
                            checked={reservation.room.oceanView}
                            onChange={handleChange}
                        />
                    }
                    label="Ocean View"
                />
                <Button type="submit" variant="contained" color="primary" fullWidth>
                    Submit Reservation
                </Button>
            </form>
        </Container>
    );
}
