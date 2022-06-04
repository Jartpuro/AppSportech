package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.BoundingSchedule} entity.
 */
public class BoundingScheduleDTO implements Serializable {

    private Long id;

    private BondingTrainerDTO bondingTrainer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BondingTrainerDTO getBondingTrainer() {
        return bondingTrainer;
    }

    public void setBondingTrainer(BondingTrainerDTO bondingTrainer) {
        this.bondingTrainer = bondingTrainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoundingScheduleDTO)) {
            return false;
        }

        BoundingScheduleDTO boundingScheduleDTO = (BoundingScheduleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, boundingScheduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoundingScheduleDTO{" +
            "id=" + getId() +
            ", bondingTrainer=" + getBondingTrainer() +
            "}";
    }
}
