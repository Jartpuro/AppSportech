package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Bonding.
 */
@Entity
@Table(name = "bonding")
public class Bonding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "bonding_type", length = 40, nullable = false, unique = true)
    private String bondingType;

    @NotNull
    @Column(name = "working_hours", nullable = false)
    private Integer workingHours;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bonding_state", nullable = false)
    private State bondingState;

    @OneToMany(mappedBy = "bonding")
    @JsonIgnoreProperties(value = { "boundingSchedules", "year", "trainer", "bonding" }, allowSetters = true)
    private Set<BondingTrainer> bondingTrainers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bonding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBondingType() {
        return this.bondingType;
    }

    public Bonding bondingType(String bondingType) {
        this.setBondingType(bondingType);
        return this;
    }

    public void setBondingType(String bondingType) {
        this.bondingType = bondingType;
    }

    public Integer getWorkingHours() {
        return this.workingHours;
    }

    public Bonding workingHours(Integer workingHours) {
        this.setWorkingHours(workingHours);
        return this;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public State getBondingState() {
        return this.bondingState;
    }

    public Bonding bondingState(State bondingState) {
        this.setBondingState(bondingState);
        return this;
    }

    public void setBondingState(State bondingState) {
        this.bondingState = bondingState;
    }

    public Set<BondingTrainer> getBondingTrainers() {
        return this.bondingTrainers;
    }

    public void setBondingTrainers(Set<BondingTrainer> bondingTrainers) {
        if (this.bondingTrainers != null) {
            this.bondingTrainers.forEach(i -> i.setBonding(null));
        }
        if (bondingTrainers != null) {
            bondingTrainers.forEach(i -> i.setBonding(this));
        }
        this.bondingTrainers = bondingTrainers;
    }

    public Bonding bondingTrainers(Set<BondingTrainer> bondingTrainers) {
        this.setBondingTrainers(bondingTrainers);
        return this;
    }

    public Bonding addBondingTrainer(BondingTrainer bondingTrainer) {
        this.bondingTrainers.add(bondingTrainer);
        bondingTrainer.setBonding(this);
        return this;
    }

    public Bonding removeBondingTrainer(BondingTrainer bondingTrainer) {
        this.bondingTrainers.remove(bondingTrainer);
        bondingTrainer.setBonding(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bonding)) {
            return false;
        }
        return id != null && id.equals(((Bonding) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bonding{" +
            "id=" + getId() +
            ", bondingType='" + getBondingType() + "'" +
            ", workingHours=" + getWorkingHours() +
            ", bondingState='" + getBondingState() + "'" +
            "}";
    }
}
