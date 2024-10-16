import React from 'react';
import { Box } from '@mui/material';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import NavigationBar from './components/NavigationBar';
import HomePage from './components/HomePage';
import PaymentScreen from './components/PaymentScreen';
import EventsPage from './components/EventsPage';

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
                </Routes>
            </Box>
        </Router>
    );
}

export default App;
