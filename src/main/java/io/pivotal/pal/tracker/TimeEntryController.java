package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    //public TimeEntryController(TimeEntryRepository timeEntryRepository) {
     //   this.timeEntryRepository = timeEntryRepository;
  //  }


    public TimeEntryController(
            TimeEntryRepository timeEntryRepository,
            MeterRegistry meterRegistry
    ) {
        this.timeEntryRepository = timeEntryRepository;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }


    @RequestMapping(value = "/time-entries", method = RequestMethod.POST)

    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        System.out.println("Input : " + timeEntry);
        TimeEntry timeEntry1 = timeEntryRepository.create(timeEntry);
        System.out.println("Response " + timeEntry1);
        return new ResponseEntity<TimeEntry>(timeEntry1, HttpStatus.CREATED);
    }

    @RequestMapping("/read")
    public ResponseEntity<TimeEntry> read(long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if (timeEntry != null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/time-entries/{id}")
    public ResponseEntity<TimeEntry> find(@PathVariable("id") long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if(timeEntry != null) {
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(timeEntry, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/time-entries", method = RequestMethod.GET)
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }

    @RequestMapping(value = "/time-entries/{id}",method = RequestMethod.PUT)
    public ResponseEntity<TimeEntry> update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry timeEntry) {
        TimeEntry timeEntry1 = timeEntryRepository.update(timeEntryId, timeEntry);
        if (timeEntry1 != null) {
            return new ResponseEntity<TimeEntry>(timeEntry1, HttpStatus.OK);
        } else {
            return new ResponseEntity<TimeEntry>(timeEntry1, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/time-entries/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity<TimeEntry>(new TimeEntry(), HttpStatus.NO_CONTENT);
    }
}
