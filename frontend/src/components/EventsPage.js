import React from 'react';
import { Typography, Box, Container } from '@mui/material';

const EventsPage = () => {
    return (
        <>
            {/* Background Image Section */}
            <Box sx={{
                minHeight: '100vh',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                backgroundImage: 'url("/HotelVenueImage.jpg")', // Ensure the image path is correct
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
                backgroundPosition: 'center',
                backgroundAttachment: 'fixed', // Keeps the background fixed while scrolling
            }}>
                <Typography variant="h2" align="center" sx={{ color: 'white', backgroundColor: 'rgba(0,0,0,0.5)', padding: '20px' }}>
                    Possible Events
                </Typography>
            </Box>

            {/* Event Details Section */}
            <Container sx={{ padding: '40px 0', backgroundColor: '#fff' }}>
                <Typography variant="h4" align="center" gutterBottom>
                    Upcoming Events
                </Typography>
                <Typography variant="h6" align="center">
                    Event 1: Wedding Reception
                </Typography>
                <Typography variant="h6" align="center">
                    Event 2: Corporate Meeting
                </Typography>
                <Typography variant="h6" align="center">
                    Event 3: Gala Dinner
                </Typography>
            </Container>

            {/* Additional Section */}
            <Box sx={{
                padding: '60px 20px',
                backgroundColor: '#f4f4f4',
                textAlign: 'center',
            }}>
                <Typography variant="h4" gutterBottom>
                    Plan Your Next Event with Us
                </Typography>
                <Typography variant="body1">
                    We offer a variety of event spaces, catering to both large and small gatherings.
                    Whether it's a wedding, corporate retreat, or private celebration, we have the
                    perfect venue for you.
                </Typography>
            </Box>

            {/* Footer Section */}
            <Box sx={{
                padding: '40px',
                backgroundColor: '#333',
                color: 'white',
                textAlign: 'center',
            }}>
                <Typography variant="h6">
                    Contact us to book your event!
                </Typography>
                <Typography variant="body2">
                    Email: events@seasideescape.com | Phone: (123) 456-7890
                </Typography>
            </Box>
        </>
    );
};

export default EventsPage;
