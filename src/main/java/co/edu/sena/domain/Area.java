package co.edu.sena.domain;

import co.edu.sena.domain.enumeration.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Area.
 */
@Entity
@Table(name = "area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "area_name", length = 200, nullable = false, unique = true)
    private String areaName;

    @Size(max = 1000)
    @Column(name = "url_logo", length = 1000)
    private String urlLogo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "area_state", nullable = false)
    private State areaState;

    @OneToMany(mappedBy = "area")
    @JsonIgnoreProperties(value = { "area", "trainer" }, allowSetters = true)
    private Set<AreaTrainer> areaTrainers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Area id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public Area areaName(String areaName) {
        this.setAreaName(areaName);
        return this;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUrlLogo() {
        return this.urlLogo;
    }

    public Area urlLogo(String urlLogo) {
        this.setUrlLogo(urlLogo);
        return this;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public State getAreaState() {
        return this.areaState;
    }

    public Area areaState(State areaState) {
        this.setAreaState(areaState);
        return this;
    }

    public void setAreaState(State areaState) {
        this.areaState = areaState;
    }

    public Set<AreaTrainer> getAreaTrainers() {
        return this.areaTrainers;
    }

    public void setAreaTrainers(Set<AreaTrainer> areaTrainers) {
        if (this.areaTrainers != null) {
            this.areaTrainers.forEach(i -> i.setArea(null));
        }
        if (areaTrainers != null) {
            areaTrainers.forEach(i -> i.setArea(this));
        }
        this.areaTrainers = areaTrainers;
    }

    public Area areaTrainers(Set<AreaTrainer> areaTrainers) {
        this.setAreaTrainers(areaTrainers);
        return this;
    }

    public Area addAreaTrainer(AreaTrainer areaTrainer) {
        this.areaTrainers.add(areaTrainer);
        areaTrainer.setArea(this);
        return this;
    }

    public Area removeAreaTrainer(AreaTrainer areaTrainer) {
        this.areaTrainers.remove(areaTrainer);
        areaTrainer.setArea(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Area)) {
            return false;
        }
        return id != null && id.equals(((Area) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Area{" +
            "id=" + getId() +
            ", areaName='" + getAreaName() + "'" +
            ", urlLogo='" + getUrlLogo() + "'" +
            ", areaState='" + getAreaState() + "'" +
            "}";
    }
}
