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
    Dialog,
    DialogTitle,
    DialogContent, DialogContentText, FormControlLabel, Checkbox
} from '@mui/material';
import {generateRoomData, handleSubmitRoom} from './RoomModule'

const theme = "NATURE_RETREAT";


const RoomOption = ({ title }) => {
    let rooms = localStorage.getItem("rooms");
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    rooms = JSON.parse(rooms);
    let isRoomAvailable = rooms[theme][title.substring(0,title.indexOf(" "))].total > 0;
    return (
        <Card sx={{backgroundColor: '#f2f2f2', padding: '1rem', position: 'relative', height: '100%', opacity: isRoomAvailable ? 1 : 0.5}}>
            {/* Placeholder Image */}
            <CardMedia>
                <Box
                    component="img"
                    src="singleNature.webp"
                    alt={title}
                    sx={{width: '100%', height: '150px', objectFit: 'cover', borderRadius: '8px'}}
                />
            </CardMedia>

            <CardContent sx={{display: 'flex', flexDirection: 'column', alignItems: 'flex-start'}}>
                <Typography variant="h6" sx={{marginBottom: '0.5rem'}}>
                    {title}
                </Typography>
                <Typography variant="body2" sx={{marginBottom: '1rem'}}>
                    {isRoomAvailable ? "Available" : "Not available."}
                </Typography>

                {/* View Rates & Reserve */}

                {/* View Options Button */}
                {!isRoomAvailable ? null : <Button variant="contained" color="primary" sx={{ position: 'absolute', bottom: 10, right: 10 }} onClick={handleOpen}>
                    View Options & Reserve
                </Button>}
                {/* Dialog for Viewing Options */}
                <Dialog open={open} onClose={handleClose}>
                    <DialogTitle>{title} - Room Options</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            Here are the available options for {title}:
                        </DialogContentText>
                        <Divider sx={{ margin: '1rem 0' }} />
                        <Box>
                            <Typography variant="body1" sx={{ marginBottom: '0.5rem' }}>
                                Preferences:
                            </Typography>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        name="isSmokingAllowed"
                                        checked={false}
                                    />
                                }
                                label="Smoking Allowed"
                            />
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        name="oceanView"
                                        checked={false}
                                    />
                                }
                                label="Ocean View"
                            />
                        </Box>
                        <Divider sx={{ margin: '1rem 0' }} />
                        {!isRoomAvailable ? null : title === "Suite Style" ? <Button
                            component={Link}
                            onClick={() => handleSubmitRoom(generateRoomData(theme, 2))}
                            variant="contained"
                            color="primary"
                            sx={{position: 'absolute', top: 10, right: 10}}
                        >
                            Reserve
                        </Button> : <Button
                            component={Link}
                            onClick={() => handleSubmitRoom(generateRoomData(theme, 1))}
                            variant="contained"
                            color="primary"
                            sx={{position: 'absolute', bottom: 10, right: 10, }}
                        >
                            Reserve
                        </Button>}
                        <Button onClick={handleClose} variant="outlined" color="primary" sx={{ bottom: -10 , right: 10 }}>
                            Close
                        </Button>
                    </DialogContent>
                </Dialog>
            </CardContent>
        </Card>
    );
};

const NatureRetreatOptions = () => (
    <Box>
        <Box
            sx={{
                width: '100%',
                height: '400px',
                backgroundImage: `url('natureRetreatMain.jpg')`,
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
                Nature Retreat
            </Typography>
        </Box>

        <Box sx={{ padding: '2rem', paddingBottom: '4rem' }}>
            <Typography variant="h5" sx={{ marginBottom: '1rem' }}>
                Explore Our Rooms
            </Typography>
            <Divider sx={{ marginBottom: '1rem' }} />
            <Grid container spacing={2}>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Economy Room" />
                </Grid>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Comfort Room" />
                </Grid>
                <Grid item xs={12} md={4}>
                    <RoomOption title="Executive Room" />
                </Grid>
            </Grid>
        </Box>
    </Box>
);

export default NatureRetreatOptions;
