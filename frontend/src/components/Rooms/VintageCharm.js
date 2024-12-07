import React from 'react';
import { Link } from 'react-router-dom';
import { Box, Typography, Divider, Grid, Card, CardContent, Button, CardMedia } from '@mui/material';
import axios from "axios";

import { handleSubmitRoom, generateRoomData} from './RoomModule'

const theme = "VINTAGE_CHARM";

const RoomOption = ({ title }) => {
    let rooms = localStorage.getItem("rooms");
    rooms = JSON.parse(rooms);
    let isRoomAvailable = rooms[theme][title.substring(0,title.indexOf(" "))].total > 0;
    return (
        <Card sx={{backgroundColor: '#f2f2f2', padding: '1rem', position: 'relative', height: '100%'}}>
            {/* Placeholder Image */}
            <CardMedia>
                <Box
                    component="img"
                    src="vintageSuite.webp"
                    alt={title}
                    sx={{width: '100%', height: '150px', objectFit: 'cover', borderRadius: '8px', opacity: isRoomAvailable ? 1 : 0.3}}
                />
            </CardMedia>

            <CardContent sx={{display: 'flex', flexDirection: 'column', alignItems: 'flex-start', opacity: isRoomAvailable ? 1 : 0.3}}>
                <Typography variant="h6" sx={{marginBottom: '0.5rem'}}>
                    {title}
                </Typography>
                <Typography variant="body2" sx={{marginBottom: '1rem'}}>
                    {isRoomAvailable ? "Available" : "Not available."}
                </Typography>

                {/* View Rates & Reserve */}
                {!isRoomAvailable ? null : title === "Suite Style" ? <Button
                    component={Link}
                    onClick={() => handleSubmitRoom(generateRoomData(theme, 2))}
                    variant="outlined"
                    color="primary"
                    sx={{position: 'absolute', bottom: 10, right: 10}}
                >
                    Reserve
                </Button> : <Button
                    component={Link}
                    onClick={() => handleSubmitRoom(generateRoomData(theme, 1))}
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
