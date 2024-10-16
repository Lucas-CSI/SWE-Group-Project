// src/components/EventsPage.js
import React from 'react';
import { Typography, Box } from '@mui/material';

const EventsPage = () => {
    return (
        <Box sx={{ minHeight: '100vh', display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center' }}>
            <Typography variant="h2" align="center">
                Events
            </Typography>
            <Typography variant="h6" align="center">
                Upcming Events: TEST
            </Typography>
        </Box>
    );
};

export default EventsPage;
