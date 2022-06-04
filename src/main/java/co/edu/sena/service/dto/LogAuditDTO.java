package co.edu.sena.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.LogAudit} entity.
 */
public class LogAuditDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 400)
    private String levelAudit;

    @NotNull
    @Size(max = 400)
    private String logName;

    @NotNull
    @Size(max = 400)
    private String messageAudit;

    @NotNull
    private ZonedDateTime dateAudit;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelAudit() {
        return levelAudit;
    }

    public void setLevelAudit(String levelAudit) {
        this.levelAudit = levelAudit;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getMessageAudit() {
        return messageAudit;
    }

    public void setMessageAudit(String messageAudit) {
        this.messageAudit = messageAudit;
    }

    public ZonedDateTime getDateAudit() {
        return dateAudit;
    }

    public void setDateAudit(ZonedDateTime dateAudit) {
        this.dateAudit = dateAudit;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogAuditDTO)) {
            return false;
        }

        LogAuditDTO logAuditDTO = (LogAuditDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, logAuditDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogAuditDTO{" +
            "id=" + getId() +
            ", levelAudit='" + getLevelAudit() + "'" +
            ", logName='" + getLogName() + "'" +
            ", messageAudit='" + getMessageAudit() + "'" +
            ", dateAudit='" + getDateAudit() + "'" +
            ", customer=" + getCustomer() +
            "}";
    }
}
