import React, { useState } from "react";
import {
    Container,
    TextField,
    Button,
    Typography,
    Box,
    Alert,
    Stack,
} from "@mui/material";
import axios from "axios";

const AdminCheckInOut = () => {
    const [email, setEmail] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const handleCheckIn = async () => {
        try {
            const response = await axios.post("/reservation/check-in", null, {
                params: { email },
            });
            setSuccessMessage(response.data);
            setErrorMessage("");
        } catch (err) {
            console.error(err);
            setSuccessMessage("");
            setErrorMessage(err.response?.data || "Failed to check-in.");
        }
    };

    const handleCheckOut = async () => {
        try {
            const response = await axios.post("/reservation/check-out", null, {
                params: { email },
            });
            setSuccessMessage(response.data);
            setErrorMessage("");
        } catch (err) {
            console.error(err);
            setSuccessMessage("");
            setErrorMessage(err.response?.data || "Failed to check-out.");
        }
    };

    return (
        <Container maxWidth="sm" sx={{ marginTop: "4rem" }}>
            <Box
                sx={{
                    padding: "2rem",
                    backgroundColor: "rgba(255, 255, 255, 0.9)",
                    borderRadius: "12px",
                    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
                }}
            >
                <Typography variant="h4" align="center" gutterBottom>
                    Admin Check-In/Check-Out
                </Typography>
                {successMessage && (
                    <Stack sx={{ marginBottom: "1rem" }}>
                        <Alert severity="success">{successMessage}</Alert>
                    </Stack>
                )}
                {errorMessage && (
                    <Stack sx={{ marginBottom: "1rem" }}>
                        <Alert severity="error">{errorMessage}</Alert>
                    </Stack>
                )}
                <TextField
                    label="Guest Email"
                    type="email"
                    fullWidth
                    margin="normal"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <Stack direction="row" spacing={2} justifyContent="center" mt={2}>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleCheckIn}
                        disabled={!email}
                    >
                        Check In
                    </Button>
                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={handleCheckOut}
                        disabled={!email}
                    >
                        Check Out
                    </Button>
                </Stack>
            </Box>
        </Container>
    );
};

export default AdminCheckInOut;