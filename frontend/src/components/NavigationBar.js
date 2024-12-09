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
    IconButton,
    Popover,
    Badge,
    List,
    ListItem,
    ListItemText
} from '@mui/material';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { login } from "../services/authService.js";
import './NavigationBar.css';

const NavigationBar = () => {
    const [isPopoverHovered, setIsPopoverHovered] = useState(false);

    const [loginOpen, setLoginOpen] = useState(false);
    const [signupOpen, setSignupOpen] = useState(false);

    const [cartAnchorEl, setCartAnchorEl] = useState(null);

    const handleCartOpen = (event) => setCartAnchorEl(event.currentTarget);
    const handleCartClose = () => setCartAnchorEl(null);

    const isCartOpen = Boolean(cartAnchorEl);

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

    const handleRoomsClick = () => {
        console.log("Navigating to Rooms & Suites page...");
        navigate('/rooms');
    };

    const [cartItems, setCartItems] = useState([
        { name: 'Room Booking', price: 200 },
        { name: 'Spa Service', price: 50 },
    ]);

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
                        <Link to="/events" className="nav-link">
                            Events
                            <span className="underline"></span>
                        </Link>
                    </Box>
                    <IconButton
                        color="inherit"
                        aria-label="cart"
                        onClick={(event) => setCartAnchorEl(cartAnchorEl ? null : event.currentTarget)}
                    >
                        <Badge badgeContent={cartItems.length} color="secondary">
                            <ShoppingCartIcon />
                        </Badge>
                    </IconButton>

                    <Button color="inherit" onClick={handleLoginOpen} style={{ fontWeight: 'bold' }}>
                        Login
                    </Button>
                </Toolbar>
                <Popover
                    open={isCartOpen}
                    anchorEl={cartAnchorEl}
                    onClose={handleCartClose}
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'right',
                    }}
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                    PaperProps={{
                        style: {
                            padding: '10px',
                            width: '250px',
                            position: 'relative',
                        },
                    }}
                >
                    <Typography
                        variant="h6"
                        gutterBottom
                        sx={{
                            textAlign: 'center',
                            marginBottom: '10px',
                            fontWeight: 'bold',
                        }}
                    >
                        Cart Summary
                    </Typography>
                    <Box
                        sx={{
                            border: '2px solid rgb(25,122,140)',
                            borderRadius: '8px',
                            padding: '8px',
                            marginBottom: '60px',
                            position: 'relative',
                        }}
                    >
                        <List>
                            {cartItems.map((item, index) => (
                                <ListItem key={index}>
                                    <ListItemText primary={item.name} secondary={`$${item.price}`} />
                                </ListItem>
                            ))}
                        </List>
                        <Typography
                            variant="body1"
                            sx={{
                                marginTop: '10px',
                                fontWeight: 'bold',
                                textAlign: 'center',
                            }}
                        >
                            Total: ${cartItems.reduce((total, item) => total + item.price, 0)}
                        </Typography>
                    </Box>
                    <Button
                        variant="contained"
                        sx={{
                            backgroundColor: 'rgb(25,122,140)',
                            color: 'white',
                            position: 'absolute',
                            bottom: '10px',
                            right: '10px',
                            '&:hover': {
                                backgroundColor: '#28c1d8',
                            },
                        }}
                        onClick={() => navigate('/checkout')}
                    >
                        Checkout
                    </Button>
                </Popover>
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