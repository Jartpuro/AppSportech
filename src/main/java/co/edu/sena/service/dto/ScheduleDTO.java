package co.edu.sena.service.dto;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Schedule} entity.
 */
public class ScheduleDTO implements Serializable {

    private Long id;

    @NotNull
    private Duration startTime;

    @NotNull
    private Duration endTime;

    private ScheduleVersionDTO scheduleVersion;

    private ModalityDTO modality;

    private DayDTO day;

    private CourseModuleDTO courseModule;

    private ClassroomDTO classroom;

    private TrainerDTO trainer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getStartTime() {
        return startTime;
    }

    public void setStartTime(Duration startTime) {
        this.startTime = startTime;
    }

    public Duration getEndTime() {
        return endTime;
    }

    public void setEndTime(Duration endTime) {
        this.endTime = endTime;
    }

    public ScheduleVersionDTO getScheduleVersion() {
        return scheduleVersion;
    }

    public void setScheduleVersion(ScheduleVersionDTO scheduleVersion) {
        this.scheduleVersion = scheduleVersion;
    }

    public ModalityDTO getModality() {
        return modality;
    }

    public void setModality(ModalityDTO modality) {
        this.modality = modality;
    }

    public DayDTO getDay() {
        return day;
    }

    public void setDay(DayDTO day) {
        this.day = day;
    }

    public CourseModuleDTO getCourseModule() {
        return courseModule;
    }

    public void setCourseModule(CourseModuleDTO courseModule) {
        this.courseModule = courseModule;
    }

    public ClassroomDTO getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomDTO classroom) {
        this.classroom = classroom;
    }

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleDTO)) {
            return false;
        }

        ScheduleDTO scheduleDTO = (ScheduleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, scheduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleDTO{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", scheduleVersion=" + getScheduleVersion() +
            ", modality=" + getModality() +
            ", day=" + getDay() +
            ", courseModule=" + getCourseModule() +
            ", classroom=" + getClassroom() +
            ", trainer=" + getTrainer() +
            "}";
    }
}
