// Did not end up doing this
// import React, { useState } from 'react';
// import { TextField, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
// //import { useHistory } from 'react-router-dom';
// import { useNavigate } from 'react-router-dom';
//
// import axios from 'axios';
//
// const AdminLogin = () =>
//     const [username, setUsername] = useState('');
//     const [password, setPassword] = useState('');
//     const [error, setError] = useState('');
//     const navigate = useNavigate(); // Use navigate instead of useHistory
//
//     const handleAdminLoginSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             const response = await axios.post('http://localhost:8080/adminLogin', {
//                 username,
//                 password,
//             });
//
//             if (response.status === 302) {
//                 // Assuming admin login is successful and response is a redirect
//                 const token = response.headers['location'];
//                 localStorage.setItem('admin', token); // Store some kind of indication of admin login
//                 navigate('/admin/homepage'); // Redirect to the admin login page
//             }
//         } catch (error) {
//             setError('Admin login failed. Please check your credentials.');
//         }
//     };
//
//     return (
//         <Dialog open={true}>
//             <DialogTitle>Admin Login</DialogTitle>
//             <DialogContent>
//                 <DialogContentText>Please enter your admin login credentials.</DialogContentText>
//                 {error && <p style={{ color: 'red' }}>{error}</p>} {/* Show error if login fails */}
//                 <TextField
//                     autoFocus
//                     margin="dense"
//                     label="Username"
//                     type="text"
//                     fullWidth
//                     variant="standard"
//                     value={username}
//                     onChange={(e) => setUsername(e.target.value)}
//                 />
//                 <TextField
//                     margin="dense"
//                     label="Password"
//                     type="password"
//                     fullWidth
//                     variant="standard"
//                     value={password}
//                     onChange={(e) => setPassword(e.target.value)}
//                 />
//             </DialogContent>
//             <DialogActions>
//                 <Button onClick={() => navigate('/')}>Cancel</Button>
//                 <Button onClick={handleAdminLoginSubmit}>Admin Login</Button>
//             </DialogActions>
//         </Dialog>
//     );
// };
//
// export default AdminLogin;
