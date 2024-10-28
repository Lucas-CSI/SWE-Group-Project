import React, { useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    TextField,
} from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {login} from "../services/authService"; // Import Axios for the admin login call

const NavigationBar = () => {
    const [loginOpen, setLoginOpen] = useState(false);
    const [signupOpen, setSignupOpen] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate(); // Use useNavigate to redirect

    const handleLoginOpen = () => {
        setLoginOpen(true);
    };

    const handleLoginClose = () => {
        setLoginOpen(false);
        setError(''); // Clear error when closing the dialog
    };

    const handleSignupOpen = () => {
        setSignupOpen(true);
        setLoginOpen(false);
    };

    const handleSignupClose = () => {
        setSignupOpen(false);
    };

    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        try {
            const data = await login(username, password);
            if (data) {
                localStorage.setItem('token', data.token);
                console.log('Login successful, token:', data.token);
                handleLoginClose();
            }
        } catch (error) {
            setError('Login failed. Please check your credentials.');
        }
    };

    const handleAdminLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8080/adminLogin', {
                username,
                password,
            });

            if (response.status === 200) {
                localStorage.setItem('admin', 'true');
                navigate('/admin/homepage');
            } else {
                setError('Admin login failed. Please check your credentials.');
            }
        } catch (error) {
            setError('Admin login failed. Please check your credentials.');
        } finally {
            handleLoginClose();
        }
    };

    return (
        <>
            <AppBar position="fixed" sx={{ backgroundColor: 'rgba(13,109,131,0.65)' }}>
                <Toolbar>
                    <Typography variant="h6" sx={{ flexGrow: 1 }}>
                        SeaSideEscape Hotel
                    </Typography>
                    <Box>
                        <Button color="inherit" component={Link} to={'/'}>
                            Home
                        </Button>
                        <Button color="inherit" component={Link} to="/rooms">
                            Rooms & Suites
                        </Button>
                        <Button color="inherit" component={Link} to="/reservation">
                            Make a Reservation
                        </Button>
                        <Button color="inherit" component={Link} to="/events">
                            Events
                        </Button>
                        <Button color="inherit" onClick={handleLoginOpen}>
                            Login
                        </Button>
                    </Box>
                </Toolbar>
            </AppBar>

            {/* Login Dialog */}
            <Dialog open={loginOpen} onClose={handleLoginClose}>
                <DialogTitle>Login</DialogTitle>
                <DialogContent>
                    <DialogContentText>Please enter your login credentials.</DialogContentText>
                    {error && <p style={{ color: 'red' }}>{error}</p>} {/* Show error if login fails */}
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Username"
                        type="text"
                        fullWidth
                        variant="standard"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <TextField
                        margin="dense"
                        label="Password"
                        type="password"
                        fullWidth
                        variant="standard"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleLoginClose}>Cancel</Button>
                    <Button onClick={handleLoginSubmit}>Login</Button>
                </DialogActions>
                <DialogActions>
                    <Button onClick={handleSignupOpen} color="secondary">
                        Create Account
                    </Button>
                    {/* New Admin Login button */}
                    <Button onClick={handleAdminLogin} color="secondary">
                        Admin Login
                    </Button>
                </DialogActions>
            </Dialog>

            {/* Signup Dialog */}
            <Dialog open={signupOpen} onClose={handleSignupClose}>
                <DialogTitle>Create Account</DialogTitle>
                <DialogContent>
                    <DialogContentText>Please enter your account details to sign up.</DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Username"
                        type="text"
                        fullWidth
                        variant="standard"
                    />
                    <TextField margin="dense" label="Email" type="email" fullWidth variant="standard" />
                    <TextField margin="dense" label="Password" type="password" fullWidth variant="standard" />
                    <TextField
                        margin="dense"
                        label="Confirm Password"
                        type="password"
                        fullWidth
                        variant="standard"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleSignupClose}>Cancel</Button>
                    <Button onClick={handleSignupClose}>Create Account</Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default NavigationBar;
