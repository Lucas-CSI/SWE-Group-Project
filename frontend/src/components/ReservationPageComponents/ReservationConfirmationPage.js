// src/components/EventConfirmationPage.js
import React from 'react';
import {Box, Button, Dialog, DialogContent, DialogContentText, DialogTitle, Divider, Typography} from '@mui/material';
import {Link, useNavigate} from "react-router-dom";
import {generateRoomData, handleSubmitRoom, qualityLevelsIndex} from "../Rooms/RoomModule";

const EventConfirmationPage = () => {
    const navigate = useNavigate();
    return (
        <Box sx={{ padding: '20px', textAlign: 'center' }}>
            <Dialog open={true}>
                <DialogTitle>Room booking successful</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Would you like to book more rooms or go to checkout?
                    </DialogContentText>
                    <Button
                        variant="contained"
                        color="primary"
                        sx={{position: 'absolute', bottom: 10, right: 10}}
                        onClick={() => navigate("/reservation/payment")}
                    >
                        CHECKOUT
                    </Button>
                    <Button onClick={() => navigate("/rooms")} variant="outlined" color="primary" sx={{ bottom: -10, left: 140 }}>
                        BACK TO ROOMS
                    </Button>
                </DialogContent>
            </Dialog>
        </Box>
    );
};

export default EventConfirmationPage;
