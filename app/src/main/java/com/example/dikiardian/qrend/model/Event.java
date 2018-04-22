package com.example.dikiardian.qrend.model;

/**
 * Created by Diki on 4/23/2018.
 */

public class Event {
    private int eventId;
    private String eventName;
    private User author;
    private User[] participants;
    private Code[] codeList;
    private boolean isActive;
    private double duration;

    public Event(int eventId, String eventName, User author, User[] participants, Code[] codeList, boolean isActive, double duration) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.author = author;
        this.participants = participants;
        this.codeList = codeList;
        this.isActive = isActive;
        this.duration = duration;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User[] getParticipants() {
        return participants;
    }

    public void setParticipants(User[] participants) {
        this.participants = participants;
    }

    public Code[] getCodeList() {
        return codeList;
    }

    public void setCodeList(Code[] codeList) {
        this.codeList = codeList;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
