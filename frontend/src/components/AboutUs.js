import React from 'react';
import { Box, Typography, Link, Grid } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';


const AboutUs = () => {
    return (
        <Box>
            {/* Main Picture with Overview */}
            <Box
                sx={{
                    position: 'relative',
                    height: '500px',
                    backgroundImage: `url('/beachOverview.jpg')`,
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                }}
            >
                <Typography
                    variant="h2"
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        color: 'white',
                        fontWeight: 'bold',
                        textShadow: '2px 2px 4px rgba(25,122,140)',
                    }}
                >
                    Overview
                </Typography>
            </Box>

            {/* Description section */}
            <Box sx={{ display: 'flex', padding: 4 }}>
                <Box sx={{ flex: 2, marginRight: 10 }}>
                    <Typography variant="h4" sx={{ marginBottom: 2 }}>
                        A Blend of Relaxation & Comfort
                    </Typography>
                    <Typography sx={{ marginBottom: 2 }}>
                        Nestled atop a scenic coastal bluff, this exclusive retreat offers an expansive setting that blends
                        refined elegance with timeless charm. Each thoughtfully designed accommodation showcases breathtaking
                        vistas and architectural artistry, complemented by curated touches that inspire a sense of sophistication.
                        Guests are invited to indulge in a variety of bespoke experiences, from rejuvenating wellness treatments
                        and exceptional dining to tranquil outdoor escapes. Every detail has been crafted to evoke an atmosphere of
                        effortless luxury and unparalleled serenity.
                    </Typography>
                </Box>
                <Box sx={{ flex: 1, borderLeft: '1px solid #ddd', paddingLeft: 4 }}>
                    <Typography variant="h6" sx={{ marginBottom: 2 }}>
                        Additional Information
                    </Typography>
                    <Box>
                        <Link
                            component={RouterLink}
                            to="/special-offers"
                            sx={{ display: 'block', marginBottom: 1 }}
                        >
                            Special Offers
                        </Link>
                        <Link
                            component={RouterLink}
                            to="/rooms"
                            sx={{ display: 'block', marginBottom: 1 }}
                        >
                            Rooms
                        </Link>
                        <Link
                            component={RouterLink}
                            to="/experiences"
                            sx={{ display: 'block', marginBottom: 1 }}
                        >
                            Experiences
                        </Link>
                        <Link
                            component={RouterLink}
                            to="/faqs"
                            sx={{ display: 'block', marginBottom: 1 }}
                        >
                            FAQs
                        </Link>
                    </Box>
                </Box>
            </Box>

            {/* At a Glance Section */}
            <Box sx={{ padding: 4, backgroundColor: '#f9f9f9' }}>
                <Typography variant="h4" sx={{ marginBottom: 1 }}>
                    At a Glance
                </Typography>
                <Grid container spacing={4}>
                    <Grid item xs={12} md={6}>
                        <Typography component="ul">
                            <li>100 guestrooms, including 20 suites</li>
                            <li>Signature restaurants and lounge</li>
                            <li>20,000 sq ft oceanfront spa</li>
                            <li>Over 2,500 sq ft of event space</li>
                        </Typography>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Box
                            sx={{
                                height: '300px',
                                backgroundImage: `url('/beachyPic.webp')`,
                                backgroundSize: 'cover',
                                backgroundPosition: 'center',
                                borderRadius: '8px',
                            }}
                        />
                    </Grid>
                </Grid>
            </Box>
        </Box>
    );
};

export default AboutUs;
