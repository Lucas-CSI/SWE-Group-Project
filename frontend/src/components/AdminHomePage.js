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
                //backgroundImage: `url("/seaView.jpg")`,
                // backgroundSize: 'cover',
                // backgroundPosition: 'center',
                // backgroundRepeat: 'no-repeat',
                //backgroundAttachment: 'fixed',
                backgroundColor: '#f5f5f5',
                padding: 2,
            }}
        >
            <Typography variant="h4" sx={{ marginBottom: 4 }}>
                Admin Dashboard
            </Typography>

            <Box sx={{ display: 'flex', gap: 2, marginBottom: 4 }}>
                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: '#5fbccc',
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
                        backgroundColor: '#5fbccc',
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
                    backgroundColor: '#5fbccc',
                    color: 'white',
                    width: '100%',
                    height: 60,
                    '&:hover': {
                        backgroundColor: '#28c1d8', // Darker teal on hover
                    },
                }}
            >
                Maintenance
            </Button>
        </Box>
    );
};

export default AdminHomepage;