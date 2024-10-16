import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';

const HomePage = () => {
    return (
        <Box>
            {/* Main Background Section(This is just an example will be changed) */}
            <Box
                sx={{
                    minHeight: '100vh',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    backgroundImage: `url("/OceanViewHotel.jpg")`, //Changes the image
                    backgroundSize: 'cover', //Covers the whole screen(can be changed)
                    backgroundPosition: 'center', //Centers the image
                    backgroundRepeat: 'no-repeat',
                    backgroundAttachment: 'fixed',
                    backgroundColor: 'rgba(0, 0, 0, 0.5)',
                }}
            >
                <Typography variant="h2" color="white" align="center">
                    Welcome to SeaSide Escape!
                </Typography>
            </Box>
        </Box>
    );
};

export default HomePage;
