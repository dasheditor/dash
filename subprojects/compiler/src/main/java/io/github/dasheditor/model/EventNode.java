package io.github.dasheditor.model;

public final class EventNode extends Node {

    private final String eventClass;

    public EventNode(String eventClass) {
        super(eventClass.substring(eventClass.lastIndexOf('/') + 1));
        this.eventClass = eventClass;
    }

    public String getEventClass() {
        return eventClass;
    }
}
