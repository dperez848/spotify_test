package com.daniela.app.busevents;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 6/16/17.
 */

public class EventNoteAdded {

    private String note;
    private String program;

    public EventNoteAdded(String note, String program) {
        this.note = note;
        this.program = program;
    }

    public String getNote() {
        return note;
    }

    public String getProgram() {
        return program;
    }
}
