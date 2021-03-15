package project6;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Vaccination {
    private long id;
    private long citizen_id;
    private LocalDateTime vaccination_date;
    private String status;
    private String note;

    public void setNote(String note) {
        this.note = note;
    }

    private String vacc_type;

    public Vaccination(long id, long citizen_id, Timestamp vaccination_date, String status, String note, String vacc_type) {
        this.id = id;
        this.citizen_id = citizen_id;
        this.vaccination_date = vaccination_date.toLocalDateTime();
        this.status = status;
        this.note = note;
        this.vacc_type = vacc_type;
    }

    public long getId() {
        return id;
    }

    public long getCitizen_id() {
        return citizen_id;
    }

    public LocalDateTime getVaccination_date() {
        return vaccination_date;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public String getVacc_type() {
        return vacc_type;
    }

    public void setVaccination_date(LocalDateTime vaccination_date) {
        this.vaccination_date = vaccination_date;
    }
}