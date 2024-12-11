import React, {useState} from 'react';
import {
    Typography,
    Button,
    Box,
    Grid,
    Card,
    CardContent,
    CardMedia,
    DialogTitle,
    DialogContent,
    DialogContentText, Divider, Dialog, TextField
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';
import { getLoginStatus} from "../services/authService";

const apiService = require("../services/apiService.js"); // Fixed CSS import path
const themes = ['Nature Retreat', 'Urban Elegance', 'Vintage Charm'];
const qualityLevels = ['Economy', 'Standard', 'Premium', 'Luxury'];
const themeMap = {};
const qualityMap = {};


const HomePage = () => {
    const navigate = useNavigate();
    const [openDates, setOpenDates] = useState(false);
    const handleCloseDates = () => setOpenDates(false);
    const handleOpenDates = () => setOpenDates(true);
    const [reservationError, setReservationError] = useState('');


    for(let i = 0; i < themes.length; ++i){
        themeMap[themes[i]] = i;
        qualityMap[qualityLevels[i]] = i;
    }

    const events = [
        { id: 1, name: "Nature Retreat", description: "Celebrate your special day with us.", imageUrl: "WeddingReception.jpg" },
        { id: 2, name: "Urban Elegance", description: "Professional and elegant spaces for your business needs.", imageUrl: "CorporateMeeting.jpg" },
        { id: 3, name: "Vintage Charm", description: "Host a grand dinner for your guests in our luxurious venue.", imageUrl: "GalaDinner.jpg" }
    ];

    let [reservation, setReservation] = useState({
        checkInDate: "",
        checkOutDate: ""
    });
    let reservationData = new FormData();

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        console.log(name);
        console.log(value);
        reservationData.set(name, value);
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(reservation);
        if(getLoginStatus()) {
            try {
                const response = await apiService.generatePostRequest("reservation/new", reservationData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                );
                if (response.status === 200)
                    navigate(`/rooms`);
                else {
                    setReservationError("Error: " + response.response.data);
                }
            } catch (response) {
                console.log(response);
                setReservationError("Error: Check-in and/or check-out date not set");
            }
        }else{
            setReservationError("Error: You must be logged in to create a reservation.")
        }
    };
    return (
        <Box sx={{margin: 0, padding: 0, width: '100%', overflowX: 'hidden'}}>
            {/* Background Section */}
            <Box className="background-video-container">
                {/* Background Video */}
                <video autoPlay loop muted className="background-video">
                    <source src="seaWaveHome.mp4" type="video/mp4"/>
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
            <div style={{display: "flex", flexDirection: "row"}}>
                <Box className="check-in-out-container hero-text">
                    {reservationError && <p style={{ color: 'red', fontSize: '10px', font:'initial'}}>{reservationError}</p>}
                    <TextField
                        label="Check-in Date"
                        type="date"
                        name="checkInDate"
                        value={reservation.startDate}
                        onChange={handleChange}
                        fullWidth
                        margin="normal"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                    <TextField
                        label="Check-out Date"
                        type="date"
                        name="checkOutDate"
                        value={reservation.endDate}
                        onChange={handleChange}
                        fullWidth
                        margin="normal"
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                    <Button variant="contained" className="check-in-out-button" style={{width: '600px'}} onClick={handleSubmit}>
                        Search for room(s)
                    </Button>
                </Box>
            </div>

                {/* Vacation Preview Section */}
                <Box sx={{padding: '2rem'}}>
                    {/* Section Header */}
                    <Box sx={{display: 'flex', alignItems: 'center', marginBottom: '1rem'}}>
                        <Typography variant="h4" sx={{textAlign: 'left', marginRight: '1rem'}}>
                            Experiences
                        </Typography>
                        <Box sx={{flexGrow: 1, height: '1px', backgroundColor: 'grey.500'}}/>
                    </Box>

                    {/* Experiences Grid */}
                    <Grid container spacing={4} justifyContent="center" alignItems="center">
                        {/* Spa */}
                        <Grid item xs={12} sm={6} md={4} display="flex" justifyContent="center">
                            <Card sx={{maxWidth: 500}}>
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
                                    <Box sx={{display: 'flex', justifyContent: 'flex-end', marginTop: '1rem'}}>
                                        <Button size="small" color="primary" onClick={() => navigate('/experiences')}>
                                            Learn More
                                        </Button>
                                    </Box>
                                </CardContent>
                            </Card>
                        </Grid>

                        {/* Snorkeling */}
                        <Grid item xs={12} sm={6} md={4} display="flex" justifyContent="center">
                            <Card sx={{maxWidth: 500}}>
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
                                    <Box sx={{display: 'flex', justifyContent: 'flex-end', marginTop: '1rem'}}>
                                        <Button size="small" color="primary" onClick={() => navigate('/experiences')}>
                                            Learn More
                                        </Button>
                                    </Box>
                                </CardContent>
                            </Card>
                        </Grid>

                        {/* Bar & Grille */}
                        <Grid item xs={12} sm={6} md={4} display="flex" justifyContent="center">
                            <Card sx={{maxWidth: 375}}>
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
                                    <Box sx={{display: 'flex', justifyContent: 'flex-end', marginTop: '1rem'}}>
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
