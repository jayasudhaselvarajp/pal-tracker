package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;

import java.util.List;


public interface TimeEntryRepository {

    TimeEntry create(TimeEntry any);

    TimeEntry update(long timeEntryId, TimeEntry expected);

    void delete(long timeEntryId);

    List<TimeEntry> list();

    TimeEntry find(long nonExistentTimeEntryId);
}
