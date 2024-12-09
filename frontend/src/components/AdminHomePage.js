import React from 'react';
import { Button, Box, Typography } from '@mui/material';

const AdminHomepage = () => {
    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                height: '100vh',
                backgroundImage: `url("/AdminPortWave.jpeg")`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat',
                backgroundAttachment: 'fixed',
                padding: 2,
            }}
        >
            <Typography variant="h4" sx={{ marginBottom: 4, color: 'white'}}>
                Admin Dashboard
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
                Create Clerk Account
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
                Maintenance
            </Button>
        </Box>
    );
};

export default AdminHomepage;
