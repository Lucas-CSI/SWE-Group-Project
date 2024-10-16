import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box } from '@mui/material';
import { Link } from 'react-router-dom';

const NavigationBar = () => {
    return (
        <AppBar position="fixed" sx={{ backgroundColor: 'rgba(0, 0, 0, 0.5)' }}>
            <Toolbar>
                <Typography variant="h6" sx={{ flexGrow: 1 }}>
                    SeaSideEscape Hotel
                </Typography>
                <Box>
                    <Button color="inherit" component={Link} to={"/"}>
                        Home
                    </Button>
                    <Button color="inherit" component={Link} to="/rooms">
                        Rooms & Suites
                    </Button>
                    <Button color="inherit" component={Link} to="/reservation">
                        Make a Reservation
                    </Button>
                    <Button color="inherit" component={Link} to="/events">
                        Events
                    </Button>

                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default NavigationBar;
