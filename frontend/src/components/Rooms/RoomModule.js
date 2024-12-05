const apiService = require("../../services/apiService");

export const sendRequest = async(reservation) => {
    localStorage.setItem("reservation",JSON.stringify(reservation));
    const response = await apiService.generatePostRequest('addRoom', reservation, {
        headers: {
            'Content-Type': 'application/json',
        },
        withCredentials: true
    });
    if(response.status === 200){
        window.location.replace("http://localhost:3000/reservation/confirmation");
    }else{
        alert("Error: Room not available.");
    }
}

export const handleComfort = async () => {
    let reservation = JSON.parse(localStorage.getItem("reservation"));
    reservation.room.bedType = "Queen";
    reservation.room.qualityLevel = 1;
    await sendRequest(reservation);
}

export const handleSuite = async () => {
    let reservation = JSON.parse(localStorage.getItem("reservation"));
    reservation.room.bedType = "King";
    reservation.room.qualityLevel = 2;
    await sendRequest(reservation);
}