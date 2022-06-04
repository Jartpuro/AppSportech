package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.ModuleSchedule} entity.
 */
public class ModuleScheduleDTO implements Serializable {

    private Long id;

    private ModuleDTO module;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModuleDTO getModule() {
        return module;
    }

    public void setModule(ModuleDTO module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModuleScheduleDTO)) {
            return false;
        }

        ModuleScheduleDTO moduleScheduleDTO = (ModuleScheduleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moduleScheduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModuleScheduleDTO{" +
            "id=" + getId() +
            ", module=" + getModule() +
            "}";
    }
}
