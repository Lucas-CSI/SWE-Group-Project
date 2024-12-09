import React from 'react';
import {
    Box,
    Typography,
    Grid,
    Card,
    CardContent,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
} from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import PaymentIcon from '@mui/icons-material/Payment';
import EditIcon from '@mui/icons-material/Edit';
import LockResetIcon from '@mui/icons-material/LockReset';

const UserPortal = () => {
    return (
        <Box
            sx={{
                padding: '6rem',
                backgroundImage: 'url("waveUser.jpg")',
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat',
                minHeight: '100vh',
            }}
        >
            <Grid container spacing={4} alignItems="flex-start">
                {/* Welcome Banner */}
                <Grid item xs={12}>
                    <Typography
                        variant="h2"
                        sx={{
                            fontWeight: 'bold',
                            color: 'white',
                            textAlign: 'center',
                            marginBottom: '2rem',
                        }}
                    >
                        Welcome
                    </Typography>
                </Grid>

                {/* Main Content Section */}
                <Grid item xs={12} md={8}>
                    {/* Stacked Cards */}
                    <Box
                        sx={{
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                            gap: '1.5rem',
                        }}
                    >
                        {/* Past Bookings */}
                        <Card sx={{ width: '80%', height: '150px', backgroundColor: 'rgba(255, 255, 255, 0.9)' }}>
                            <CardContent>
                                <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                                    Past Bookings
                                </Typography>
                                <Typography variant="body2" sx={{ marginTop: '1rem' }}>
                                    View all your completed trips and past experiences.
                                </Typography>
                            </CardContent>
                        </Card>

                        {/* Current Bookings */}
                        <Card sx={{ width: '80%', height: '150px', backgroundColor: 'rgba(255, 255, 255, 0.9)' }}>
                            <CardContent>
                                <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                                    Current Bookings
                                </Typography>
                                <Typography variant="body2" sx={{ marginTop: '1rem' }}>
                                    Manage your ongoing trips and reservations here.
                                </Typography>
                            </CardContent>
                        </Card>

                        {/* Future Trips */}
                        <Card sx={{ width: '80%', height: '150px', backgroundColor: 'rgba(255, 255, 255, 0.9)' }}>
                            <CardContent>
                                <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                                    Future Trips
                                </Typography>
                                <Typography variant="body2" sx={{ marginTop: '1rem' }}>
                                    Plan your upcoming journeys and experiences.
                                </Typography>
                            </CardContent>
                        </Card>
                    </Box>
                </Grid>

                {/* My Account Section */}
                <Grid item xs={12} md={4}>
                    <Box
                        sx={{
                            backgroundColor: 'rgba(255, 255, 255, 0.9)',
                            padding: '2rem',
                            borderRadius: '8px',
                            boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
                        }}
                    >
                        <Box
                            sx={{
                                display: 'flex',
                                alignItems: 'center',
                                marginBottom: '1.5rem',
                            }}
                        >
                            <AccountCircleIcon
                                sx={{ fontSize: '40px', marginRight: '0.5rem', color: 'rgb(25,122,140)' }}
                            />
                            <Typography variant="h5" sx={{ fontWeight: 'bold', color: 'rgb(25,122,140)' }}>
                                My Account
                            </Typography>
                        </Box>

                        <List>
                            <ListItem button>
                                <ListItemIcon>
                                    <PaymentIcon sx={{ color: 'rgb(25,122,140)' }} />
                                </ListItemIcon>
                                <ListItemText primary="Payment Options" />
                            </ListItem>
                            <ListItem button>
                                <ListItemIcon>
                                    <EditIcon sx={{ color: 'rgb(25,122,140)' }} />
                                </ListItemIcon>
                                <ListItemText primary="Modify or Cancel Bookings" />
                            </ListItem>
                            <ListItem button>
                                <ListItemIcon>
                                    <LockResetIcon sx={{ color: 'rgb(25,122,140)' }} />
                                </ListItemIcon>
                                <ListItemText primary="Reset Password" />
                            </ListItem>
                        </List>
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
};

export default UserPortal;
