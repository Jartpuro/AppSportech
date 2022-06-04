package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BoundingSchedule.
 */
@Entity
@Table(name = "bounding_schedule")
public class BoundingSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "boundingSchedules", "year", "trainer", "bonding" }, allowSetters = true)
    private BondingTrainer bondingTrainer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BoundingSchedule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BondingTrainer getBondingTrainer() {
        return this.bondingTrainer;
    }

    public void setBondingTrainer(BondingTrainer bondingTrainer) {
        this.bondingTrainer = bondingTrainer;
    }

    public BoundingSchedule bondingTrainer(BondingTrainer bondingTrainer) {
        this.setBondingTrainer(bondingTrainer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoundingSchedule)) {
            return false;
        }
        return id != null && id.equals(((BoundingSchedule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoundingSchedule{" +
            "id=" + getId() +
            "}";
    }
}
