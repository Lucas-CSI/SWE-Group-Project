const apiService = require("../../services/apiService");

export const formattedGetAvailableRooms = async () => {
    const availableRooms = await apiService.generateGetRequest("/getAvailableRooms");
    let rooms = availableRooms.data;
    let formattedRooms = {
        "URBAN_ELEGANCE": {
            "total": 0,
            "Comfort":{
                "total": 0,
                "oceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
                "noOceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
            },
            "Suite": {
                "total": 0,
                "oceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
                "noOceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
            }
        },
        "VINTAGE_CHARM": {
            "total": 0,
            "Comfort":{
                "total": 0,
                "oceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
                "noOceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
            },
            "Suite": {
                "total": 0,
                "oceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
                "noOceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
            }
        },
        "NATURE_RETREAT": {
            "total": 0,
            "Comfort":{
                "total": 0,
                "oceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
                "noOceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
            },
            "Suite": {
                "total": 0,
                "oceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
                "noOceanView": {
                    "total": 0,
                    "smokingAllowed": 0,
                    "noSmokingAllowed": 0
                },
            }
        },
    };
    if(rooms) {
        for(let i = 0; i < rooms.length; ++i){
            let themeTable = formattedRooms[rooms[i].theme]
            let room = rooms[i];
            console.log(room);
            if(room.qualityLevel === "Comfort" || room.qualityLevel === "Suite") {
                let toChange = themeTable[room.qualityLevel];
                toChange.total++;
                if (room.oceanView){
                    toChange.oceanView.total++;
                    if(room.smokingAllowed){
                        toChange.oceanView.smokingAllowed++;
                    }else{
                        toChange.oceanView.noSmokingAllowed++;
                    }
                }else{
                    toChange.noOceanView.total++;
                    if(room.smokingAllowed){
                        toChange.noOceanView.smokingAllowed++;
                    }else{
                        toChange.noOceanView.noSmokingAllowed++;
                    }
                }
            }
            themeTable.total++;
        }
    }
    console.log(formattedRooms);
    return formattedRooms;
}


const sendAddRoomRequest = async(reservation) => {
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
    await sendAddRoomRequest(reservation);
}

export const handleSuite = async () => {
    let reservation = JSON.parse(localStorage.getItem("reservation"));
    reservation.room.bedType = "King";
    reservation.room.qualityLevel = 2;
    await sendAddRoomRequest(reservation);
}