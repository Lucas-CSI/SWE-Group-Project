import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import PaymentScreen from './components/PaymentScreen';
import PaymentSummaryScreen from './components/PaymentSummaryScreen';
import EventsPage from "./components/EventsPage"


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/payment" element={<PaymentScreen />} />
                <Route path="/" element={<HomePage />} />
                <Route path="/payment-summary" element={<PaymentSummaryScreen />} />
                <Route path="/events" element={<EventsPage />} /> {/* Events route */}
            </Routes>
        </Router>
    );

}

export default App;
