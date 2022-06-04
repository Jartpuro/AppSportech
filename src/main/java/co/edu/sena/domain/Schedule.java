package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Duration;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Duration startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Duration endTime;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "schedules" }, allowSetters = true)
    private ScheduleVersion scheduleVersion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "schedules" }, allowSetters = true)
    private Modality modality;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "schedules" }, allowSetters = true)
    private Day day;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "schedules", "course", "module" }, allowSetters = true)
    private CourseModule courseModule;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "schedules", "classroomType", "campus" }, allowSetters = true)
    private Classroom classroom;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "areaTrainers", "bondingTrainers", "schedules", "customer" }, allowSetters = true)
    private Trainer trainer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Schedule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getStartTime() {
        return this.startTime;
    }

    public Schedule startTime(Duration startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Duration startTime) {
        this.startTime = startTime;
    }

    public Duration getEndTime() {
        return this.endTime;
    }

    public Schedule endTime(Duration endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Duration endTime) {
        this.endTime = endTime;
    }

    public ScheduleVersion getScheduleVersion() {
        return this.scheduleVersion;
    }

    public void setScheduleVersion(ScheduleVersion scheduleVersion) {
        this.scheduleVersion = scheduleVersion;
    }

    public Schedule scheduleVersion(ScheduleVersion scheduleVersion) {
        this.setScheduleVersion(scheduleVersion);
        return this;
    }

    public Modality getModality() {
        return this.modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public Schedule modality(Modality modality) {
        this.setModality(modality);
        return this;
    }

    public Day getDay() {
        return this.day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Schedule day(Day day) {
        this.setDay(day);
        return this;
    }

    public CourseModule getCourseModule() {
        return this.courseModule;
    }

    public void setCourseModule(CourseModule courseModule) {
        this.courseModule = courseModule;
    }

    public Schedule courseModule(CourseModule courseModule) {
        this.setCourseModule(courseModule);
        return this;
    }

    public Classroom getClassroom() {
        return this.classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Schedule classroom(Classroom classroom) {
        this.setClassroom(classroom);
        return this;
    }

    public Trainer getTrainer() {
        return this.trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Schedule trainer(Trainer trainer) {
        this.setTrainer(trainer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        return id != null && id.equals(((Schedule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
