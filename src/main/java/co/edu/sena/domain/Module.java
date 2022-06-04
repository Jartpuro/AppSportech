package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Module.
 */
@Entity
@Table(name = "module")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "module_name", nullable = false)
    private Integer moduleName;

    @Column(name = "module_state")
    private String moduleState;

    @OneToMany(mappedBy = "module")
    @JsonIgnoreProperties(value = { "schedules", "course", "module" }, allowSetters = true)
    private Set<CourseModule> courseModules = new HashSet<>();

    @OneToMany(mappedBy = "module")
    @JsonIgnoreProperties(value = { "module" }, allowSetters = true)
    private Set<ModuleSchedule> moduleSchedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Module id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getModuleName() {
        return this.moduleName;
    }

    public Module moduleName(Integer moduleName) {
        this.setModuleName(moduleName);
        return this;
    }

    public void setModuleName(Integer moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleState() {
        return this.moduleState;
    }

    public Module moduleState(String moduleState) {
        this.setModuleState(moduleState);
        return this;
    }

    public void setModuleState(String moduleState) {
        this.moduleState = moduleState;
    }

    public Set<CourseModule> getCourseModules() {
        return this.courseModules;
    }

    public void setCourseModules(Set<CourseModule> courseModules) {
        if (this.courseModules != null) {
            this.courseModules.forEach(i -> i.setModule(null));
        }
        if (courseModules != null) {
            courseModules.forEach(i -> i.setModule(this));
        }
        this.courseModules = courseModules;
    }

    public Module courseModules(Set<CourseModule> courseModules) {
        this.setCourseModules(courseModules);
        return this;
    }

    public Module addCourseModule(CourseModule courseModule) {
        this.courseModules.add(courseModule);
        courseModule.setModule(this);
        return this;
    }

    public Module removeCourseModule(CourseModule courseModule) {
        this.courseModules.remove(courseModule);
        courseModule.setModule(null);
        return this;
    }

    public Set<ModuleSchedule> getModuleSchedules() {
        return this.moduleSchedules;
    }

    public void setModuleSchedules(Set<ModuleSchedule> moduleSchedules) {
        if (this.moduleSchedules != null) {
            this.moduleSchedules.forEach(i -> i.setModule(null));
        }
        if (moduleSchedules != null) {
            moduleSchedules.forEach(i -> i.setModule(this));
        }
        this.moduleSchedules = moduleSchedules;
    }

    public Module moduleSchedules(Set<ModuleSchedule> moduleSchedules) {
        this.setModuleSchedules(moduleSchedules);
        return this;
    }

    public Module addModuleSchedule(ModuleSchedule moduleSchedule) {
        this.moduleSchedules.add(moduleSchedule);
        moduleSchedule.setModule(this);
        return this;
    }

    public Module removeModuleSchedule(ModuleSchedule moduleSchedule) {
        this.moduleSchedules.remove(moduleSchedule);
        moduleSchedule.setModule(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Module)) {
            return false;
        }
        return id != null && id.equals(((Module) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", moduleName=" + getModuleName() +
            ", moduleState='" + getModuleState() + "'" +
            "}";
    }
}
