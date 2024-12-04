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
            ...(isRoomProperty
                ? { room: { ...prev.room, [name]: type === 'checkbox' ? checked : value } }
                : { [name]: type === 'checkbox' ? checked : value })
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!reservation.startDate || !reservation.endDate) {
            alert("Please select both check-in and check-out dates.");
            return;
        }

        try {
            console.log(reservation)
            reservation.room.theme = themeMap[reservation.room.theme];
            reservation.room.qualityLevel = qualityMap[reservation.room.qualityLevel];

            const reservationData = {
                startDate: reservation.startDate,
                endDate: reservation.endDate,
                room: {
                    ...reservation.room,
                },
            };

            localStorage.setItem("reservation", JSON.stringify(reservation));
            navigate(`/rooms`);
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
