package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.Offer;
import co.edu.sena.domain.enumeration.StateProgram;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.TrainingProgram} entity.
 */
public class TrainingProgramDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String programCode;

    @NotNull
    @Size(max = 40)
    private String programVersion;

    @NotNull
    private Offer programName;

    @NotNull
    @Size(max = 40)
    private String programInitials;

    @NotNull
    private StateProgram programState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramVersion() {
        return programVersion;
    }

    public void setProgramVersion(String programVersion) {
        this.programVersion = programVersion;
    }

    public Offer getProgramName() {
        return programName;
    }

    public void setProgramName(Offer programName) {
        this.programName = programName;
    }

    public String getProgramInitials() {
        return programInitials;
    }

    public void setProgramInitials(String programInitials) {
        this.programInitials = programInitials;
    }

    public StateProgram getProgramState() {
        return programState;
    }

    public void setProgramState(StateProgram programState) {
        this.programState = programState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingProgramDTO)) {
            return false;
        }

        TrainingProgramDTO trainingProgramDTO = (TrainingProgramDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trainingProgramDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingProgramDTO{" +
            "id=" + getId() +
            ", programCode='" + getProgramCode() + "'" +
            ", programVersion='" + getProgramVersion() + "'" +
            ", programName='" + getProgramName() + "'" +
            ", programInitials='" + getProgramInitials() + "'" +
            ", programState='" + getProgramState() + "'" +
            "}";
    }
}
