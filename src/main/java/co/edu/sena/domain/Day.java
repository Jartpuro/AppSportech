package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Day.
 */
@Entity
@Table(name = "day")
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "day_name", length = 40, nullable = false, unique = true)
    private String dayName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_state", nullable = false)
    private State dayState;

    @OneToMany(mappedBy = "day")
    @JsonIgnoreProperties(value = { "scheduleVersion", "modality", "day", "courseModule", "classroom", "trainer" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Day id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayName() {
        return this.dayName;
    }

    public Day dayName(String dayName) {
        this.setDayName(dayName);
        return this;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public State getDayState() {
        return this.dayState;
    }

    public Day dayState(State dayState) {
        this.setDayState(dayState);
        return this;
    }

    public void setDayState(State dayState) {
        this.dayState = dayState;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.setDay(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setDay(this));
        }
        this.schedules = schedules;
    }

    public Day schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public Day addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setDay(this);
        return this;
    }

    public Day removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setDay(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Day)) {
            return false;
        }
        return id != null && id.equals(((Day) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Day{" +
            "id=" + getId() +
            ", dayName='" + getDayName() + "'" +
            ", dayState='" + getDayState() + "'" +
            "}";
    }
}
