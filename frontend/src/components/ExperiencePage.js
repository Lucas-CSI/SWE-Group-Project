import React from 'react';
import {
    Box,
    Typography,
    Grid,
    Card,
    CardMedia,
    CardContent,
    Button,
} from '@mui/material';

const ExperiencePage = () => {
    const experiences = [
        {
            category: 'Relaxation',
            items: [
                { title: 'Beachside Yoga', description: 'Find your balance where the waves meet the shore.', image: 'beachyoga.jpg' },
                { title: 'Poolside Lounge', description: 'Unwind, recline, and let your worries float away.', image: 'poolsideLounge.webp' },
                { title: 'Guided Nature Walks', description: 'Step into serenity, one trail at a time.', image: 'guidedNature.jpg' },
                { title: 'Soothing Spa Treatment', description: 'Relaxation redefined- because you deserve the bliss.', image: 'soothingSpaPic.jpg' },
            ],
        },
        {
            category: 'Culture',
            items: [
                { title: 'Local Cuisine', description: 'Taste the flavors of the region.', image: 'cuisine.jpg' },
                { title: 'Cooking Classes', description: 'Master the art of flavor, one dish at a time.', image: 'cookingClasses.jpg' },
                { title: 'Historic Sites', description: 'Step back in time and uncover the stories etched in stone.', image: 'historicBeachy.jpg' },
            ],
        },
        {
            category: 'Adventure',
            items: [
                { title: 'Jet Skiing', description: 'Ride the waves, chase the thrill.', image: 'jetSkiing.jpg' },
                { title: 'Snorkeling', description: 'Dive in and discover a world beneath the surface.', image: 'snorkeling.jpg' },
                { title: 'Scuba Diving', description: 'Plunge into the deep and explore the blue unknown.', image: 'scubaDiving.webp' },
                { title: 'Parasailing', description: 'Soar above and let your spirit take flight.', image: 'parasailing.webp' },
            ],
        },
    ];

    return (
        <Box>
            {/* Full-page header image */}
            <Box
                sx={{
                    height: '70vh',
                    backgroundImage: 'url(exp.jpg)',
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    color: 'white',
                    textAlign: 'center',
                }}
            >
                <Typography variant="h2" sx={{ fontWeight: 'bold', textShadow: '2px 2px 4px rgba(0,0,0,0.6)' }}>
                    Explore Our Experiences
                </Typography>
            </Box>

            {/* Categories with cards */}
            <Box sx={{ padding: '2rem' }}>
                {experiences.map((category) => (
                    <Box key={category.category} sx={{ marginBottom: '3rem' }}>
                        <Typography variant="h4" sx={{ marginBottom: '2rem', textAlign: 'center' }}>
                            {category.category}
                        </Typography>
                        <Grid container spacing={4} justifyContent="center">
                            {category.items.map((experience, index) => (
                                <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                                    <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
                                        <CardMedia
                                            component="img"
                                            height="160"
                                            image={experience.image}
                                            alt={experience.title}
                                        />
                                        <CardContent>
                                            <Typography variant="h6" gutterBottom>
                                                {experience.title}
                                            </Typography>
                                            <Typography variant="body2" color="textSecondary">
                                                {experience.description}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                </Grid>
                            ))}
                        </Grid>
                        <Box sx={{ textAlign: 'center', marginTop: '2rem' }}>
                            <Button variant="outlined" color="primary" href={`/more-options/${category.category.toLowerCase()}`}>
                                Book Now!
                            </Button>
                        </Box>
                    </Box>
                ))}
            </Box>
        </Box>
    );
};

export default ExperiencePage;
