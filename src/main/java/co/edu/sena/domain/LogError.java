package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A LogError.
 */
@Entity
@Table(name = "log_error")
public class LogError implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 400)
    @Column(name = "level_error", length = 400, nullable = false)
    private String levelError;

    @NotNull
    @Size(max = 400)
    @Column(name = "log_name", length = 400, nullable = false)
    private String logName;

    @NotNull
    @Size(max = 400)
    @Column(name = "message_error", length = 400, nullable = false)
    private String messageError;

    @NotNull
    @Column(name = "date_error", nullable = false)
    private ZonedDateTime dateError;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "trainees", "logErrors", "logAudits", "trainers", "documentType" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LogError id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelError() {
        return this.levelError;
    }

    public LogError levelError(String levelError) {
        this.setLevelError(levelError);
        return this;
    }

    public void setLevelError(String levelError) {
        this.levelError = levelError;
    }

    public String getLogName() {
        return this.logName;
    }

    public LogError logName(String logName) {
        this.setLogName(logName);
        return this;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getMessageError() {
        return this.messageError;
    }

    public LogError messageError(String messageError) {
        this.setMessageError(messageError);
        return this;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public ZonedDateTime getDateError() {
        return this.dateError;
    }

    public LogError dateError(ZonedDateTime dateError) {
        this.setDateError(dateError);
        return this;
    }

    public void setDateError(ZonedDateTime dateError) {
        this.dateError = dateError;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LogError customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogError)) {
            return false;
        }
        return id != null && id.equals(((LogError) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogError{" +
            "id=" + getId() +
            ", levelError='" + getLevelError() + "'" +
            ", logName='" + getLogName() + "'" +
            ", messageError='" + getMessageError() + "'" +
            ", dateError='" + getDateError() + "'" +
            "}";
    }
}
