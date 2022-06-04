package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LogAudit.
 */
@Entity
@Table(name = "log_audit")
public class LogAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 400)
    @Column(name = "level_audit", length = 400, nullable = false)
    private String levelAudit;

    @NotNull
    @Size(max = 400)
    @Column(name = "log_name", length = 400, nullable = false)
    private String logName;

    @NotNull
    @Size(max = 400)
    @Column(name = "message_audit", length = 400, nullable = false)
    private String messageAudit;

    @NotNull
    @Column(name = "date_audit", nullable = false)
    private ZonedDateTime dateAudit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "trainees", "logErrors", "logAudits", "trainers", "documentType" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LogAudit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelAudit() {
        return this.levelAudit;
    }

    public LogAudit levelAudit(String levelAudit) {
        this.setLevelAudit(levelAudit);
        return this;
    }

    public void setLevelAudit(String levelAudit) {
        this.levelAudit = levelAudit;
    }

    public String getLogName() {
        return this.logName;
    }

    public LogAudit logName(String logName) {
        this.setLogName(logName);
        return this;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getMessageAudit() {
        return this.messageAudit;
    }

    public LogAudit messageAudit(String messageAudit) {
        this.setMessageAudit(messageAudit);
        return this;
    }

    public void setMessageAudit(String messageAudit) {
        this.messageAudit = messageAudit;
    }

    public ZonedDateTime getDateAudit() {
        return this.dateAudit;
    }

    public LogAudit dateAudit(ZonedDateTime dateAudit) {
        this.setDateAudit(dateAudit);
        return this;
    }

    public void setDateAudit(ZonedDateTime dateAudit) {
        this.dateAudit = dateAudit;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LogAudit customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogAudit)) {
            return false;
        }
        return id != null && id.equals(((LogAudit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogAudit{" +
            "id=" + getId() +
            ", levelAudit='" + getLevelAudit() + "'" +
            ", logName='" + getLogName() + "'" +
            ", messageAudit='" + getMessageAudit() + "'" +
            ", dateAudit='" + getDateAudit() + "'" +
            "}";
    }
}
