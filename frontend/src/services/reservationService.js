const apiService = require("apiService");

export const addRoom = async(room) => {
    return await apiService.generateRequest("/reservation/addRoom", room);
}

