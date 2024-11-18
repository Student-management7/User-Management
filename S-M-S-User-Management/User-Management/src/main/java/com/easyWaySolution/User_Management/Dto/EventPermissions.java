package com.easyWaySolution.User_Management.Dto;

import lombok.Data;

@Data
public class EventPermissions {
    private boolean viewEvents;
    private boolean createEvent;
    private boolean updateEvent;
    private boolean deleteEvent;
    EventPermissions(){
        this.viewEvents = false;
        this.createEvent = false;
        this.updateEvent = false;
        this.deleteEvent = false;
    }
}
