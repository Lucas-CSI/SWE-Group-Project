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
    IconButton
} from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { login } from "../services/authService";
import './NavigationBar.css';

const NavigationBar = () => {
    const [loginOpen, setLoginOpen] = useState(false);
    const [signupOpen, setSignupOpen] = useState(false);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLoginOpen = () => setLoginOpen(true);
    const handleLoginClose = () => {
        setLoginOpen(false);
        setError('');
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
                handleLoginClose();
            }
        } catch (error) {
            setError('Login failed. Please check your credentials.');
        }
    };

    const handleAdminLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8080/adminLogin', { username, password });
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


    // New function to handle "Rooms & Suites" button click
    const handleRoomsClick = () => {
        // Perform any additional action here, e.g., logging, fetching data, or showing a message
        console.log("Navigating to Rooms & Suites page...");

        // Navigate to /rooms route
        navigate('/rooms');
    };

    return (
        <>
            <AppBar position="fixed" className="app-bar">
                <Toolbar>
                    <IconButton edge="start" color="inherit" aria-label="logo" component={Link} to="/">
                        <img src="/SeaSideEscapeLogo.webp" alt="SeaSideEscape Hotel" style={{ height: '40px', marginRight: '10px' }} />
                    </IconButton>
                    <Typography variant="h6" className="header-title" onClick={() => navigate('/')}>
                        SeaSideEscape Hotel
                    </Typography>
                    <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center' }}>
                        <Link to="/" className="nav-link">
                            Home
                            <span className="underline"></span>
                        </Link>
                        <Link to="/rooms" className="nav-link">
                            Rooms & Suites
                            <span className="underline"></span>
                        </Link>
                        <Link to="/reservation" className="nav-link">
                            Make a Reservation
                            <span className="underline"></span>
                        </Link>
                        <Link to="/events" className="nav-link">
                            Events
                            <span className="underline"></span>
                        </Link>
                    </Box>
                    <Button color="inherit" onClick={handleLoginOpen} style={{ fontWeight: 'bold' }}>
                        Login
                    </Button>
                </Toolbar>
            </AppBar>

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

            {/* Login Dialog */}
            <Dialog open={loginOpen} onClose={handleLoginClose}>
                <DialogTitle>Login</DialogTitle>
                <DialogContent>
                    <DialogContentText>Please enter your login credentials.</DialogContentText>
                    {error && <p style={{ color: 'red' }}>{error}</p>}
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Username"
                        type="text"
                        fullWidth
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <TextField
                        margin="dense"
                        label="Password"
                        type="password"
                        fullWidth
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
                    <Button onClick={handleAdminLogin} color="secondary">
                        Admin Login
                    </Button>
                </DialogActions>
            </Dialog>
            {/*Signup Dialog*/}
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
