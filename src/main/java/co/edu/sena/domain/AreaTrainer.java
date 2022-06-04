package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A AreaTrainer.
 */
@Entity
@Table(name = "area_trainer")
public class AreaTrainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "area_trainer_state", nullable = false)
    private State areaTrainerState;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "areaTrainers" }, allowSetters = true)
    private Area area;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "areaTrainers", "bondingTrainers", "schedules", "customer" }, allowSetters = true)
    private Trainer trainer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AreaTrainer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getAreaTrainerState() {
        return this.areaTrainerState;
    }

    public AreaTrainer areaTrainerState(State areaTrainerState) {
        this.setAreaTrainerState(areaTrainerState);
        return this;
    }

    public void setAreaTrainerState(State areaTrainerState) {
        this.areaTrainerState = areaTrainerState;
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public AreaTrainer area(Area area) {
        this.setArea(area);
        return this;
    }

    public Trainer getTrainer() {
        return this.trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public AreaTrainer trainer(Trainer trainer) {
        this.setTrainer(trainer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaTrainer)) {
            return false;
        }
        return id != null && id.equals(((AreaTrainer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaTrainer{" +
            "id=" + getId() +
            ", areaTrainerState='" + getAreaTrainerState() + "'" +
            "}";
    }
}
