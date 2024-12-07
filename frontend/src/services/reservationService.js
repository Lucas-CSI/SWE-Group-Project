const apiService = require("apiService");

export const addRoom = async(room) => {
    return await apiService.generatePostRequest("/reservation/addRoom", room);
}

