package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.TrainingStatus} entity.
 */
public class TrainingStatusDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String statusName;

    @NotNull
    private State stateTraining;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public State getStateTraining() {
        return stateTraining;
    }

    public void setStateTraining(State stateTraining) {
        this.stateTraining = stateTraining;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingStatusDTO)) {
            return false;
        }

        TrainingStatusDTO trainingStatusDTO = (TrainingStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trainingStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingStatusDTO{" +
            "id=" + getId() +
            ", statusName='" + getStatusName() + "'" +
            ", stateTraining='" + getStateTraining() + "'" +
            "}";
    }
}
