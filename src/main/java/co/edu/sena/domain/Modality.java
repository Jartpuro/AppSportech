package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Modality.
 */
@Entity
@Table(name = "modality")
public class Modality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "modality_name", length = 40, nullable = false, unique = true)
    private String modalityName;

    @NotNull
    @Size(max = 50)
    @Column(name = "modality_color", length = 50, nullable = false)
    private String modalityColor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modality_state", nullable = false)
    private State modalityState;

    @OneToMany(mappedBy = "modality")
    @JsonIgnoreProperties(value = { "scheduleVersion", "modality", "day", "courseModule", "classroom", "trainer" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Modality id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModalityName() {
        return this.modalityName;
    }

    public Modality modalityName(String modalityName) {
        this.setModalityName(modalityName);
        return this;
    }

    public void setModalityName(String modalityName) {
        this.modalityName = modalityName;
    }

    public String getModalityColor() {
        return this.modalityColor;
    }

    public Modality modalityColor(String modalityColor) {
        this.setModalityColor(modalityColor);
        return this;
    }

    public void setModalityColor(String modalityColor) {
        this.modalityColor = modalityColor;
    }

    public State getModalityState() {
        return this.modalityState;
    }

    public Modality modalityState(State modalityState) {
        this.setModalityState(modalityState);
        return this;
    }

    public void setModalityState(State modalityState) {
        this.modalityState = modalityState;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.setModality(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setModality(this));
        }
        this.schedules = schedules;
    }

    public Modality schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public Modality addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setModality(this);
        return this;
    }

    public Modality removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setModality(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Modality)) {
            return false;
        }
        return id != null && id.equals(((Modality) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Modality{" +
            "id=" + getId() +
            ", modalityName='" + getModalityName() + "'" +
            ", modalityColor='" + getModalityColor() + "'" +
            ", modalityState='" + getModalityState() + "'" +
            "}";
    }
}
