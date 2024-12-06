import React from 'react';
import { AppBar, Toolbar, Typography, Button, Box, Grid, Card, CardContent, CardMedia } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
    const navigate = useNavigate();

    return (
        <Box>
            {/* Main Background Section */}
            <Box
                sx={{
                    minHeight: '100vh',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    backgroundImage: `url("/OceanViewHomePage.jpg")`,
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                    backgroundRepeat: 'no-repeat',
                    backgroundAttachment: 'fixed',
                    backgroundColor: 'rgba(0, 0, 0, 0.5)',
                }}
            >
                <Typography variant="h2" color="white" align="center" >
                    Welcome to SeaSide Escape!
                </Typography>
            </Box>

            {/* Vacation Preview */}
            <Box sx={{ padding: '2rem' }}>
                <Box sx={{ display: 'flex', alignItems: 'center', marginBottom: '1rem' }}>
                    <Typography variant="h4" sx={{ textAlign: 'left', marginRight: '1rem' }}>
                        Experiences
                    </Typography>
                    <Box sx={{ flexGrow: 1, height: '1px', backgroundColor: 'grey.500' }} />
                </Box>
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
