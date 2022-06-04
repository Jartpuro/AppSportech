package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.State;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Area} entity.
 */
public class AreaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String areaName;

    @Size(max = 1000)
    private String urlLogo;

    @NotNull
    private State areaState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public State getAreaState() {
        return areaState;
    }

    public void setAreaState(State areaState) {
        this.areaState = areaState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaDTO)) {
            return false;
        }

        AreaDTO areaDTO = (AreaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, areaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDTO{" +
            "id=" + getId() +
            ", areaName='" + getAreaName() + "'" +
            ", urlLogo='" + getUrlLogo() + "'" +
            ", areaState='" + getAreaState() + "'" +
            "}";
    }
}
