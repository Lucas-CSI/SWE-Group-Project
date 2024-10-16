import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';

const HomePage = () => {
    return (
        <Box>
            {/* AppBar for Header */}
            <AppBar position="static" sx={{ backgroundColor: 'rgba(0, 0, 0, 0.7)' }}>
                <Toolbar>
                    {/* SeaSide Hotel Name(Change the name to whatever) */}
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        SeaSideEscape Hotel
                    </Typography>

                    {/* Buttons on the right */}
                    <Button color="inherit">Rooms & Suites</Button>
                    <Button color="inherit">Dining</Button>
                    <Button component={Link} to="/events" color="inherit">Events</Button> {/* Link to Events */}
                    <Button color="inherit">Make a Reservation</Button>

                </Toolbar>
            </AppBar>

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
