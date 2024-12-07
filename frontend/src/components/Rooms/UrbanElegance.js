import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import {
    Box,
    Typography,
    Divider,
    Grid,
    Card,
    CardContent,
    Button,
    CardMedia,
    Modal,
    FormControl,
    FormControlLabel,
    RadioGroup,
    Radio,
} from '@mui/material';

const RoomOption = ({ title }) => {
    const [open, setOpen] = useState(false);
    const [smokingPreference, setSmokingPreference] = useState('non-smoking');

    const handleReserveClick = () => {
        setOpen(true);
    };

    const handleConfirm = () => {
        setOpen(false);
        const reservation = {
            room: {
                bedType: title === "Suite Style" ? "Suite" : "Deluxe",
                qualityLevel: title === "Suite Style" ? 2 : 1,
                smokingPreference,
            },
        };
        console.log("Reservation Details:", reservation);
        alert(`Reservation confirmed for ${title} with ${smokingPreference} preference.`);
    };

    return (
        <>
            <Card sx={{ backgroundColor: '#f2f2f2', padding: '1rem', position: 'relative', height: '100%' }}>
                {/* Placeholder Image */}
                <CardMedia>
                    <Box
                        component="img"
                        src="urbanSuite.jpg"
                        alt={title}
                        sx={{ width: '100%', height: '150px', objectFit: 'cover', borderRadius: '8px' }}
                    />
                </CardMedia>

                <CardContent sx={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                    <Typography variant="h6" sx={{ marginBottom: '0.5rem' }}>
                        {title}
                    </Typography>
                    <Typography variant="body2" sx={{ marginBottom: '1rem' }}>
                        Text
                    </Typography>

                    {/* Reserve Button */}
                    <Button
                        onClick={handleReserveClick}
                        variant="outlined"
                        color="primary"
                        sx={{ position: 'absolute', bottom: 10, right: 10 }}
                    >
                        View Rates & Reserve
                    </Button>
                </CardContent>
            </Card>

            {/* Smoking Preference Modal */}
            <Modal open={open} onClose={() => setOpen(false)}>
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        bgcolor: 'background.paper',
                        boxShadow: 24,
                        p: 4,
                        borderRadius: '8px',
                        width: '300px',
                    }}
                >
                    <Typography variant="h6" sx={{ mb: 2 }}>
                        Smoking Preference
                    </Typography>
                    <FormControl component="fieldset">
                        <RadioGroup
                            value={smokingPreference}
                            onChange={(e) => setSmokingPreference(e.target.value)}
                        >
                            <FormControlLabel value="smoking" control={<Radio />} label="Smoking" />
                            <FormControlLabel value="non-smoking" control={<Radio />} label="Non-Smoking" />
                        </RadioGroup>
                    </FormControl>
                    <Box sx={{ mt: 2, display: 'flex', justifyContent: 'flex-end' }}>
                        <Button
                            onClick={handleConfirm}
                            variant="contained"
                            color="primary"
                        >
                            Confirm
                        </Button>
                    </Box>
                </Box>
            </Modal>
        </>
    );
};

const UrbanElegance = () => (
    <Box>
        {/* Top */}
        <Box
            sx={{
                width: '100%',
                height: '400px',
                backgroundImage: `url('TBD.jpg')`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                color: 'white',
                textAlign: 'center',
            }}
        >
            <Typography variant="h2" sx={{ backgroundColor: 'rgba(0, 0, 0, 0.2)', padding: '1rem' }}>
                Urban Elegance
            </Typography>
        </Box>

        {/* Room Options */}
        <Box sx={{ padding: '2rem', paddingBottom: '4rem' }}>
            <Typography variant="h5" sx={{ marginBottom: '1.5rem' }}>
                Explore Our Rooms
            </Typography>
            <Divider sx={{ marginBottom: '1.5rem' }} />
            <Grid container spacing={4} justifyContent="center">
                <Grid item xs={12} md={4}>
                    <RoomOption title="Suite Style" />
                </Grid>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Deluxe Style" />
                </Grid>
            </Grid>
        </Box>
    </Box>
);

export default UrbanElegance;
