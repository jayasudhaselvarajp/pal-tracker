package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    List<TimeEntry> timeEntries = new ArrayList<>();
    int autoIncrementID = 1;

    public TimeEntry create(TimeEntry timeEntry) {
        if (timeEntry.getId()==0){
            timeEntry.setId(autoIncrementID++);
        }
        timeEntries.add(timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long inputId) {
        for(TimeEntry timeEntry: timeEntries){
            if(timeEntry.getId()==inputId){
                return timeEntry;
            }
        }
        return null;
    }

    public List<TimeEntry> list() {
        return timeEntries;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntries.remove(find(id));
        timeEntry.setId(id);
        timeEntries.add(timeEntry);
        return timeEntry;
    }

    public void delete(long id) {
        timeEntries.clear();
    }
}
