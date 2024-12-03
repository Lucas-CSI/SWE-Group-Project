import React from 'react';
import {Link, useNavigate} from 'react-router-dom';

import { Box, Typography, Divider, Grid, Card, CardContent, Button, CardMedia } from '@mui/material';
import axios from "axios";
const sendRequest = async (reservation, navigate) => {
    localStorage.setItem("reservation", JSON.stringify(reservation));
    const response = await axios.post('http://localhost:8080/reservation/addRoom', reservation, {
        headers: {
            'Content-Type': 'application/json',
        },
        withCredentials: true
    });
    if (response.status === 200) {
        navigate(`/reservation/payment/${reservation.id}`);
    } else {
        alert("Error: Room not available.");
    }
};

const handleComfort = (navigate) => {
    let reservation = JSON.parse(localStorage.getItem("reservation"));
    reservation.room.bedType = "Queen";
    reservation.room.qualityLevel = 1;
    sendRequest(reservation, navigate);
};

const handleSuite = (navigate) => {
    let reservation = JSON.parse(localStorage.getItem("reservation"));
    reservation.room.bedType = "King";
    reservation.room.qualityLevel = 2;
    sendRequest(reservation, navigate);
};


const RoomOption = ({ title }) => {
    const navigate = useNavigate();

    const handleReserve = () => {
        const reservation = {
            room: {
                bedType: title === "Suite Style" ? "King" : "Queen",
                qualityLevel: title === "Suite Style" ? 2 : 1
            }
        };
        localStorage.setItem("reservation", JSON.stringify(reservation));
        navigate("/reservation/payment/${reservation.id}");
    };
    return (
        <Card sx={{backgroundColor: '#f2f2f2', padding: '1rem', position: 'relative', height: '100%'}}>
            {/* Placeholder Image */}
            <CardMedia>
                <Box
                    component="img"
                    src="singleNature.webp"
                    alt={title}
                    sx={{width: '100%', height: '150px', objectFit: 'cover', borderRadius: '8px'}}
                />
            </CardMedia>
                <CardContent sx={{display: 'flex', flexDirection: 'column', alignItems: 'flex-start'}}>
                    <Typography variant="h6" sx={{marginBottom: '0.5rem'}}>
                        {title}
                    </Typography>
                    <Typography variant="body2" sx={{marginBottom: '1rem'}}>
                        Text
                    </Typography>

                    {/* View Rates & Reserve */}
                    <Button
                        //component={Link}
                        onClick={handleReserve}
                        variant="outlined"
                        color="primary"
                        sx={{position: 'absolute', bottom: 10, right: 10}}
                    >
                        Reserve
                    </Button>
                </CardContent>
            </Card>
        );

        return (
            <Card sx={{backgroundColor: '#f2f2f2', padding: '1rem', position: 'relative', height: '100%'}}>
                {/* Placeholder Image */}
                <CardMedia>
                    <Box
                        component="img"
                        src="singleNature.webp"
                        alt={title}
                        sx={{width: '100%', height: '150px', objectFit: 'cover', borderRadius: '8px'}}
                    />
                </CardMedia>

                <CardContent sx={{display: 'flex', flexDirection: 'column', alignItems: 'flex-start'}}>
                    <Typography variant="h6" sx={{marginBottom: '0.5rem'}}>
                        {title}
                    </Typography>
                    <Typography variant="body2" sx={{marginBottom: '1rem'}}>
                        Text
                    </Typography>

                    {/* View Rates & Reserve */}
                    <Button
                        component={Link}
                        onClick={handleComfort}
                        variant="outlined"
                        color="primary"
                        sx={{position: 'absolute', bottom: 10, right: 10}}
                    >
                        Reserve
                    </Button>
                </CardContent>
            </Card>
        );
};

const NatureRetreatOptions = () => (
    <Box>
        {/*  Top */}
        <Box
            sx={{
                width: '100%',
                height: '400px',
                backgroundImage: `url('natureRetreatMain.jpg')`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                color: 'white',
                textAlign: 'center',
            }}
        >
            <Typography variant="h2" sx={{ backgroundColor: 'rgba(0, 0, 0, 0.2)', padding: '1rem' }}>
                Nature Retreat
            </Typography>
        </Box>

        {/* Room Options */}
        <Box sx={{ padding: '2rem', paddingBottom: '4rem' }}>
            <Typography variant="h5" sx={{ marginBottom: '1rem' }}>
                Explore Our Rooms
            </Typography>
            <Divider sx={{ marginBottom: '1rem' }} />
            <Grid container spacing={2}>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Single Room" />
                </Grid>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Double Room" />
                </Grid>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Family Room" />
                </Grid>
            </Grid>
        </Box>
    </Box>
);

export default NatureRetreatOptions;
