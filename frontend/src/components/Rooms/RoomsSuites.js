import React, { useState } from 'react';
import {
    Box,
    Typography,
    Card,
    CardContent,
    CardMedia,
    Grid,
    Divider,
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogContentText,
} from '@mui/material';
import Slider from 'react-slick';

import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

const natureImages = ['beachyBed.jpg', 'beachView.jpg', 'beachBed.jpg'];
const urbanImages = ['modernBeachBed.jpg', 'urbanpt2.jpeg', 'modernBed.jpg'];
const vintageImages = ['vintageBed.jpg', 'vintageView.jpg', 'vintageType.jpg'];

const RoomSuites = () => {
    return (
        <Box sx={{ padding: '2rem' }}>
            <Typography variant="h4" align="center" sx={{ marginBottom: '2rem' }}>
                Room Categories
            </Typography>
            <Grid container spacing={4} justifyContent="center">
                <Grid item xs={12} md={8}>
                    <RoomCategory
                        title="Nature Retreat"
                        description="Single, Double, and Family rooms available."
                        images={natureImages}
                        options={['Single Room', 'Double Room', 'Family Room']}
                    />
                </Grid>
                <Grid item xs={12} md={8}>
                    <RoomCategory
                        title="Urban Elegance"
                        description="Suite and Deluxe rooms available."
                        images={urbanImages}
                        options={['Suite Room', 'Deluxe Room']}
                    />
                </Grid>
                <Grid item xs={12} md={8}>
                    <RoomCategory
                        title="Vintage Charm"
                        description="Standard and Deluxe rooms available. Smoking and Non-smoking options."
                        images={vintageImages}
                        options={['Standard Room (Non-Smoking)', 'Standard Room (Smoking)', 'Deluxe Room']}
                    />
                </Grid>
            </Grid>
        </Box>
    );
};

const RoomCategory = ({ title, description, images, options }) => {
    const [open, setOpen] = useState(false); // State for dialog open/close

    const sliderSettings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: true,
    };

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    return (
        <Card sx={{ backgroundColor: '#f2f2f2', padding: '1rem' }}>
            {/* Image Carousel */}
            <CardMedia>
                <Slider {...sliderSettings}>
                    {images.map((image, index) => (
                        <Box
                            key={index}
                            component="img"
                            src={image}
                            alt={`${title} ${index + 1}`}
                            sx={{ width: '100%', height: '300px', objectFit: 'cover', borderRadius: '8px' }}
                        />
                    ))}
                </Slider>
            </CardMedia>

            {/* Text Content */}
            <CardContent>
                <Typography variant="h5" sx={{ marginBottom: '0.5rem' }}>
                    {title}
                </Typography>
                <Divider sx={{ marginBottom: '1rem' }} />
                <Typography variant="body1">{description}</Typography>

                {/* View Options Button */}
                <Button variant="outlined" color="primary" sx={{ marginTop: '1rem' }} onClick={handleOpen}>
                    View Options
                </Button>

                {/* Dialog for Viewing Options */}
                <Dialog open={open} onClose={handleClose}>
                    <DialogTitle>{title} - Room Options</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            Here are the available options for {title}:
                        </DialogContentText>
                        <ul>
                            {options.map((option, index) => (
                                <li key={index}>
                                    <Typography variant="body1">{option}</Typography>
                                </li>
                            ))}
                        </ul>
                    </DialogContent>
                    <Button onClick={handleClose} color="primary" sx={{ margin: '1rem' }}>
                        Close
                    </Button>
                </Dialog>
            </CardContent>
        </Card>
    );
};

export default RoomSuites;
