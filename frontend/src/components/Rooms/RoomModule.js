const apiService = require("../../services/apiService");
const themes = ["NATURE_RETREAT", "URBAN_ELEGANCE", "VINTAGE_CHARM"];
let themesToIndex = {}
for(let i = 0; i < themes.length; ++i){
    themesToIndex[themes[i]] = i;
}
const qualityLevels = ["Comfort", "Suite", "Deluxe", "Economy", "Business", "Executive"];

export const formattedGetAvailableRooms = async () => {
    let cachedRooms = localStorage.getItem("rooms");
    if(cachedRooms != null){
        cachedRooms = JSON.parse(cachedRooms);
        if(Date.now() <= cachedRooms.expiresAt){
            return cachedRooms;
        }
    }

    const availableRooms = await apiService.generateGetRequest("/getAvailableRooms");
    let rooms = availableRooms.data;
    let formattedRooms = {
        expiresAt: Date.now() + 30000
    };
    for(let themeIndex = 0; themeIndex < themes.length; ++themeIndex){
        formattedRooms[themes[themeIndex]] = {
            "total": 0
        }
        for(let qualityIndex = 0; qualityIndex < qualityLevels.length; ++qualityIndex) {
            formattedRooms[themes[themeIndex]][qualityLevels[qualityIndex]] = {
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
        }
    }
    //console.log(formattedRooms);
    if(rooms) {
        for(let i = 0; i < rooms.length; ++i){
            let themeTable = formattedRooms[rooms[i].theme]
            let room = rooms[i];
            //console.log(room);
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
            themeTable.total++;
        }
    }
    localStorage.setItem("rooms", JSON.stringify(formattedRooms));
    //console.log(formattedRooms);
    return formattedRooms;
}


const sendAddRoomRequest = async(reservation) => {
    const response = await apiService.generatePostRequest('reservation/addRoom', reservation, {
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

export const handleSubmitRoom = async (roomInfo) => {
    await sendAddRoomRequest(roomInfo);
}


export const generateRoomData = (theme = "", qualityLevel = 0, oceanView = false, isSmokingAllowed = false) => {
    return {
        bedType: qualityLevel > 1 ? "King" : "Queen",
        qualityLevel: qualityLevel,
        oceanView: oceanView,
        isSmokingAllowed: isSmokingAllowed,
        theme: themesToIndex[theme]
    }
}