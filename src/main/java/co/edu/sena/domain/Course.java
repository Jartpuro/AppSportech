package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "course_number", length = 100, nullable = false, unique = true)
    private String courseNumber;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Size(max = 40)
    @Column(name = "route", length = 40, nullable = false)
    private String route;

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties(value = { "customer", "trainingStatus", "course" }, allowSetters = true)
    private Set<Trainee> trainees = new HashSet<>();

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties(value = { "schedules", "course", "module" }, allowSetters = true)
    private Set<CourseModule> courseModules = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "courses" }, allowSetters = true)
    private CourseStatus courseStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "courses" }, allowSetters = true)
    private TrainingProgram trainingProgram;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseNumber() {
        return this.courseNumber;
    }

    public Course courseNumber(String courseNumber) {
        this.setCourseNumber(courseNumber);
        return this;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Course startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Course endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRoute() {
        return this.route;
    }

    public Course route(String route) {
        this.setRoute(route);
        return this;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Set<Trainee> getTrainees() {
        return this.trainees;
    }

    public void setTrainees(Set<Trainee> trainees) {
        if (this.trainees != null) {
            this.trainees.forEach(i -> i.setCourse(null));
        }
        if (trainees != null) {
            trainees.forEach(i -> i.setCourse(this));
        }
        this.trainees = trainees;
    }

    public Course trainees(Set<Trainee> trainees) {
        this.setTrainees(trainees);
        return this;
    }

    public Course addTrainee(Trainee trainee) {
        this.trainees.add(trainee);
        trainee.setCourse(this);
        return this;
    }

    public Course removeTrainee(Trainee trainee) {
        this.trainees.remove(trainee);
        trainee.setCourse(null);
        return this;
    }

    public Set<CourseModule> getCourseModules() {
        return this.courseModules;
    }

    public void setCourseModules(Set<CourseModule> courseModules) {
        if (this.courseModules != null) {
            this.courseModules.forEach(i -> i.setCourse(null));
        }
        if (courseModules != null) {
            courseModules.forEach(i -> i.setCourse(this));
        }
        this.courseModules = courseModules;
    }

    public Course courseModules(Set<CourseModule> courseModules) {
        this.setCourseModules(courseModules);
        return this;
    }

    public Course addCourseModule(CourseModule courseModule) {
        this.courseModules.add(courseModule);
        courseModule.setCourse(this);
        return this;
    }

    public Course removeCourseModule(CourseModule courseModule) {
        this.courseModules.remove(courseModule);
        courseModule.setCourse(null);
        return this;
    }

    public CourseStatus getCourseStatus() {
        return this.courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Course courseStatus(CourseStatus courseStatus) {
        this.setCourseStatus(courseStatus);
        return this;
    }

    public TrainingProgram getTrainingProgram() {
        return this.trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public Course trainingProgram(TrainingProgram trainingProgram) {
        this.setTrainingProgram(trainingProgram);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseNumber='" + getCourseNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", route='" + getRoute() + "'" +
            "}";
    }
}
