import React, { useState, useEffect } from 'react';
import { Container, Typography, Box, Grid, Button, Divider, List, ListItem, ListItemText } from '@mui/material';
import axios from 'axios';

const PaymentSummaryScreen = () => {
    const [paymentInfo, setPaymentInfo] = useState(null);

    useEffect(() => {
        //Fetches the data from the backend
        const fetchPaymentInfo = async () => {
            try {
                const response = await axios.get('/billing/1'); //Replace as needed
                setPaymentInfo(response.data);
            } catch (error) {
                console.error("Error fetching payment details:", error);
            }
        };
        fetchPaymentInfo();
    }, []);

    const handleConfirm = () => {
        alert("Payment confirmed!");
    };

    if (!paymentInfo) {
        return <Typography>Loading payment details...</Typography>;
    }

    return (
        <Container maxWidth="md">
            <Typography variant="h4" gutterBottom>
                Payment Summary
            </Typography>

            <Grid container spacing={2} mb={4}>
                <Grid item xs={12}>
                    <Box>
                        <Typography variant="h6">Itemized Charges</Typography>
                        <List>
                            {paymentInfo.charges.map((charge, index) => (
                                <ListItem key={index}>
                                    <ListItemText
                                        primary={charge.description}
                                        secondary={`Amount: $${charge.amount}`}
                                    />
                                </ListItem>
                            ))}
                        </List>

                        <Divider sx={{ my: 4 }} />

                        <Typography variant="h6">Room Charges</Typography>
                        <Typography variant="body1">Room Rate: ${paymentInfo.roomRate}</Typography>
                        <Typography variant="body1">Taxes: ${paymentInfo.taxes}</Typography>
                        <Typography variant="body1">Discounts: ${paymentInfo.discount || "0.00"}</Typography>

                        <Divider sx={{ my: 4 }} />

                        <Typography variant="h5">Total Amount Due: ${paymentInfo.totalAmount}</Typography>

                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleConfirm}
                            sx={{ mt: 3 }}
                        >
                            Confirm and Proceed to Payment
                        </Button>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
};

export default PaymentSummaryScreen;
