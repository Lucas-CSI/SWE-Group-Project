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
import { TextField } from '@mui/material';

import {
    generateRoomData,
    handleSubmitRoom,
    checkPreferenceAvailability,
    findFirstAvailableRoom,
    qualityLevelsIndex
} from './RoomModule'

const theme = "NATURE_RETREAT";


const RoomOption = ({ title }) => {
    let rooms = localStorage.getItem("rooms");
    rooms = JSON.parse(rooms);
    const qualityLevel = title.substring(0,title.indexOf(" "));
    let roomType = rooms[theme][qualityLevel];
    let isRoomAvailable = roomType.total > 0;
    const firstAvailableRoom = findFirstAvailableRoom(roomType);
    const [open, setOpen] = useState(false);
    const [smokingAllowed, setSmokingAllowed] = useState(firstAvailableRoom ? firstAvailableRoom[1] : false);
    const [oceanView, setOceanView] = useState(firstAvailableRoom ? firstAvailableRoom[0] : false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const [specialRequest, setSpecialRequest] = useState('');

    const handleOceanView =  () => {
        if(checkPreferenceAvailability(roomType, !oceanView, smokingAllowed)){
            setOceanView(!oceanView);
        }
    }

    const handleSmokingAllowed = () => {
        if(checkPreferenceAvailability(roomType, oceanView, !smokingAllowed)){
            setSmokingAllowed(!smokingAllowed);
        }
    }

    const handleSubmitRequest = () => {
        const existingRequests = JSON.parse(localStorage.getItem("specialRequests")) || [];
        existingRequests.push({
            title,
            specialRequest,
        });
        localStorage.setItem("specialRequests", JSON.stringify(existingRequests));
        setOpen(false); 
        setSpecialRequest('');
    };

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
                                        checked={smokingAllowed}
                                        onChange={handleSmokingAllowed}
                                    />
                                }
                                label="Smoking Allowed"
                                disabled={!checkPreferenceAvailability(roomType, oceanView, !smokingAllowed)}
                            />
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        name="oceanView"
                                        checked={oceanView}
                                        onChange={handleOceanView}
                                    />
                                }
                                label="Ocean View"
                                disabled={!checkPreferenceAvailability(roomType, !oceanView, smokingAllowed)}
                            />
                        </Box>

                        {/* Special Requests Text Box */}
                        <Box sx={{ marginTop: '1rem' }}>
                            <Typography variant="body1" sx={{ marginBottom: '0.5rem' }}>
                                Special Requests:
                            </Typography>
                            <TextField
                                fullWidth
                                multiline
                                rows={3}
                                placeholder="Enter any special requests or additional information..."
                                variant="outlined"
                                value={specialRequest}
                                onChange={(e) => setSpecialRequest(e.target.value)}

                            />
                        </Box>

                        <Divider sx={{ margin: '1rem 0' }} />
                        {!isRoomAvailable ? null : <Button
                            component={Link}
                            onClick={() => handleSubmitRoom(generateRoomData(theme, qualityLevelsIndex[qualityLevel], oceanView, smokingAllowed))}
                            variant="contained"
                            color="primary"
                            sx={{position: 'absolute', bottom: 10, right: 10}}
                        >
                            Reserve
                        </Button> }
                        
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
