// import React, { useState } from 'react';
// import { Button, Box, Typography, TextField, Dialog, DialogTitle, DialogContent, MenuItem, Select, FormControl, InputLabel } from '@mui/material';
//
// const ClerkHomepage = () => {
//     const [email, setEmail] = useState('');
//     const [isDialogOpen, setIsDialogOpen] = useState(false);
//     const [reservationAction, setReservationAction] = useState('');
//     const [isRoomDialogOpen, setIsRoomDialogOpen] = useState(false);
//     const [roomType, setRoomType] = useState('');
//     const [roomOption, setRoomOption] = useState('');
//     const [roomNumber, setRoomNumber] = useState('');
//
//     const handleSearch = () => {
//         console.log(`Searching for: ${email}`);
//     };
//
//     const handleDialogOpen = () => {
//         setIsDialogOpen(true);
//     };
//
//     const handleDialogClose = () => {
//         setIsDialogOpen(false);
//     };
//
//     const handleReservationActionChange = (event) => {
//         setReservationAction(event.target.value);
//     };
//
//     const handleRoomDialogOpen = () => {
//         setIsRoomDialogOpen(true);
//     };
//
//     const handleRoomDialogClose = () => {
//         setIsRoomDialogOpen(false);
//     };
//
//     const handleRoomTypeChange = (event) => {
//         setRoomType(event.target.value);
//         setRoomOption('');
//     };
//
//     const handleRoomOptionChange = (event) => {
//         setRoomOption(event.target.value);
//     };
//
//     const handleRoomNumberChange = (event) => {
//         setRoomNumber(event.target.value);
//     };
//
//     const handleSeeStatus = () => {
//         console.log(`Checking status for room number: ${roomNumber}, Type: ${roomType}, Option: ${roomOption}`);
//     };
//
//     const getRoomOptions = () => {
//         switch (roomType) {
//             case 'Nature Retreat':
//                 return ['Single Room', 'Double Room', 'Family Room'];
//             case 'Urban Elegance':
//                 return ['Suite Room', 'Deluxe Room'];
//             case 'Vintage Charm':
//                 return ['Standard Room', 'Deluxe Room'];
//             default:
//                 return [];
//         }
//     };
//
//     return (
//         <Box
//             sx={{
//                 position: 'relative',
//                 height: '100vh',
//                 backgroundImage: `url("/ClerkWave.jpg")`,
//                 backgroundSize: 'cover',
//                 backgroundPosition: 'center',
//                 backgroundRepeat: 'no-repeat',
//                 backgroundAttachment: 'fixed',
//                 padding: 2,
//             }}
//         >
//             {/* Email Search Prompt */}
//             <Box
//                 sx={{
//                     position: 'absolute',
//                     top: '100px',
//                     right: '20px',
//                     display: 'flex',
//                     alignItems: 'center',
//                     gap: '10px',
//                     backgroundColor: 'rgba(255, 255, 255, 0.9)',
//                     padding: '10px',
//                     borderRadius: '8px',
//                     boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
//                 }}
//             >
//                 <TextField
//                     label="Look up with Email"
//                     variant="outlined"
//                     size="small"
//                     value={email}
//                     onChange={(e) => setEmail(e.target.value)}
//                 />
//                 <Button
//                     variant="contained"
//                     sx={{
//                         backgroundColor: 'rgb(25,122,140)',
//                         color: 'white',
//                         '&:hover': {
//                             backgroundColor: '#28c1d8',
//                         },
//                     }}
//                     onClick={handleSearch}
//                 >
//                     Search
//                 </Button>
//             </Box>
//
//             {/* Admin Dashboard Content */}
//             <Box
//                 sx={{
//                     display: 'flex',
//                     flexDirection: 'column',
//                     alignItems: 'center',
//                     justifyContent: 'center',
//                     height: '100%',
//                 }}
//             >
//                 <Typography variant="h4" sx={{ marginBottom: 4, color: 'white' }}>
//                     Clerk Dashboard
//                 </Typography>
//
//                 <Box sx={{ display: 'flex', gap: 2, marginBottom: 4 }}>
//                     <Button
//                         variant="contained"
//                         sx={{
//                             backgroundColor: 'rgb(25,122,140)',
//                             color: 'white',
//                             width: 200,
//                             height: 60,
//                             '&:hover': {
//                                 backgroundColor: '#28c1d8',
//                             },
//                         }}
//                     >
//                         Check In
//                     </Button>
//                     <Button
//                         variant="contained"
//                         sx={{
//                             backgroundColor: 'rgb(25,122,140)',
//                             color: 'white',
//                             width: 200,
//                             height: 60,
//                             '&:hover': {
//                                 backgroundColor: '#28c1d8',
//                             },
//                         }}
//                     >
//                         Check Out
//                     </Button>
//                 </Box>
//
//                 <Button
//                     variant="contained"
//                     sx={{
//                         backgroundColor: 'rgb(25,122,140)',
//                         color: 'white',
//                         width: '100%',
//                         height: 60,
//                         '&:hover': {
//                             backgroundColor: '#28c1d8',
//                         },
//                         marginBottom: 4,
//                     }}
//                 >
//                     Modify My Account
//                 </Button>
//
//                 <Button
//                     variant="contained"
//                     sx={{
//                         backgroundColor: 'rgb(25,122,140)',
//                         color: 'white',
//                         width: '100%',
//                         height: 60,
//                         '&:hover': {
//                             backgroundColor: '#28c1d8',
//                         },
//                         marginBottom: 4,
//                     }}
//                     onClick={handleRoomDialogOpen}
//                 >
//                     Room Status
//                 </Button>
//
//                 <Button
//                     variant="contained"
//                     sx={{
//                         backgroundColor: 'rgb(25,122,140)',
//                         color: 'white',
//                         width: '100%',
//                         height: 60,
//                         '&:hover': {
//                             backgroundColor: '#28c1d8',
//                         },
//                         marginTop: 4,
//                     }}
//                     onClick={handleDialogOpen}
//                 >
//                     Reservation Help
//                 </Button>
//             </Box>
//
//             {/* Reservation Help Dialog */}
//             <Dialog open={isDialogOpen} onClose={handleDialogClose}>
//                 <DialogTitle>Reservation Help</DialogTitle>
//                 <DialogContent>
//                     <FormControl fullWidth sx={{ marginTop: 2 }}>
//                         <InputLabel id="reservation-action-label">Select Action</InputLabel>
//                         <Select
//                             labelId="reservation-action-label"
//                             value={reservationAction}
//                             onChange={handleReservationActionChange}
//                         >
//                             <MenuItem value="make">Make Reservation</MenuItem>
//                             <MenuItem value="cancel">Cancel Reservation</MenuItem>
//                             <MenuItem value="modify">Modify Reservation</MenuItem>
//                         </Select>
//                     </FormControl>
//                 </DialogContent>
//             </Dialog>
//
//             {/* Room Status Dialog */}
//             <Dialog open={isRoomDialogOpen} onClose={handleRoomDialogClose}>
//                 <DialogTitle>Room Status</DialogTitle>
//                 <DialogContent>
//                     <FormControl fullWidth sx={{ marginBottom: 2 }}>
//                         <InputLabel id="room-type-label">Room Type</InputLabel>
//                         <Select
//                             labelId="room-type-label"
//                             value={roomType}
//                             onChange={handleRoomTypeChange}
//                         >
//                             <MenuItem value="Nature Retreat">Nature Retreat</MenuItem>
//                             <MenuItem value="Urban Elegance">Urban Elegance</MenuItem>
//                             <MenuItem value="Vintage Charm">Vintage Charm</MenuItem>
//                         </Select>
//                     </FormControl>
//
//                     {roomType && (
//                         <FormControl fullWidth sx={{ marginBottom: 2 }}>
//                             <InputLabel id="room-option-label">Room Option</InputLabel>
//                             <Select
//                                 labelId="room-option-label"
//                                 value={roomOption}
//                                 onChange={handleRoomOptionChange}
//                             >
//                                 {getRoomOptions().map((option) => (
//                                     <MenuItem key={option} value={option}>
//                                         {option}
//                                     </MenuItem>
//                                 ))}
//                             </Select>
//                         </FormControl>
//                     )}
//
//                     <TextField
//                         label="Enter Room Number"
//                         variant="outlined"
//                         fullWidth
//                         sx={{ marginBottom: 2 }}
//                         value={roomNumber}
//                         onChange={handleRoomNumberChange}
//                     />
//
//                     <Button
//                         variant="contained"
//                         fullWidth
//                         sx={{
//                             backgroundColor: 'rgb(25,122,140)',
//                             color: 'white',
//                             '&:hover': {
//                                 backgroundColor: '#28c1d8',
//                             },
//                         }}
//                         onClick={handleSeeStatus}
//                     >
//                         See Status
//                     </Button>
//                 </DialogContent>
//             </Dialog>
//         </Box>
//     );
// };
//
// export default ClerkHomepage;
