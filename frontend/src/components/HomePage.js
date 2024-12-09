import React from 'react';
import { Typography, Button, Box, Grid, Card, CardContent, CardMedia } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';

const HomePage = () => {
    const navigate = useNavigate();

    return (
        <Box sx={{ margin: 0, padding: 0, width: '100%', overflowX: 'hidden' }}>
            {/* Background Section */}
            <Box className="background-video-container">
                {/* Background Video */}
                <video autoPlay loop muted className="background-video">
                    <source src="seaWaveHome.mp4" type="video/mp4" />
                    Your browser does not support the video tag.
                </video>

                {/* Dark Overlay */}
                <Box className="background-overlay"></Box>

                {/* Hero Text */}
                <Typography className="hero-text">
                    Welcome to SeaSide Escape!
                </Typography>
            </Box>

            {/* Check-in and Check-out Section */}
            <Box className="check-in-out-container">
                <Box className="check-in-out-box">
                    <Typography variant="h6" className="check-in-out-label">
                        Check-in
                    </Typography>
                    <Button variant="contained" className="check-in-out-button">
                        Select Date
                    </Button>
                </Box>
                <Box className="check-in-out-box">
                    <Typography variant="h6" className="check-in-out-label">
                        Check-out
                    </Typography>
                    <Button variant="contained" className="check-in-out-button">
                        Select Date
                    </Button>
                </Box>
            </Box>

            {/* Vacation Preview Section */}
            <Box sx={{ padding: '2rem' }}>
                {/* Section Header */}
                <Box sx={{ display: 'flex', alignItems: 'center', marginBottom: '1rem' }}>
                    <Typography variant="h4" sx={{ textAlign: 'left', marginRight: '1rem' }}>
                        Experiences
                    </Typography>
                    <Box sx={{ flexGrow: 1, height: '1px', backgroundColor: 'grey.500' }} />
                </Box>

                {/* Experiences Grid */}
                <Grid container spacing={4} justifyContent="center" alignItems="center">
                    {/* Spa */}
                    <Grid item xs={12} sm={6} md={4} display="flex" justifyContent="center">
                        <Card sx={{ maxWidth: 500 }}>
                            <CardMedia
                                component="img"
                                height="200"
                                image="spaPic.jpg"
                                alt="Spa Treatment"
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    Relaxation
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Indulge in Tranquility
                                </Typography>
                                <Box sx={{ display: 'flex', justifyContent: 'flex-end', marginTop: '1rem' }}>
                                    <Button size="small" color="primary" onClick={() => navigate('/experiences')}>
                                        Learn More
                                    </Button>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>

                    {/* Snorkeling */}
                    <Grid item xs={12} sm={6} md={4} display="flex" justifyContent="center">
                        <Card sx={{ maxWidth: 500 }}>
                            <CardMedia
                                component="img"
                                height="200"
                                image="barGrillePic.webp"
                                alt="Snorkeling"
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    Culture
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Discover the Depths
                                </Typography>
                                <Box sx={{ display: 'flex', justifyContent: 'flex-end', marginTop: '1rem' }}>
                                    <Button size="small" color="primary" onClick={() => navigate('/experiences')}>
                                        Learn More
                                    </Button>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>

                    {/* Bar & Grille */}
                    <Grid item xs={12} sm={6} md={4} display="flex" justifyContent="center">
                        <Card sx={{ maxWidth: 375 }}>
                            <CardMedia
                                component="img"
                                height="200"
                                image="snorkelingPic.jpg"
                                alt="Bar & Grille"
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    Adventure
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Sip, Savor, and Soak in the Views
                                </Typography>
                                <Box sx={{ display: 'flex', justifyContent: 'flex-end', marginTop: '1rem' }}>
                                    <Button size="small" color="primary" onClick={() => navigate('/experiences')}>
                                        Learn More
                                    </Button>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>
                </Grid>
            </Box>
        </Box>
    );
};

export default HomePage;
