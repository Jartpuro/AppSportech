package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Trainee} entity.
 */
public class TraineeDTO implements Serializable {

    private Long id;

    private CustomerDTO customer;

    private TrainingStatusDTO trainingStatus;

    private CourseDTO course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public TrainingStatusDTO getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(TrainingStatusDTO trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TraineeDTO)) {
            return false;
        }

        TraineeDTO traineeDTO = (TraineeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, traineeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TraineeDTO{" +
            "id=" + getId() +
            ", customer=" + getCustomer() +
            ", trainingStatus=" + getTrainingStatus() +
            ", course=" + getCourse() +
            "}";
    }
}
