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
import UserPortal from './components/UserPortal.js';
import AboutUs from './components/AboutUs.js';
import { CartProvider } from './components/CartItems';
import PaymentConfirmation from './components/PaymentConfirmation'
import ResetPassword from './components/ResetPassword'

function App() {
    return (
        <CartProvider>
        <Router>
            <NavigationBar />
            {/* Adding some spacing for the AppBar */}
            <Box sx={{ paddingTop: {  } }}>
                <Routes>

                    <Route path="/" element={<HomePage />} />
                    <Route path="/reservation/payment" element={<PaymentScreen />} />
                    <Route path="/reservation/payment/confirmation" element={<PaymentConfirmation />} />
                    <Route path="/events" element={<EventsPage />} />
                    <Route path="/ResetPassword/:token" element={<ResetPassword />} />
                    <Route path="/event-reservation/:eventId" element={<EventReservationPage />} />
                    <Route path="/event-reservation-summary" element={<EventSummaryPage />} />
                    <Route path="/event-confirmation/:eventId" element={<EventConfirmationPage />} />
                    <Route path="/experiences" element={<ExperiencePage />} />
                    <Route path="/reservation" element={<ReservationPage />} />
                    <Route path="/admin/homepage" element={<AdminHomePage />} />
                    {/*<Route path="/clerk/homepage" element={<ClerkHomePage />} />*/}
                    <Route path="/rooms" element={<RoomsSuites />} />
                    <Route path="/natureRetreat" element={<NatureRetreatOptions />} />
                    <Route path="/urbanElegance" element={<UrbanElegance />} />
                    <Route path="/vintageCharm" element={<VintageCharm />} />
                    <Route path="/reservation/confirmation" element={<ReservationConfirmationPage />} />
                    <Route path="/userPortal" element={<UserPortal />} />
                    <Route path="/aboutUs" element={<AboutUs />} />
                </Routes>
            </Box>
        </Router>
        </CartProvider>
    );
}

export default App;
