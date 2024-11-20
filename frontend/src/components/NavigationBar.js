import React, { useState } from 'react';
import Cookies from 'js-cookie';
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
    const [email, setEmail] = useState('');
    const [canCreateAccount, setCanCreateAccount] = useState(true);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const handleLoginOpen = () => setLoginOpen(true);
    const handleLoginClose = () => {
        setLoginOpen(false);
        setError('');
    };

    async function generateRequest(endpoint, params){
        let successful = true;
        try {
            await axios.post('http://localhost:8080/' + endpoint, params, {withCredentials: true});
        }catch (e) {
            setError("Error: " + e.response.data);
            successful = false
        }
        return successful;
    }

    const handleSignupOpen = () => {
        setSignupOpen(true);
        setLoginOpen(false);
    };

    const handleSignupClose = () => {
        setSignupOpen(false);
        setSuccess('');
        setError('');
    };

    const handleCreateAccount = async () => {
        if(canCreateAccount) {
            const response = generateRequest("createAccount", {username, password, email});
            if (response) {
                setError('');
                setSuccess('Account created successfully.');
                setTimeout(() => {
                    handleSignupClose();
                    handleLoginOpen();
                }, 1000);
            }
        }
    }


    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        const response = generateRequest("login", {username, password});
        if (response) {
            handleLoginClose();
            alert("Logged in.");
        }
    };

    const handleLogout = async () => {
        const response = generateRequest("logout", {});
        if (response) {
            navigate("/");
            alert("Logged in.");
        }
    }

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

    const handleRoomsClick = () => {
        console.log("Navigating to Rooms & Suites page...");
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
                        <Link to="/reservation" className="nav-link">
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
                    {!Cookies.get('username') || Cookies.get('username') === "" ? <Button color="inherit" onClick={handleLoginOpen} style={{ fontWeight: 'bold' }}>
                        Login
                    </Button> : <Button color="inherit" onClick={handleLogout} style={{ fontWeight: 'bold' }}>
                        Logout
                    </Button>}
                </Toolbar>
            </AppBar>

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
                </DialogActions>
            </Dialog>

            {/* Signup Dialog */}
            <Dialog open={signupOpen} onClose={handleSignupClose}>
                <DialogTitle>Create Account</DialogTitle>
                <DialogContent>
                    <DialogContentText>Please enter your account details to sign up.</DialogContentText>
                    {error && <p style={{ color: 'red' }}>{error}</p>}
                    {success && <p style={{ color: 'green' }}>{success}</p>}
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Username"
                        type="text"
                        fullWidth
                        variant="standard"
                        onChange={(e) => setUsername(e.target.value)}
                    />
                    <TextField margin="dense" label="Email" type="email" fullWidth variant="standard" onChange={(e) => { setEmail(e.target.value) }}/>
                    <TextField margin="dense" label="Password" type="password" fullWidth variant="standard" onChange={(e) => setPassword(e.target.value)}/>
                    <TextField
                        margin="dense"
                        label="Confirm Password"
                        type="password"
                        fullWidth
                        variant="standard"
                        onChange={(e) => {
                            if(e.target.value !== password) {
                                setError("Passwords do not match.");
                                setCanCreateAccount(false);
                            }else {
                                setError('');
                                setCanCreateAccount(true);
                            }
                        }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleSignupClose}>Cancel</Button>
                    <Button onClick={handleCreateAccount}>Create Account</Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default NavigationBar;
