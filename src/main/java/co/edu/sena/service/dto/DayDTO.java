package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Day} entity.
 */
public class DayDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String dayName;

    @NotNull
    private State dayState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public State getDayState() {
        return dayState;
    }

    public void setDayState(State dayState) {
        this.dayState = dayState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DayDTO)) {
            return false;
        }

        DayDTO dayDTO = (DayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayDTO{" +
            "id=" + getId() +
            ", dayName='" + getDayName() + "'" +
            ", dayState='" + getDayState() + "'" +
            "}";
    }
}
