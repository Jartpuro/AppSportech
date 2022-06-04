package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Modality} entity.
 */
public class ModalityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String modalityName;

    @NotNull
    @Size(max = 50)
    private String modalityColor;

    @NotNull
    private State modalityState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModalityName() {
        return modalityName;
    }

    public void setModalityName(String modalityName) {
        this.modalityName = modalityName;
    }

    public String getModalityColor() {
        return modalityColor;
    }

    public void setModalityColor(String modalityColor) {
        this.modalityColor = modalityColor;
    }

    public State getModalityState() {
        return modalityState;
    }

    public void setModalityState(State modalityState) {
        this.modalityState = modalityState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModalityDTO)) {
            return false;
        }

        ModalityDTO modalityDTO = (ModalityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, modalityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModalityDTO{" +
            "id=" + getId() +
            ", modalityName='" + getModalityName() + "'" +
            ", modalityColor='" + getModalityColor() + "'" +
            ", modalityState='" + getModalityState() + "'" +
            "}";
    }
}
