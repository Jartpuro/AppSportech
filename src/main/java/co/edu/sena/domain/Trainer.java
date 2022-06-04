package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Trainer.
 */
@Entity
@Table(name = "trainer")
public class Trainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trainer_state", nullable = false)
    private State trainerState;

    @OneToMany(mappedBy = "trainer")
    @JsonIgnoreProperties(value = { "area", "trainer" }, allowSetters = true)
    private Set<AreaTrainer> areaTrainers = new HashSet<>();

    @OneToMany(mappedBy = "trainer")
    @JsonIgnoreProperties(value = { "boundingSchedules", "year", "trainer", "bonding" }, allowSetters = true)
    private Set<BondingTrainer> bondingTrainers = new HashSet<>();

    @OneToMany(mappedBy = "trainer")
    @JsonIgnoreProperties(value = { "scheduleVersion", "modality", "day", "courseModule", "classroom", "trainer" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "trainees", "logErrors", "logAudits", "trainers", "documentType" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trainer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State getTrainerState() {
        return this.trainerState;
    }

    public Trainer trainerState(State trainerState) {
        this.setTrainerState(trainerState);
        return this;
    }

    public void setTrainerState(State trainerState) {
        this.trainerState = trainerState;
    }

    public Set<AreaTrainer> getAreaTrainers() {
        return this.areaTrainers;
    }

    public void setAreaTrainers(Set<AreaTrainer> areaTrainers) {
        if (this.areaTrainers != null) {
            this.areaTrainers.forEach(i -> i.setTrainer(null));
        }
        if (areaTrainers != null) {
            areaTrainers.forEach(i -> i.setTrainer(this));
        }
        this.areaTrainers = areaTrainers;
    }

    public Trainer areaTrainers(Set<AreaTrainer> areaTrainers) {
        this.setAreaTrainers(areaTrainers);
        return this;
    }

    public Trainer addAreaTrainer(AreaTrainer areaTrainer) {
        this.areaTrainers.add(areaTrainer);
        areaTrainer.setTrainer(this);
        return this;
    }

    public Trainer removeAreaTrainer(AreaTrainer areaTrainer) {
        this.areaTrainers.remove(areaTrainer);
        areaTrainer.setTrainer(null);
        return this;
    }

    public Set<BondingTrainer> getBondingTrainers() {
        return this.bondingTrainers;
    }

    public void setBondingTrainers(Set<BondingTrainer> bondingTrainers) {
        if (this.bondingTrainers != null) {
            this.bondingTrainers.forEach(i -> i.setTrainer(null));
        }
        if (bondingTrainers != null) {
            bondingTrainers.forEach(i -> i.setTrainer(this));
        }
        this.bondingTrainers = bondingTrainers;
    }

    public Trainer bondingTrainers(Set<BondingTrainer> bondingTrainers) {
        this.setBondingTrainers(bondingTrainers);
        return this;
    }

    public Trainer addBondingTrainer(BondingTrainer bondingTrainer) {
        this.bondingTrainers.add(bondingTrainer);
        bondingTrainer.setTrainer(this);
        return this;
    }

    public Trainer removeBondingTrainer(BondingTrainer bondingTrainer) {
        this.bondingTrainers.remove(bondingTrainer);
        bondingTrainer.setTrainer(null);
        return this;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.setTrainer(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setTrainer(this));
        }
        this.schedules = schedules;
    }

    public Trainer schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public Trainer addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setTrainer(this);
        return this;
    }

    public Trainer removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setTrainer(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Trainer customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trainer)) {
            return false;
        }
        return id != null && id.equals(((Trainer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trainer{" +
            "id=" + getId() +
            ", trainerState='" + getTrainerState() + "'" +
            "}";
    }
}
