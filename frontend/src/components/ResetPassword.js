import React, { useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { Container, TextField, Button, Typography, Box } from '@mui/material';
import axios from 'axios';

const ResetPasswordPage = () => {
    const [searchParams] = useSearchParams(); // Extract the token from the URL
    const token = searchParams.get('token');

    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async () => {
        if (!newPassword || !confirmPassword) {
            setError('Please fill in all fields.');
            return;
        }

        if (newPassword !== confirmPassword) {
            setError('Passwords do not match.');
            return;
        }

        try {
            const response = await axios.post('/password/reset', null, {
                params: { token, newPassword },
            });
            setSuccess(response.data); // Show success message
            setError('');
        } catch (err) {
            console.error(err);
            setError(err.response?.data || 'Failed to reset password.');
        }
    };

    return (
        <Container maxWidth="sm" sx={{ marginTop: '4rem' }}>
            <Box
                sx={{
                    padding: '2rem',
                    backgroundColor: 'rgba(255, 255, 255, 0.9)',
                    borderRadius: '12px',
                    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
                }}
            >
                <Typography variant="h4" align="center" gutterBottom>
                    Reset Your Password
                </Typography>
                {error && <Typography color="error" align="center">{error}</Typography>}
                {success && <Typography color="primary" align="center">{success}</Typography>}
                <TextField
                    label="New Password"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                />
                <TextField
                    label="Confirm Password"
                    type="password"
                    fullWidth
                    margin="normal"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    onClick={handleSubmit}
                    sx={{ marginTop: '1rem' }}
                >
                    Reset Password
                </Button>
            </Box>
        </Container>
    );
};

export default ResetPasswordPage;