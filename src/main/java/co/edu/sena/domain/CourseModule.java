package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CourseModule.
 */
@Entity
@Table(name = "course_module")
public class CourseModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "courseModule")
    @JsonIgnoreProperties(value = { "scheduleVersion", "modality", "day", "courseModule", "classroom", "trainer" }, allowSetters = true)
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "trainees", "courseModules", "courseStatus", "trainingProgram" }, allowSetters = true)
    private Course course;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "courseModules", "moduleSchedules" }, allowSetters = true)
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourseModule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        if (this.schedules != null) {
            this.schedules.forEach(i -> i.setCourseModule(null));
        }
        if (schedules != null) {
            schedules.forEach(i -> i.setCourseModule(this));
        }
        this.schedules = schedules;
    }

    public CourseModule schedules(Set<Schedule> schedules) {
        this.setSchedules(schedules);
        return this;
    }

    public CourseModule addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setCourseModule(this);
        return this;
    }

    public CourseModule removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setCourseModule(null);
        return this;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseModule course(Course course) {
        this.setCourse(course);
        return this;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public CourseModule module(Module module) {
        this.setModule(module);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseModule)) {
            return false;
        }
        return id != null && id.equals(((CourseModule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseModule{" +
            "id=" + getId() +
            "}";
    }
}
