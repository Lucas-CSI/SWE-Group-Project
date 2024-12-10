import React, { useState } from 'react';
import { Button, Box, Typography, TextField, Dialog, DialogTitle, DialogContent, MenuItem, Select, FormControl, InputLabel } from '@mui/material';

const ClerkHomepage = () => {
    const [email, setEmail] = useState('');
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [reservationAction, setReservationAction] = useState('');

    const handleSearch = () => {
        console.log(`Searching for: ${email}`);
    };

    const handleDialogOpen = () => {
        setIsDialogOpen(true);
    };

    const handleDialogClose = () => {
        setIsDialogOpen(false);
    };

    const handleReservationActionChange = (event) => {
        setReservationAction(event.target.value);
    };

    return (
        <Box
            sx={{
                position: 'relative',
                height: '100vh',
                backgroundImage: `url("/ClerkWave.jpg")`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat',
                backgroundAttachment: 'fixed',
                padding: 2,
            }}
        >
            {/* Email Search Prompt */}
            <Box
                sx={{
                    position: 'absolute',
                    top: '100px',
                    right: '20px',
                    display: 'flex',
                    alignItems: 'center',
                    gap: '10px',
                    backgroundColor: 'rgba(255, 255, 255, 0.9)',
                    padding: '10px',
                    borderRadius: '8px',
                    boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                }}
            >
                <TextField
                    label="Look up with Email"
                    variant="outlined"
                    size="small"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                    }}
                    onClick={handleSearch}
                >
                    Search
                </Button>
            </Box>

            {/* Admin Dashboard Content */}
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    height: '100%',
                }}
            >
                <Typography variant="h4" sx={{ marginBottom: 4, color: 'white' }}>
                    Clerk Dashboard
                </Typography>

                <Box sx={{ display: 'flex', gap: 2, marginBottom: 4 }}>
                    <Button
                        variant="contained"
                        sx={{
                            backgroundColor: 'rgb(25,122,140)',
                            color: 'white',
                            width: 200,
                            height: 60,
                            '&:hover': {
                                backgroundColor: '#28c1d8',
                            },
                        }}
                    >
                        Check In
                    </Button>
                    <Button
                        variant="contained"
                        sx={{
                            backgroundColor: 'rgb(25,122,140)',
                            color: 'white',
                            width: 200,
                            height: 60,
                            '&:hover': {
                                backgroundColor: '#28c1d8',
                            },
                        }}
                    >
                        Check Out
                    </Button>
                </Box>

                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        width: '100%',
                        height: 60,
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                        marginBottom: 4,
                    }}
                >
                    Modify My Account
                </Button>

                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        width: '100%',
                        height: 60,
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                    }}
                >
                    Room Status
                </Button>

                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        width: '100%',
                        height: 60,
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                        marginTop: 4,
                    }}
                    onClick={handleDialogOpen}
                >
                    Reservation Help
                </Button>
            </Box>

            {/* Reservation Help Dialog */}
            <Dialog open={isDialogOpen} onClose={handleDialogClose}>
                <DialogTitle>Reservation Help</DialogTitle>
                <DialogContent>
                    <FormControl fullWidth sx={{ marginTop: 2 }}>
                        <InputLabel id="reservation-action-label">Select Action</InputLabel>
                        <Select
                            labelId="reservation-action-label"
                            value={reservationAction}
                            onChange={handleReservationActionChange}
                        >
                            <MenuItem value="make">Make Reservation</MenuItem>
                            <MenuItem value="cancel">Cancel Reservation</MenuItem>
                            <MenuItem value="modify">Modify Reservation</MenuItem>
                        </Select>
                    </FormControl>
                </DialogContent>
            </Dialog>
        </Box>
    );
};

export default ClerkHomepage;
