package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BondingTrainer.
 */
@Entity
@Table(name = "bonding_trainer")
public class BondingTrainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalDate endTime;

    @OneToMany(mappedBy = "bondingTrainer")
    @JsonIgnoreProperties(value = { "bondingTrainer" }, allowSetters = true)
    private Set<BoundingSchedule> boundingSchedules = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bondingTrainers" }, allowSetters = true)
    private Year year;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "areaTrainers", "bondingTrainers", "schedules", "customer" }, allowSetters = true)
    private Trainer trainer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bondingTrainers" }, allowSetters = true)
    private Bonding bonding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BondingTrainer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartTime() {
        return this.startTime;
    }

    public BondingTrainer startTime(LocalDate startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return this.endTime;
    }

    public BondingTrainer endTime(LocalDate endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Set<BoundingSchedule> getBoundingSchedules() {
        return this.boundingSchedules;
    }

    public void setBoundingSchedules(Set<BoundingSchedule> boundingSchedules) {
        if (this.boundingSchedules != null) {
            this.boundingSchedules.forEach(i -> i.setBondingTrainer(null));
        }
        if (boundingSchedules != null) {
            boundingSchedules.forEach(i -> i.setBondingTrainer(this));
        }
        this.boundingSchedules = boundingSchedules;
    }

    public BondingTrainer boundingSchedules(Set<BoundingSchedule> boundingSchedules) {
        this.setBoundingSchedules(boundingSchedules);
        return this;
    }

    public BondingTrainer addBoundingSchedule(BoundingSchedule boundingSchedule) {
        this.boundingSchedules.add(boundingSchedule);
        boundingSchedule.setBondingTrainer(this);
        return this;
    }

    public BondingTrainer removeBoundingSchedule(BoundingSchedule boundingSchedule) {
        this.boundingSchedules.remove(boundingSchedule);
        boundingSchedule.setBondingTrainer(null);
        return this;
    }

    public Year getYear() {
        return this.year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public BondingTrainer year(Year year) {
        this.setYear(year);
        return this;
    }

    public Trainer getTrainer() {
        return this.trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public BondingTrainer trainer(Trainer trainer) {
        this.setTrainer(trainer);
        return this;
    }

    public Bonding getBonding() {
        return this.bonding;
    }

    public void setBonding(Bonding bonding) {
        this.bonding = bonding;
    }

    public BondingTrainer bonding(Bonding bonding) {
        this.setBonding(bonding);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BondingTrainer)) {
            return false;
        }
        return id != null && id.equals(((BondingTrainer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BondingTrainer{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
