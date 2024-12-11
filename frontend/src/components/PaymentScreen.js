import React, {useState, useEffect, useContext} from "react";
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
import {CartContext} from "./CartItems";
import {generateGetRequest, generatePostRequest} from "../services/apiService";

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

    const [selectedReservationId, setSelectedReservationId] = useState(null);
    const { cartItems } = useContext(CartContext);
    const [cartSubtotal, setCartSubtotal] = useState(0);
    const [tax, setTax] = useState(0);
    const [total, setTotal] = useState(0);
    const [cartDetails, setCartDetails] = useState([]);
    const navigate = useNavigate();

    const TAX_RATE = 0.10;
    const [errors, setErrors] = useState({});

    const validate = () => {
        const validationErrors = {};

        if (!formData.firstName) validationErrors.firstName = "First Name is required.";
        if (!formData.lastName) validationErrors.lastName = "Last Name is required.";
        if (!formData.email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email))
            validationErrors.email = "A valid Email Address is required.";
        if (!formData.address) validationErrors.address = "Address is required.";
        if (!formData.city) validationErrors.city = "City is required.";
        if (!formData.state) validationErrors.state = "State is required.";
        if (!formData.zip || !/^\d{5}(-\d{4})?$/.test(formData.zip))
            validationErrors.zip = "A valid ZIP Code is required.";

        if (!formData.creditCardNumber || !/^\d{16}$/.test(formData.creditCardNumber))
            validationErrors.creditCardNumber = "A valid 16-digit Credit/Debit Card Number is required.";
        if (!formData.expirationMonth || !/^(0[1-9]|1[0-2])$/.test(formData.expirationMonth))
            validationErrors.expirationMonth = "Enter a valid month (01-12).";
        if (!formData.expirationYear || !/^\d{4}$/.test(formData.expirationYear))
            validationErrors.expirationYear = "Enter a valid year (e.g., 2025).";
        if (!formData.cvv || !/^\d{3,4}$/.test(formData.cvv))
            validationErrors.cvv = "A valid 3 or 4-digit CVV is required.";

        setErrors(validationErrors);
        return Object.keys(validationErrors).length === 0;
    };

    useEffect(() => {
        const subtotal = cartItems.reduce((sum, item) => sum + item.price, 0);
        const calculatedTax = subtotal * TAX_RATE;
        setCartSubtotal(subtotal);
        setTax(calculatedTax);
        setTotal(subtotal + calculatedTax);
    }, [cartItems]);

    useEffect(() => {
        const fetchReservationId = async () => {
            try {
                const response = await generateGetRequest("/reservation/current");
                if (response && response.data) {
                    setSelectedReservationId(response.data);
                } else {
                    alert("No reservation found. Please ensure you have an active reservation.");
                }
            } catch (error) {
                console.error("Error fetching reservation:", error);
                alert("Failed to fetch reservation. Please try again.");
            }
        };

        fetchReservationId();
    }, []);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if(!validate()){
            return;
        }

        if (!selectedReservationId) {
            alert("No reservation selected.");
            return;
        }

        const paymentData = {
            reservationId: selectedReservationId,
            paymentMethod: formData.paymentMethod,
            billingAddress: formData.address,
            cardNumber: formData.creditCardNumber,
            expirationDate: `${formData.expirationMonth}/${formData.expirationYear}`,
            cvv: formData.cvv,
        };



        try {
            const response = await generatePostRequest("/payments/payRoom", paymentData);

            if (response && response.status === 200) {
                navigate(`/reservation/confirmation`);
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
                                        error={!!errors.firstName}
                                        helperText={errors.firstName}
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
                                        error={!!errors.lastName}
                                        helperText={errors.lastName}
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
                                        error={!!errors.email}
                                        helperText={errors.email}
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
                                        error={!!errors.address}
                                        helperText={errors.address}
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
                                        error={!!errors.city}
                                        helperText={errors.city}
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
                                        error={!!errors.state}
                                        helperText={errors.state}
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
                                        error={!!errors.zip}
                                        helperText={errors.zip}
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
                                        error={!!errors.creditCardNumber}
                                        helperText={errors.creditCardNumber}
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
                                        error={!!errors.expirationMonth}
                                        helperText={errors.expirationMonth}
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
                                        error={!!errors.expirationYear}
                                        helperText={errors.expirationYear}
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
                                        error={!!errors.cvv}
                                        helperText={errors.cvv}
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
                                    backgroundColor: "rgba(0, 59, 74, 0.65)",
                                    color: "white",
                                    fontWeight: "bold",
                                    padding: "0.75rem 1rem",
                                    borderRadius: "5px",
                                    border: "none",
                                    transition: "background-color 0.3s ease, transform 0.2s ease",
                                    zIndex: 3,
                                    "&:hover": {
                                        backgroundColor: "rgba(0, 59, 74, 0.85)",
                                        transform: "scale(1.02)",
                                    },
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
                                {cartItems.map((room, index) => {
                                    const roomTotal = room.price + (room.oceanView ? 20.0 : 0.0) + (room.smokingAllowed ? 10.0 : 0.0);

                                    return (
                                        <Box key={index} mb={2}>
                                            <Typography variant="body1"><strong>Room {index + 1}:</strong></Typography>
                                            <Typography variant="body2">Room Number: {room.roomNumber}</Typography>
                                            <Typography variant="body2">Theme: {room.theme}</Typography>
                                            <Typography variant="body2">Quality Level: {room.qualityLevel}</Typography>
                                            <Typography variant="body2">Ocean View: {room.oceanView ? "Yes" : "No"}</Typography>
                                            <Typography variant="body2">Smoking Allowed: {room.smokingAllowed ? "Yes" : "No"}</Typography>
                                            <Typography variant="body2">Bed Type: {room.bedType}</Typography>
                                            <Typography variant="body2">Base Room Rate: ${room.price.toFixed(2)}</Typography>
                                            {room.oceanView && <Typography variant="body2">Ocean View Charge: $20.00</Typography>}
                                            {room.smokingAllowed && <Typography variant="body2">Smoking Charge: $10.00</Typography>}
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