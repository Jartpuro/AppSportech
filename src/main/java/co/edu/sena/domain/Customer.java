package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "document_number", length = 50, nullable = false)
    private String documentNumber;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Size(max = 50)
    @Column(name = "second_name", length = 50)
    private String secondName;

    @NotNull
    @Size(max = 50)
    @Column(name = "fisrt_last_name", length = 50, nullable = false)
    private String fisrtLastName;

    @Size(max = 50)
    @Column(name = "second_last_name", length = 50)
    private String secondLastName;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer", "trainingStatus", "course" }, allowSetters = true)
    private Set<Trainee> trainees = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<LogError> logErrors = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<LogAudit> logAudits = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "areaTrainers", "bondingTrainers", "schedules", "customer" }, allowSetters = true)
    private Set<Trainer> trainers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "customers" }, allowSetters = true)
    private DocumentType documentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public Customer documentNumber(String documentNumber) {
        this.setDocumentNumber(documentNumber);
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Customer firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public Customer secondName(String secondName) {
        this.setSecondName(secondName);
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFisrtLastName() {
        return this.fisrtLastName;
    }

    public Customer fisrtLastName(String fisrtLastName) {
        this.setFisrtLastName(fisrtLastName);
        return this;
    }

    public void setFisrtLastName(String fisrtLastName) {
        this.fisrtLastName = fisrtLastName;
    }

    public String getSecondLastName() {
        return this.secondLastName;
    }

    public Customer secondLastName(String secondLastName) {
        this.setSecondLastName(secondLastName);
        return this;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Trainee> getTrainees() {
        return this.trainees;
    }

    public void setTrainees(Set<Trainee> trainees) {
        if (this.trainees != null) {
            this.trainees.forEach(i -> i.setCustomer(null));
        }
        if (trainees != null) {
            trainees.forEach(i -> i.setCustomer(this));
        }
        this.trainees = trainees;
    }

    public Customer trainees(Set<Trainee> trainees) {
        this.setTrainees(trainees);
        return this;
    }

    public Customer addTrainee(Trainee trainee) {
        this.trainees.add(trainee);
        trainee.setCustomer(this);
        return this;
    }

    public Customer removeTrainee(Trainee trainee) {
        this.trainees.remove(trainee);
        trainee.setCustomer(null);
        return this;
    }

    public Set<LogError> getLogErrors() {
        return this.logErrors;
    }

    public void setLogErrors(Set<LogError> logErrors) {
        if (this.logErrors != null) {
            this.logErrors.forEach(i -> i.setCustomer(null));
        }
        if (logErrors != null) {
            logErrors.forEach(i -> i.setCustomer(this));
        }
        this.logErrors = logErrors;
    }

    public Customer logErrors(Set<LogError> logErrors) {
        this.setLogErrors(logErrors);
        return this;
    }

    public Customer addLogError(LogError logError) {
        this.logErrors.add(logError);
        logError.setCustomer(this);
        return this;
    }

    public Customer removeLogError(LogError logError) {
        this.logErrors.remove(logError);
        logError.setCustomer(null);
        return this;
    }

    public Set<LogAudit> getLogAudits() {
        return this.logAudits;
    }

    public void setLogAudits(Set<LogAudit> logAudits) {
        if (this.logAudits != null) {
            this.logAudits.forEach(i -> i.setCustomer(null));
        }
        if (logAudits != null) {
            logAudits.forEach(i -> i.setCustomer(this));
        }
        this.logAudits = logAudits;
    }

    public Customer logAudits(Set<LogAudit> logAudits) {
        this.setLogAudits(logAudits);
        return this;
    }

    public Customer addLogAudit(LogAudit logAudit) {
        this.logAudits.add(logAudit);
        logAudit.setCustomer(this);
        return this;
    }

    public Customer removeLogAudit(LogAudit logAudit) {
        this.logAudits.remove(logAudit);
        logAudit.setCustomer(null);
        return this;
    }

    public Set<Trainer> getTrainers() {
        return this.trainers;
    }

    public void setTrainers(Set<Trainer> trainers) {
        if (this.trainers != null) {
            this.trainers.forEach(i -> i.setCustomer(null));
        }
        if (trainers != null) {
            trainers.forEach(i -> i.setCustomer(this));
        }
        this.trainers = trainers;
    }

    public Customer trainers(Set<Trainer> trainers) {
        this.setTrainers(trainers);
        return this;
    }

    public Customer addTrainer(Trainer trainer) {
        this.trainers.add(trainer);
        trainer.setCustomer(this);
        return this;
    }

    public Customer removeTrainer(Trainer trainer) {
        this.trainers.remove(trainer);
        trainer.setCustomer(null);
        return this;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Customer documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", fisrtLastName='" + getFisrtLastName() + "'" +
            ", secondLastName='" + getSecondLastName() + "'" +
            "}";
    }
}
