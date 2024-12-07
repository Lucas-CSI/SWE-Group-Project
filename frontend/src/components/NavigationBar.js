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
import { generatePostRequest } from "../services/apiService"

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
            const response = await generatePostRequest("createAccount", {username, password, email});
            if (response.status === 200) {
                setError('');
                setSuccess('Account created successfully.');
                setTimeout(() => {
                    handleSignupClose();
                    handleLoginOpen();
                }, 1000);
            }else{
                setError("Error: " + response.data);
            }
        }
    }


    const handleLoginSubmit = async (e) => {
        e.preventDefault();
        const response = await generatePostRequest("login", {username, password});
        if (response.status === 200) {
            handleLoginClose();
            alert("Logged in.");
        }else{
            setError("Error: " + response.response.data);
        }
    };

    const handleLogout = async () => {
        const response = await generatePostRequest("logoutAccount", {});
        if (response.status === 200) {
            navigate("/");
            alert("Logged out.");
        }else{
            setError("Error: " + response.response.data);
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
                        <Link to="/reservation" className="nav-link">
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

                    {!Cookies.get('username') || Cookies.get('username') === "" ? <Button color="inherit" onClick={handleLoginOpen} style={{ fontWeight: 'bold' }}>
                        Login
                    </Button> : <Button color="inherit" onClick={handleLogout} style={{ fontWeight: 'bold' }}>
                        Logout
                    </Button>}
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
                        style: { padding: '10px', width: '250px' },
                    }}
                >
                    <Typography variant="h6" gutterBottom>
                        Cart Summary
                    </Typography>
                    <List>
                        {cartItems.map((item, index) => (
                            <ListItem key={index}>
                                <ListItemText primary={item.name} secondary={`$${item.price}`} />
                            </ListItem>
                        ))}
                    </List>
                    <Typography variant="body1" style={{ marginTop: '10px', fontWeight: 'bold' }}>
                        Total: ${cartItems.reduce((total, item) => total + item.price, 0)}
                    </Typography>
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