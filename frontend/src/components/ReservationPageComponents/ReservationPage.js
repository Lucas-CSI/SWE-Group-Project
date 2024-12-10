import React, { useState } from 'react';
import {
    TextField,
    MenuItem,
    FormControlLabel,
    Checkbox,
    Button,
    Typography,
    Container,
    Box,
    Card,
    CardMedia, CardContent, CardActions,
    Grid2
} from '@mui/material';
import axios from 'axios';
import {Link, useNavigate} from "react-router-dom";


// Enums for the select fields
const themes = ['Nature Retreat', 'Urban Elegance', 'Vintage Charm'];
const qualityLevels = ['Economy', 'Standard', 'Premium', 'Luxury'];
const themeMap = {};
const qualityMap = {};
var cardStyle = {
    display: 'block',
    width: '25vw',
    height: '43vw'
}

const apiService = require("../../services/apiService");

for(let i = 0; i < themes.length; ++i){
    themeMap[themes[i]] = i;
    qualityMap[qualityLevels[i]] = i;
}

const events = [
    { id: 1, name: "Nature Retreat", description: "Celebrate your special day with us.", imageUrl: "WeddingReception.jpg" },
    { id: 2, name: "Urban Elegance", description: "Professional and elegant spaces for your business needs.", imageUrl: "CorporateMeeting.jpg" },
    { id: 3, name: "Vintage Charm", description: "Host a grand dinner for your guests in our luxurious venue.", imageUrl: "GalaDinner.jpg" }
];

export default function ReservationPage() {
    const navigate = useNavigate();
    let [reservation, setReservation] = useState({
        checkInDate: "",
        checkOutDate: ""
    });
    let reservationData = new FormData();

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        console.log(name);
        reservationData.set(name, value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(reservation);
        const response = await apiService.generatePostRequest("reservation/new", reservationData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }
        );
        if(response.status === 200)
            navigate(`/rooms`);
        else{
            // TODO: ADD ERROR TEXT INSTEAD OF USING ALERT
            console.error('Error submitting reservation', response.response.data);
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
                    name="checkInDate"
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
                    name="checkOutDate"
                    value={reservation.endDate}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
                <Button type="submit" variant="contained" color="primary" fullWidth>
                    Submit Reservation
                </Button>
            </form>
        </Container>
    );
}

/*
 <FormControlLabel
                    control={
                        <Checkbox
                            name="isSmokingAllowed"
                            checked={reservation.rooms[0].isSmokingAllowed}
                            onChange={handleChange}
                        />
                    }
                    label="Smoking Allowed"
                />
                <FormControlLabel
                    control={
                        <Checkbox
                            name="oceanView"
                            checked={reservation.rooms[0].oceanView}
                            onChange={handleChange}
                        />
                    }
                    label="Ocean View"
                />
 */