import React, { useState } from 'react';
import { Button, Box, Typography, TextField } from '@mui/material';

const AdminHomepage = () => {
    const [email, setEmail] = useState('');

    const handleSearch = () => {
        console.log(`Searching for: ${email}`);
    };

    return (
        <Box
            sx={{
                position: 'relative',
                height: '100vh',
                backgroundImage: `url("/AdminPortWave.jpeg")`,
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
        </Box>
    );
};

export default AdminHomepage;
