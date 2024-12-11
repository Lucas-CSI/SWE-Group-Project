import React, { useState, useEffect } from 'react';

import {
    Button,
    Box,
    Typography,
    TextField,
    Select,
    MenuItem,
    InputLabel,
    FormControl,
    Checkbox,
    FormControlLabel,
    Divider,
    List,
    ListItem,
    ListItemText,
} from '@mui/material';

import {generateGetRequest, generatePostRequest} from "../services/apiService";
import {checkPreferenceAvailability, handleSubmitRoom} from "./Rooms/RoomModule";

const AdminHomepage = () => {
    const [email, setEmail] = useState('');
    const [clerkEmail, setClerkEmail] = useState('');
    const [clerkUsername, setClerkUsername] = useState('');
    const [clerkInfo, setClerkInfo] = useState('');
    const [modifyUsername, setModifyUsername] = useState('');
    const [permissionLevel, setPermissionLevel] = useState(0);
    const [modifyResult, setModifyResult] = useState('');
    const [roomNumber, setRoomNumber] = useState('');
    const [roomInfo, setRoomInfo] = useState(
        {"id":-1,"roomNumber":"","theme":"","qualityLevel":"","bedType":"","":0,"oceanView":false,"smokingAllowed":false,"isBookedCurrently":false});
    const [stringRoomInfo, setStringRoomInfo] = useState('');
    const [ submissionRoomStatus, setSubmissionRoomStatus ] = useState('');


    const handleSearch = () => {
        console.log(`Searching for: ${email}`);
    };

    const handleCreateClerk = async() => {
        const response = await generatePostRequest("/admin/createAccount/clerk?username="+clerkUsername+"&email="+clerkEmail, {}, {});
        if(response.status === 200)
            setClerkInfo(response.data);
        else
            setClerkInfo(response.response.data);
    }

    const handleModifyPermission = async() => {
        const response = await generatePostRequest("/admin/changePermission?username="+modifyUsername+"&permissionLevel="+permissionLevel, {}, {});
        if(response.status === 200)
            setModifyResult(response.data);
        else
            setModifyResult(response.response.data);
    }

    const handleGetRoomInfo = async () => {
        const response = await generateGetRequest("/admin/getRoom?roomNumber=" + roomNumber, {}, {});
        if(response.status === 200) {
            setRoomInfo(response.data);
            setStringRoomInfo(JSON.stringify(response.data));
        }else {
            setRoomInfo({});
            setStringRoomInfo(response.response.data);
        }
    }

    const handleModifyRoom = (e) => {
        const thing = {...roomInfo};
        if(e.target.name === "smokingAllowed" || e.target.name === "oceanView"){
            thing[e.target.name] = !thing[e.target.name];
        }else
            thing[e.target.name] = e.target.value;
        setRoomInfo(thing);
    }

    const handleSetRoom = async() => {
        const response = await generatePostRequest("/admin/setRoom", roomInfo);
        if(response.status === 200) {
            setSubmissionRoomStatus((response.data));
        }else {
            setSubmissionRoomStatus(response.response.data);
        }
    }

    const [specialRequests, setSpecialRequests] = useState([]);

    useEffect(() => {
        const fetchRequests = () => {
            const requests = JSON.parse(localStorage.getItem("specialRequests")) || [];
            setSpecialRequests(requests);
        };
        fetchRequests();

        const handleStorageUpdate = () => fetchRequests();
        window.addEventListener('storage', handleStorageUpdate);
        return () => window.removeEventListener('storage', fetchRequests);
    }, []);

    return (
        <Box
            sx={{
                position: 'relative',
                height: '100vh',
                backgroundImage: `url("/AdminPortWave.jpeg")`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                backgroundRepeat: 'no-repeat',
                backgroundAttachment: 'fixed',
                padding: 2,
            }}
        >
            {/* Email Search Prompt */}
            <Box
                sx={{
                    position: 'absolute',
                    top: '100px',
                    right: '20px',
                    display: 'flex',
                    alignItems: 'center',
                    gap: '10px',
                    backgroundColor: 'rgba(255, 255, 255, 0.9)',
                    padding: '10px',
                    borderRadius: '8px',
                    boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                }}
            >
                <TextField
                    label="Look up with Email"
                    variant="outlined"
                    size="small"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <Button
                    variant="contained"
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                    }}
                    onClick={handleSearch}
                >
                    Search
                </Button>
            </Box>

            {/* Admin Dashboard Content */}
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    height: '100%',
                }}
            >
                <Typography variant="h4" sx={{ marginBottom: 4, color: 'white' }}>
                    Admin Dashboard
                </Typography>

                <Box sx={{ display: 'flex', gap: 2, marginBottom: 4 }}>
                    <Button
                        variant="contained"
                        sx={{
                            backgroundColor: 'rgb(25,122,140)',
                            color: 'white',
                            width: 200,
                            height: 60,
                            '&:hover': {
                                backgroundColor: '#28c1d8',
                            },
                        }}
                    >
                        Check In
                    </Button>
                    <Button
                        variant="contained"
                        sx={{
                            backgroundColor: 'rgb(25,122,140)',
                            color: 'white',
                            width: 200,
                            height: 60,
                            '&:hover': {
                                backgroundColor: '#28c1d8',
                            },
                        }}
                    >
                        Check Out
                    </Button>
                </Box>
                <Box
                    sx={{
                        top: '100px',
                        right: '20px',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '10px',
                        backgroundColor: 'rgba(255, 255, 255, 0.9)',
                        padding: '10px',
                        borderRadius: '8px',
                        boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                    }}
                >
                    <TextField
                        label="Username"
                        variant="standard"
                        size="small"
                        value={clerkUsername}
                        onChange={(e) => setClerkUsername(e.target.value)}
                    />
                    <TextField
                        label="Email"
                        variant="standard"
                        size="small"
                        value={clerkEmail}
                        onChange={(e) => setClerkEmail(e.target.value)}
                    />
                    <p>{clerkInfo}</p>
                </Box>
                <Button
                    variant="contained"
                    onClick={handleCreateClerk}
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        width: '100%',
                        height: 60,
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                        marginBottom: 4,
                    }}
                >
                    Create Clerk Account
                </Button>

                <Box
                    sx={{
                        top: '100px',
                        right: '20px',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '10px',
                        backgroundColor: 'rgba(255, 255, 255, 0.9)',
                        padding: '10px',
                        borderRadius: '8px',
                        boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                    }}
                >
                    <TextField
                        label="Username"
                        variant="standard"
                        size="small"
                        value={modifyUsername}
                        onChange={(e) => setModifyUsername(e.target.value)}
                    />
                    <FormControl fullWidth>
                        <InputLabel id="accountPermission">Permission</InputLabel>
                        <Select
                            onChange={(e) => setPermissionLevel(e.target.value)}
                            labelId="accountPermission"
                            defaultValue={0}
                            label="Permission Level" variant="standard">
                            <MenuItem value={0}>Guest</MenuItem>
                            <MenuItem value={1}>Clerk</MenuItem>
                            <MenuItem value={2}>Admin</MenuItem>
                        </Select>
                    </FormControl>
                    <p>{modifyResult}</p>
                </Box>

                <Button
                    variant="contained"
                    onClick={handleModifyPermission}
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        width: '100%',
                        height: 60,
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                        marginBottom: 4,
                    }}
                >
                    Modify Account Permissions
                </Button>

                <Box
                    sx={{
                        top: '100px',
                        right: '20px',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '10px',
                        backgroundColor: 'rgba(255, 255, 255, 0.9)',
                        padding: '10px',
                        borderRadius: '8px',
                        boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                    }}
                >
                    <TextField
                        label="Room Number"
                        variant="standard"
                        size="small"
                        value={roomNumber}
                        onChange={(e) => setRoomNumber(e.target.value)}
                    />
                    <p>{stringRoomInfo}</p>
                </Box>


                <Button
                    variant="contained"
                    onClick={handleGetRoomInfo}
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        width: '100%',
                        height: 60,
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                    }}
                >
                    Get Room Status
                </Button>
                <Box
                    sx={{
                        top: '100px',
                        right: '20px',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '10px',
                        backgroundColor: 'rgba(255, 255, 255, 0.9)',
                        padding: '10px',
                        borderRadius: '8px',
                        boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                    }}
                >
                    <TextField
                        label="ID"
                        variant="standard"
                        size="small"
                        value={roomInfo.id}
                        onChange={handleModifyRoom}
                    />
                    <TextField
                        label="Room Number"
                        variant="standard"
                        size="small"
                        value={roomInfo.roomNumber}
                        onChange={handleModifyRoom}
                    />
                    <TextField
                        label="Theme"
                        variant="standard"
                        size="small"
                        value={roomInfo.theme}
                        onChange={handleModifyRoom}
                    />
                    <TextField
                        label="Quality Level"
                        variant="standard"
                        size="small"
                        value={roomInfo.qualityLevel}
                        onChange={handleModifyRoom}
                    />
                    <FormControlLabel
                        control={
                            <Checkbox
                                name="smokingAllowed"
                                checked={roomInfo.smokingAllowed}
                                onChange={handleModifyRoom}
                                defaultChecked={false}
                            />
                        }
                        label="Smoking Allowed"
                    />
                    <FormControlLabel
                        control={
                            <Checkbox
                                name="oceanView"
                                checked={roomInfo.oceanView}
                                onChange={handleModifyRoom}
                                defaultChecked={false}
                            />
                        }
                        label="Ocean View"
                    />
                    <p>{submissionRoomStatus}</p>
                </Box>


                <Button
                    variant="contained"
                    onClick={handleSetRoom}
                    sx={{
                        backgroundColor: 'rgb(25,122,140)',
                        color: 'white',
                        width: '100%',
                        height: 60,
                        '&:hover': {
                            backgroundColor: '#28c1d8',
                        },
                    }}
                >
                    Add Room / Set Room Status
                </Button>
                <Typography variant="h5" sx={{ marginTop: '2rem' }}>
                    Special Requests from Guests
                </Typography>
                <Divider sx={{ marginBottom: '1rem' }} />
                <List>
                    {specialRequests.length > 0 ? (
                        specialRequests.map((request, index) => (
                            <ListItem key={index} alignItems="flex-start">
                                <ListItemText
                                    primary={`${request.title} - Preferences: Smoking ${
                                        request.smokingAllowed ? "Allowed" : "Not Allowed"
                                    }, Ocean View ${request.oceanView ? "Requested" : "Not Requested"}`}
                                    secondary={request.specialRequest}
                                />
                            </ListItem>
                        ))
                    ) : (
                        <Typography variant="body2">No special requests available.</Typography>
                    )}
                </List>
            </Box>
        </Box>
    );
};

export default AdminHomepage;
