// src/components/EventConfirmationPage.js
import React from 'react';
import { Box, Typography } from '@mui/material';

const EventConfirmationPage = () => {
    return (
        <Box sx={{ padding: '20px', textAlign: 'center' }}>
            <Typography variant="h4" gutterBottom>
                Reservation Confirmed!
            </Typography>
            <Typography variant="body1">
                Thank you for reserving your event with us.
            </Typography>
        </Box>
    );
};

export default EventConfirmationPage;
