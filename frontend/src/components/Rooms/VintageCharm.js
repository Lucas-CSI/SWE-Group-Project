import React from 'react';
import { Link } from 'react-router-dom';
import { Box, Typography, Divider, Grid, Card, CardContent, Button, CardMedia } from '@mui/material';
import axios from "axios";

import { handleComfort, handleSuite, formattedGetAvailableRooms } from './RoomModule'

const RoomOption = ({ title }) => {
    let rooms = localStorage.getItem("rooms")
    return (
        <Card sx={{backgroundColor: '#f2f2f2', padding: '1rem', position: 'relative', height: '100%'}}>
            {/* Placeholder Image */}
            <CardMedia>
                <Box
                    component="img"
                    src="vintageSuite.webp"
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
                {title === "Suite Style" ? <Button
                    component={Link}
                    onClick={handleSuite}
                    variant="outlined"
                    color="primary"
                    sx={{position: 'absolute', bottom: 10, right: 10}}
                >
                    Reserve
                </Button> : <Button
                    component={Link}
                    onClick={handleComfort}
                    variant="outlined"
                    color="primary"
                    sx={{position: 'absolute', bottom: 10, right: 10}}
                >
                    Reserve
                </Button>}
            </CardContent>
        </Card>
    );
};


const VintageCharm = () => (
    <Box>
        {/* Top */}
        <Box
            sx={{
                width: '100%',
                height: '400px',
                backgroundImage: `url('TBD.jpg')`,
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
                Vintage Charm
            </Typography>
        </Box>

        {/* Room Options */}
        <Box sx={{ padding: '2rem', paddingBottom: '4rem' }}>
            <Typography variant="h5" sx={{ marginBottom: '1.5rem' }}>
                Explore Our Rooms
            </Typography>
            <Divider sx={{ marginBottom: '1.5rem' }} />
            <Grid container spacing={4} justifyContent="center">
                <Grid item xs={12} md={4}>
                    <RoomOption title="Suite Style" />
                </Grid>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Deluxe Style" />
                </Grid>
            </Grid>
        </Box>
    </Box>
);

export default VintageCharm;
