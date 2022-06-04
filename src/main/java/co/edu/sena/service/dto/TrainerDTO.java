package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Trainer} entity.
 */
public class TrainerDTO implements Serializable {

    private Long id;

    @NotNull
    private State trainerState;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getTrainerState() {
        return trainerState;
    }

    public void setTrainerState(State trainerState) {
        this.trainerState = trainerState;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainerDTO)) {
            return false;
        }

        TrainerDTO trainerDTO = (TrainerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trainerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainerDTO{" +
            "id=" + getId() +
            ", trainerState='" + getTrainerState() + "'" +
            ", customer=" + getCustomer() +
            "}";
    }
}
