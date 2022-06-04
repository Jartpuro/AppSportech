package co.edu.sena.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.LogError} entity.
 */
public class LogErrorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 400)
    private String levelError;

    @NotNull
    @Size(max = 400)
    private String logName;

    @NotNull
    @Size(max = 400)
    private String messageError;

    @NotNull
    private ZonedDateTime dateError;

    private CustomerDTO customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelError() {
        return levelError;
    }

    public void setLevelError(String levelError) {
        this.levelError = levelError;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public ZonedDateTime getDateError() {
        return dateError;
    }

    public void setDateError(ZonedDateTime dateError) {
        this.dateError = dateError;
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
        if (!(o instanceof LogErrorDTO)) {
            return false;
        }

        LogErrorDTO logErrorDTO = (LogErrorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, logErrorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogErrorDTO{" +
            "id=" + getId() +
            ", levelError='" + getLevelError() + "'" +
            ", logName='" + getLogName() + "'" +
            ", messageError='" + getMessageError() + "'" +
            ", dateError='" + getDateError() + "'" +
            ", customer=" + getCustomer() +
            "}";
    }
}
