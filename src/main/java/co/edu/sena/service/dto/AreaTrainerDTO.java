package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.AreaTrainer} entity.
 */
public class AreaTrainerDTO implements Serializable {

    private Long id;

    @NotNull
    private State areaTrainerState;

    private AreaDTO area;

    private TrainerDTO trainer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getAreaTrainerState() {
        return areaTrainerState;
    }

    public void setAreaTrainerState(State areaTrainerState) {
        this.areaTrainerState = areaTrainerState;
    }

    public AreaDTO getArea() {
        return area;
    }

    public void setArea(AreaDTO area) {
        this.area = area;
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
        if (!(o instanceof AreaTrainerDTO)) {
            return false;
        }

        AreaTrainerDTO areaTrainerDTO = (AreaTrainerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, areaTrainerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaTrainerDTO{" +
            "id=" + getId() +
            ", areaTrainerState='" + getAreaTrainerState() + "'" +
            ", area=" + getArea() +
            ", trainer=" + getTrainer() +
            "}";
    }
}
