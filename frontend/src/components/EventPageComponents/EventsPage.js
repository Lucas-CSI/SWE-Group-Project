import React, { useState, useEffect } from 'react';
import { Typography, Box, Container, Button, Card, CardContent, CardActions, CardMedia, MenuItem, FormControl, Select, InputLabel } from '@mui/material';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';

const EventsPage = () => {
    const [selectedFloor, setSelectedFloor] = useState(1); // Default floor 1
    const [venues, setVenues] = useState([]); // Venues for the selected floor
    const navigate = useNavigate();


    const events = [
        { id: 1, name: "Wedding Reception", description: "Celebrate your special day with us.", imageUrl: "WeddingReception.jpg" },
        { id: 2, name: "Corporate Meeting", description: "Professional and elegant spaces for your business needs.", imageUrl: "CorporateMeeting.jpg" },
        { id: 3, name: "Gala Dinner", description: "Host a grand dinner for your guests in our luxurious venue.", imageUrl: "GalaDinner.jpg" }
    ];

    useEffect(() => {
        // Fetch venues for the selected floor
        const fetchVenues = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/events/venues/floor/${selectedFloor}`);
                setVenues(response.data);
            } catch (error) {
                console.error("Error fetching venues:", error);
            }
        };
        fetchVenues();
    }, [selectedFloor]);

    const handleFloorChange = (event) => {
        setSelectedFloor(event.target.value);
    };

    const handleEventSelection = (event) => {
        const selectedEvent = events.find(e => e.id === event.id);
        navigate('/event-reservation/:eventId', {
            state: {
                selectedEventName: selectedEvent.name,
                selectedFloor: selectedFloor,
            }
        });
    };

    return (
        <>
            {/* Background Image Section */}
            <Box sx={{
                minHeight: '100vh',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                backgroundImage: 'url("/HotelVenueImage.jpg")',
                backgroundSize: 'cover',
                backgroundRepeat: 'no-repeat',
                backgroundPosition: 'center',
                backgroundAttachment: 'fixed',
            }}>
                <Typography variant="h2" align="center" sx={{ color: 'white', backgroundColor: 'rgba(0,0,0,0.5)', padding: '20px' }}>
                    Possible Events
                </Typography>
            </Box>

            {/* Floor Selector */}
            <Container sx={{ padding: '20px', textAlign: 'center' }}>
                <FormControl variant="outlined" sx={{ minWidth: 200 }}>
                    <InputLabel id="floor-select-label">Select Floor</InputLabel>
                    <Select
                        labelId="floor-select-label"
                        id="floor-select"
                        value={selectedFloor}
                        onChange={handleFloorChange}
                        label="Select Floor"
                    >
                        <MenuItem value={1}>1st Floor</MenuItem>
                        <MenuItem value={2}>2nd Floor</MenuItem>
                        <MenuItem value={3}>3rd Floor</MenuItem>
                    </Select>
                </FormControl>
            </Container>

            {/* Event Details Section with Reservation Button */}
            <Container sx={{ padding: '40px 0', backgroundColor: '#fff' }}>
                <Typography variant="h4" align="center" gutterBottom>
                    Available Venues on Floor {selectedFloor}
                </Typography>

                {events.map(event => (
                    <Card key={event.id} sx={{ maxWidth: 600, margin: '20px auto', padding: '20px' }}>
                        <CardMedia
                            component="img"
                            height="200"
                            image={event.imageUrl}
                            alt={event.name}
                        />
                        <CardContent>
                            <Typography variant="h5" align="center">{event.name}</Typography>
                            <Typography variant="body1" align="center" sx={{ marginBottom: '20px' }}>
                                {event.description}
                            </Typography>
                        </CardContent>
                        <CardActions sx={{ justifyContent: 'center' }}>
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => handleEventSelection(event)}
                            >
                                Reserve for {event.name}
                            </Button>
                        </CardActions>
                    </Card>
                ))}
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