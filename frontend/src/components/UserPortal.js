import React, {useEffect, useState} from 'react';
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
    Button,
    Modal,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
} from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import PaymentIcon from '@mui/icons-material/Payment';
import EditIcon from '@mui/icons-material/Edit';
import LockResetIcon from '@mui/icons-material/LockReset';
import {generateGetRequest, generatePostRequest} from "../services/apiService";

const UserPortal = () => {
    const [open, setOpen] = useState(false);
    const [action, setAction] = useState('');
    const [reservations, setReservations] = useState('');

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const handleActionChange = (event) => setAction(event.target.value);


    useEffect(() => {
        const getReservations = async() => {
            const response = await generateGetRequest("/profile/reservations");
            let string = "";
            console.log(response);
            if(response.status === 200){
                for(let i in response.data){
                    console.log(i);
                    string += "Reservation " + i;
                    string += "\n Check-In Date: " + response.data[i].checkInDate + " Check-Out Date: " + response.data[i].checkOutDate;
                    string += "\n" + JSON.stringify(response.data[i].bookings, null, 4) + "\n";
                }
                console.log(string);
                setReservations(string);
            }
        }
       getReservations();
    });

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
                        {/* Current Bookings */}
                        <Card sx={{ width: '80%', height: '50%', backgroundColor: 'rgba(255, 255, 255, 0.9)', overflowY: 'scroll', overflowX: 'scroll', whiteSpace: 'pre-line' }}>
                            <CardContent>
                                <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                                    Current Bookings
                                </Typography>
                                <Typography variant="body2" sx={{ marginTop: '1rem'}}>
                                    Manage your ongoing trips and reservations here.
                                </Typography>
                                {reservations}
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
                            <ListItem button onClick={handleOpen}>
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

            {/* Modal for Modify or Cancel */}
            <Modal open={open} onClose={handleClose}>
                <Box
                    sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        bgcolor: 'background.paper',
                        boxShadow: 24,
                        p: 4,
                        borderRadius: 2,
                    }}
                >
                    <Typography variant="h6" component="h2"  sx={{ mb: 2 }}>
                        Change Reservation
                    </Typography>
                    <FormControl fullWidth>
                        <InputLabel id="action-select-label">Action</InputLabel>
                        <Select
                            labelId="action-select-label"
                            value={action}
                            onChange={handleActionChange}
                        >
                            <MenuItem value="modify">Modify</MenuItem>
                            <MenuItem value="cancel">Cancel</MenuItem>
                        </Select>
                    </FormControl>
                    <Box sx={{ mt: 3, display: 'flex', justifyContent: 'space-between' }}>
                        <Button
                            variant="contained"
                            onClick={handleClose}
                            sx={{
                                backgroundColor: 'rgb(25,122,140)',
                                '&:hover': {
                                    backgroundColor: '#28c1d8',
                                },
                            }}
                        >
                            Confirm
                        </Button>
                        <Button variant="outlined" onClick={handleClose}
                                sx={{
                                    color: 'rgb(25,122,140)',
                                    borderColor: 'rgb(25,122,140)',
                                    '&:hover': {
                                        color: 'darkslategray',
                                        borderColor: 'darkslategray',
                                    },
                                }}
                        >
                            Close
                        </Button>
                    </Box>
                </Box>
            </Modal>
        </Box>
    );
};

export default UserPortal;
