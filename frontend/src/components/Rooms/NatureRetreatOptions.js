import React from 'react';
import { Link } from 'react-router-dom';

import { Box, Typography, Divider, Grid, Card, CardContent, Button, CardMedia } from '@mui/material';
import { handleComfort, handleSuite } from './RoomModule'



const RoomOption = ({ title }) => {
    let rooms = localStorage.getItem("rooms");
    rooms = JSON.parse(rooms);
    let isRoomAvailable = rooms["NATURE_RETREAT"][title.substring(0,title.indexOf(" "))].total > 0;
    return (
        <Card sx={{backgroundColor: '#f2f2f2', padding: '1rem', position: 'relative', height: '100%', opacity: isRoomAvailable ? 1 : 0.5}}>
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
                    {isRoomAvailable ? "Available" : "Not available."}
                </Typography>

                {/* View Rates & Reserve */}
                {!isRoomAvailable ? null : title === "Suite Style" ? <Button
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
                    <RoomOption title="Economy Room" />
                </Grid>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Comfort Room" />
                </Grid>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Executive Room" />
                </Grid>
            </Grid>
        </Box>
    </Box>
);

export default NatureRetreatOptions;
