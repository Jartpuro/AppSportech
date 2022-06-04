package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TrainingStatus.
 */
@Entity
@Table(name = "training_status")
public class TrainingStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "status_name", length = 40, nullable = false, unique = true)
    private String statusName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state_training", nullable = false)
    private State stateTraining;

    @OneToMany(mappedBy = "trainingStatus")
    @JsonIgnoreProperties(value = { "customer", "trainingStatus", "course" }, allowSetters = true)
    private Set<Trainee> trainees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrainingStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public TrainingStatus statusName(String statusName) {
        this.setStatusName(statusName);
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public State getStateTraining() {
        return this.stateTraining;
    }

    public TrainingStatus stateTraining(State stateTraining) {
        this.setStateTraining(stateTraining);
        return this;
    }

    public void setStateTraining(State stateTraining) {
        this.stateTraining = stateTraining;
    }

    public Set<Trainee> getTrainees() {
        return this.trainees;
    }

    public void setTrainees(Set<Trainee> trainees) {
        if (this.trainees != null) {
            this.trainees.forEach(i -> i.setTrainingStatus(null));
        }
        if (trainees != null) {
            trainees.forEach(i -> i.setTrainingStatus(this));
        }
        this.trainees = trainees;
    }

    public TrainingStatus trainees(Set<Trainee> trainees) {
        this.setTrainees(trainees);
        return this;
    }

    public TrainingStatus addTrainee(Trainee trainee) {
        this.trainees.add(trainee);
        trainee.setTrainingStatus(this);
        return this;
    }

    public TrainingStatus removeTrainee(Trainee trainee) {
        this.trainees.remove(trainee);
        trainee.setTrainingStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainingStatus)) {
            return false;
        }
        return id != null && id.equals(((TrainingStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingStatus{" +
            "id=" + getId() +
            ", statusName='" + getStatusName() + "'" +
            ", stateTraining='" + getStateTraining() + "'" +
            "}";
    }
}
