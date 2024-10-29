import React from 'react';
import { Box } from '@mui/material';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavigationBar from './components/NavigationBar';
import HomePage from './components/HomePage';
import PaymentScreen from './components/PaymentScreen';
import EventsPage from './components/EventPageComponents/EventsPage';
import EventReservationPage from "./components/EventPageComponents/EventReservationPage";
import EventSummaryPage from "./components/EventPageComponents/EventSummaryPage";
import EventConfirmationPage from "./components/EventPageComponents/EventConfirmationPage";
import ReservationPage from "./components/ReservationPageComponents/ReservationPage";
import AdminHomePage from "./components/AdminHomePage";
import ReservationConfirmationPage from "./components/ReservationPageComponents/ReservationConfirmationPage";

function App() {
    return (
        <Router>
            <NavigationBar />
            {/* Adding some spacing for the AppBar */}
            <Box sx={{ paddingTop: '64px' }}>  {/* Make sure this padding matches AppBar height */}
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/payment" element={<PaymentScreen />} />
                    <Route path="/events" element={<EventsPage />} />
                    <Route path="/event-reservation/:eventId" element={<EventReservationPage />} />
                    <Route path="/event-reservation-summary" element={<EventSummaryPage />} />
                    <Route path="/event-confirmation/:eventId" element={<EventConfirmationPage />} />
                    <Route path="/reservation" element={<ReservationPage />} />
                    <Route path="/admin/homepage" element={<AdminHomePage />} />
                    <Route path="/reservation/confirmation" element={<ReservationConfirmationPage />} />
                </Routes>
            </Box>
        </Router>
    );
}

export default App;
// <Route path="/event-confirmation/:eventId" element={<EventConfirmation />} />