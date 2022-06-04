package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Year.
 */
@Entity
@Table(name = "year")
public class Year implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "year_number", nullable = false, unique = true)
    private Integer yearNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "year_state", nullable = false)
    private State yearState;

    @OneToMany(mappedBy = "year")
    @JsonIgnoreProperties(value = { "boundingSchedules", "year", "trainer", "bonding" }, allowSetters = true)
    private Set<BondingTrainer> bondingTrainers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Year id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYearNumber() {
        return this.yearNumber;
    }

    public Year yearNumber(Integer yearNumber) {
        this.setYearNumber(yearNumber);
        return this;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
    }

    public State getYearState() {
        return this.yearState;
    }

    public Year yearState(State yearState) {
        this.setYearState(yearState);
        return this;
    }

    public void setYearState(State yearState) {
        this.yearState = yearState;
    }

    public Set<BondingTrainer> getBondingTrainers() {
        return this.bondingTrainers;
    }

    public void setBondingTrainers(Set<BondingTrainer> bondingTrainers) {
        if (this.bondingTrainers != null) {
            this.bondingTrainers.forEach(i -> i.setYear(null));
        }
        if (bondingTrainers != null) {
            bondingTrainers.forEach(i -> i.setYear(this));
        }
        this.bondingTrainers = bondingTrainers;
    }

    public Year bondingTrainers(Set<BondingTrainer> bondingTrainers) {
        this.setBondingTrainers(bondingTrainers);
        return this;
    }

    public Year addBondingTrainer(BondingTrainer bondingTrainer) {
        this.bondingTrainers.add(bondingTrainer);
        bondingTrainer.setYear(this);
        return this;
    }

    public Year removeBondingTrainer(BondingTrainer bondingTrainer) {
        this.bondingTrainers.remove(bondingTrainer);
        bondingTrainer.setYear(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Year)) {
            return false;
        }
        return id != null && id.equals(((Year) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Year{" +
            "id=" + getId() +
            ", yearNumber=" + getYearNumber() +
            ", yearState='" + getYearState() + "'" +
            "}";
    }
}
