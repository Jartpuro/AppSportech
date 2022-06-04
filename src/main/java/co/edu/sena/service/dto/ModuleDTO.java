package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Module} entity.
 */
public class ModuleDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer moduleName;

    private String moduleState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getModuleName() {
        return moduleName;
    }

    public void setModuleName(Integer moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleState() {
        return moduleState;
    }

    public void setModuleState(String moduleState) {
        this.moduleState = moduleState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModuleDTO)) {
            return false;
        }

        ModuleDTO moduleDTO = (ModuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moduleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModuleDTO{" +
            "id=" + getId() +
            ", moduleName=" + getModuleName() +
            ", moduleState='" + getModuleState() + "'" +
            "}";
    }
}
