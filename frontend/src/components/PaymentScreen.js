import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import {
    Container,
    TextField,
    Typography,
    Box,
    Grid,
    Button,
    Divider,
    Paper,
    Card,
    CardContent,
} from "@mui/material";

const TAX_RATE = 0.1;

const PaymentScreen = () => {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        address: "",
        city: "",
        state: "",
        zip: "",
        creditCardNumber: "",
        expirationMonth: "",
        expirationYear: "",
        cvv: "",
    });

    const [cartSubtotal, setCartSubtotal] = useState(0);
    const [tax, setTax] = useState(0);
    const [total, setTotal] = useState(0);
    const [cartDetails, setCartDetails] = useState([]);
    const navigate = useNavigate();

    const fetchCartSubtotal = async () => {
        try {
            const response = await axios.get("http://localhost:8080/cart/subtotal", {
                withCredentials: true,
            });
            const subtotal = response.data.subtotal || 0;
            setCartSubtotal(subtotal);
            const calculatedTax = subtotal * TAX_RATE;
            setTax(calculatedTax);
            setTotal(subtotal + calculatedTax);
        } catch (error) {
            console.error("Error fetching cart subtotal:", error);
            alert("Failed to fetch cart subtotal. Please try again.");
            navigate("/");
        }
    };

    const fetchCartDetails = async () => {
        try {
            const response = await axios.get("http://localhost:8080/cart/details", {
                params: { username: "loggedInUsername" },
                withCredentials: true,
            });
            setCartDetails(response.data);
        } catch (error) {
            console.error("Error fetching cart details:", error);
            alert("Failed to fetch cart details. Please try again.");
        }
    };

    useEffect(() => {
        fetchCartSubtotal();
        fetchCartDetails();
    }, [navigate]);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const paymentData = {
            ...formData,
            amount: total,
        };

    try {
            const response = await axios.post("http://localhost:8080/payments/payRoom", paymentData, {
                headers: { "Content-Type": "application/json" },
                withCredentials: true,
            });

            if (response.status === 200) {
                navigate(`/reservation/confirmation/${response.data.id}`);
            } else {
                alert("Payment failed. Please try again.");
            }
        } catch (error) {
            console.error("Error processing payment:", error);
            alert("Payment failed. Please try again.");
        }
    };

    return (
        <Container maxWidth="lg" sx={{ mt: 5, mb: 5 }}>
            <Paper elevation={3} sx={{ p: 4 }}>
                <Typography variant="h4" align="center" gutterBottom>
                    Secure Payment
                </Typography>
                <Grid container spacing={4}>
                    {/* Guest Information Form */}
                    <Grid item xs={12} md={8}>
                        <Box component="form" onSubmit={handleSubmit} noValidate>
                            <Typography variant="h6" gutterBottom>
                                Guest Information
                            </Typography>
                            <Grid container spacing={2}>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        fullWidth
                                        label="First Name"
                                        name="firstName"
                                        value={formData.firstName}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        fullWidth
                                        label="Last Name"
                                        name="lastName"
                                        value={formData.lastName}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        label="Email Address"
                                        name="email"
                                        type="email"
                                        value={formData.email}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        label="Address"
                                        name="address"
                                        value={formData.address}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={6}>
                                    <TextField
                                        fullWidth
                                        label="City"
                                        name="city"
                                        value={formData.city}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={3}>
                                    <TextField
                                        fullWidth
                                        label="State"
                                        name="state"
                                        value={formData.state}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={3}>
                                    <TextField
                                        fullWidth
                                        label="Zip Code"
                                        name="zip"
                                        value={formData.zip}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                            </Grid>

                            <Divider sx={{ my: 4 }} />

                            <Typography variant="h6" gutterBottom>
                                Payment Information
                            </Typography>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        label="Credit/Debit Card Number"
                                        name="creditCardNumber"
                                        value={formData.creditCardNumber}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={6} sm={3}>
                                    <TextField
                                        fullWidth
                                        label="Expiration Month"
                                        name="expirationMonth"
                                        value={formData.expirationMonth}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={6} sm={3}>
                                    <TextField
                                        fullWidth
                                        label="Expiration Year"
                                        name="expirationYear"
                                        value={formData.expirationYear}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={3}>
                                    <TextField
                                        fullWidth
                                        label="CVV"
                                        name="cvv"
                                        value={formData.cvv}
                                        onChange={handleChange}
                                        required
                                    />
                                </Grid>
                            </Grid>
                            <Button
                                type="submit"
                                variant="contained"
                                fullWidth
                                sx={{
                                    mt: 3,
                                    backgroundColor: "#1976d2",
                                    "&:hover": { backgroundColor: "#1565c0" },
                                }}
                            >
                                Pay ${total.toFixed(2)}
                            </Button>
                        </Box>
                    </Grid>

                    {/* Booking Summary */}
                    <Grid item xs={12} md={4}>
                        <Card sx={{ backgroundColor: "#f5f5f5", p: 2 }}>
                            <CardContent>
                                <Typography variant="h6" gutterBottom>
                                    Booking Summary
                                </Typography>
                                {cartDetails.map((room, index) => {
                                    const roomRate = room?.roomRate || 0.0;
                                    const oceanViewCharge = room?.oceanView ? 20.0 : 0.0;
                                    const smokingCharge = room?.smokingAllowed ? 10.0 : 0.0;
                                    const roomTotal = roomRate + oceanViewCharge + smokingCharge;

                                    return (
                                        <Box key={index} mb={2}>
                                            <Typography variant="body1"><strong>Room {index + 1}:</strong></Typography>
                                            <Typography variant="body2">Theme: {room?.theme || "[Not Provided]"}</Typography>
                                            <Typography variant="body2">Quality Level: {room?.qualityLevel || "[Not Provided]"}</Typography>
                                            <Typography variant="body2">Ocean View: {room?.oceanView ? "Yes" : "No"}</Typography>
                                            <Typography variant="body2">Smoking Allowed: {room?.smokingAllowed ? "Yes" : "No"}</Typography>
                                            <Typography variant="body2">Bed Type: {room?.bedType || "[Not Provided]"}</Typography>
                                            <Typography variant="body2">Base Room Rate: ${roomRate.toFixed(2)}</Typography>
                                            {oceanViewCharge > 0 && (
                                                <Typography variant="body2">Ocean View Charge: ${oceanViewCharge.toFixed(2)}</Typography>
                                            )}
                                            {smokingCharge > 0 && (
                                                <Typography variant="body2">Smoking Charge: ${smokingCharge.toFixed(2)}</Typography>
                                            )}
                                            <Typography variant="body2" fontWeight="bold">Room Total: ${roomTotal.toFixed(2)}</Typography>
                                            <Divider sx={{ my: 2 }} />
                                        </Box>
                                    );
                                })}
                                <Typography variant="h6" gutterBottom>
                                    Subtotal: ${cartSubtotal.toFixed(2)}
                                </Typography>
                                <Typography variant="h6" gutterBottom>
                                    Tax (10%): ${tax.toFixed(2)}
                                </Typography>
                                <Typography variant="h6" fontWeight="bold" gutterBottom>
                                    Total: ${total.toFixed(2)}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                </Grid>
            </Paper>
        </Container>
    );
};

export default PaymentScreen;
