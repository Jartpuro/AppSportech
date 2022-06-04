package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ScheduleVersion.
 */
@Entity
@Table(name = "schedule_version")
public class ScheduleVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "version_number", length = 40, nullable = false)
    private String versionNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "version_state", nullable = false)
    private State versionState;

    @OneToMany(mappedBy = "scheduleVersion")
    @JsonIgnoreProperties(value = { "scheduleVersion", "modality", "day", "courseModule", "classroom", "trainer" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ScheduleVersion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionNumber() {
        return this.versionNumber;
    }

    public ScheduleVersion versionNumber(String versionNumber) {
        this.setVersionNumber(versionNumber);
        return this;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public State getVersionState() {
        return this.versionState;
    }

    public ScheduleVersion versionState(State versionState) {
        this.setVersionState(versionState);
        return this;
    }

    public void setVersionState(State versionState) {
        this.versionState = versionState;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.setScheduleVersion(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setScheduleVersion(this));
        }
        this.schedules = schedules;
    }

    public ScheduleVersion schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public ScheduleVersion addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setScheduleVersion(this);
        return this;
    }

    public ScheduleVersion removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setScheduleVersion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleVersion)) {
            return false;
        }
        return id != null && id.equals(((ScheduleVersion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleVersion{" +
            "id=" + getId() +
            ", versionNumber='" + getVersionNumber() + "'" +
            ", versionState='" + getVersionState() + "'" +
            "}";
    }
}
