
import React from 'react';
import { Box, Typography, Divider, Grid, Card, CardContent, Button, CardMedia } from '@mui/material';

const RoomOption = ({ title }) => {
    return (
        <Card sx={{ backgroundColor: '#f2f2f2', padding: '1rem', position: 'relative', height: '100%' }}>
            {/* Placeholder Image */}
            <CardMedia>
                <Box
                    component="img"
                    src="placeholder.jpg" // Replace with actual image source
                    alt={title}
                    sx={{ width: '100%', height: '150px', objectFit: 'cover', borderRadius: '8px' }}
                />
            </CardMedia>

            <CardContent sx={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                <Typography variant="h6" sx={{ marginBottom: '0.5rem' }}>
                    {title}
                </Typography>
                <Typography variant="body2" sx={{ marginBottom: '1rem' }}>
                    Text
                </Typography>
                {/* "View Rates & Reserve" Button */}
                <Button
                    variant="outlined"
                    color="primary"
                    sx={{ position: 'absolute', bottom: 10, right: 10 }}
                >
                    View Rates & Reserve
                </Button>
            </CardContent>
        </Card>
    );
};

const NatureRetreatOps = () => (
    <Box sx={{ padding: '2rem' }}>
        <Typography variant="h4" sx={{ marginBottom: '1rem' }}>
            Nature Retreat
        </Typography>
        <Divider sx={{ marginBottom: '1rem' }} />
        <Grid container spacing={2}>
            <Grid item xs={12} md={4}>
                <RoomOption title="Single Room" />
            </Grid>
            <Grid item xs={12} md={4}>
                <RoomOption title="Double Room" />
            </Grid>
            <Grid item xs={12} md={4}>
                <RoomOption title="Family Room" />
            </Grid>
        </Grid>
    </Box>
);

export default NatureRetreatOps;
