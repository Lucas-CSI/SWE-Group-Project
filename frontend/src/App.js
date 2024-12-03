import React from 'react';
import { Box } from '@mui/material';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavigationBar from './components/NavigationBar.js';
import HomePage from './components/HomePage.js';
import PaymentScreen from './components/PaymentScreen.js';
import EventsPage from './components/EventPageComponents/EventsPage.js';
import EventReservationPage from './components/EventPageComponents/EventReservationPage.js';
import EventSummaryPage from './components/EventPageComponents/EventSummaryPage.js';
import EventConfirmationPage from './components/EventPageComponents/EventConfirmationPage.js';
import ReservationPage from './components/ReservationPageComponents/ReservationPage.js';
import AdminHomePage from './components/AdminHomePage.js';
import RoomsSuites from './components/Rooms/RoomsSuites.js';
import NatureRetreatOptions from './components/Rooms/NatureRetreatOptions.js';
import UrbanElegance from './components/Rooms/UrbanElegance.js';
import VintageCharm from './components/Rooms/VintageCharm.js';
import ExperiencePage from './components/ExperiencePage.js';
import ReservationConfirmationPage from './components/ReservationPageComponents/ReservationConfirmationPage.js';

function App() {
    return (
        <Router>
            <NavigationBar />
            {/* Adding some spacing for the AppBar */}
            <Box sx={{ paddingTop: { xs: '56px', sm: '64px' } }}>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/reservation/payment/:reservationId" element={<PaymentScreen />} />
                    <Route path="/events" element={<EventsPage />} />
                    <Route path="/event-reservation/:eventId" element={<EventReservationPage />} />
                    <Route path="/event-reservation-summary" element={<EventSummaryPage />} />
                    <Route path="/event-confirmation/:eventId" element={<EventConfirmationPage />} />
                    <Route path="/experiences" element={<ExperiencePage />} />
                    <Route path="/reservation" element={<ReservationPage />} />
                    <Route path="/admin/homepage" element={<AdminHomePage />} />
                    <Route path="/rooms" element={<RoomsSuites />} />
                    <Route path="/natureRetreat" element={<NatureRetreatOptions />} />
                    <Route path="/urbanElegance" element={<UrbanElegance />} />
                    <Route path="/vintageCharm" element={<VintageCharm />} />
                    <Route path="/reservation/confirmation" element={<ReservationConfirmationPage />} />
                </Routes>
            </Box>
        </Router>
    );
}

export default App;
