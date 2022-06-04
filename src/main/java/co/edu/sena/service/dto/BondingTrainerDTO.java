package co.edu.sena.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.BondingTrainer} entity.
 */
public class BondingTrainerDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate startTime;

    @NotNull
    private LocalDate endTime;

    private YearDTO year;

    private TrainerDTO trainer;

    private BondingDTO bonding;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public YearDTO getYear() {
        return year;
    }

    public void setYear(YearDTO year) {
        this.year = year;
    }

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }

    public BondingDTO getBonding() {
        return bonding;
    }

    public void setBonding(BondingDTO bonding) {
        this.bonding = bonding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BondingTrainerDTO)) {
            return false;
        }

        BondingTrainerDTO bondingTrainerDTO = (BondingTrainerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bondingTrainerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BondingTrainerDTO{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", year=" + getYear() +
            ", trainer=" + getTrainer() +
            ", bonding=" + getBonding() +
            "}";
    }
}
