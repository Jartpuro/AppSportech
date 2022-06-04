package co.edu.sena.service.mapper;

import co.edu.sena.domain.Customer;
import co.edu.sena.domain.LogAudit;
import co.edu.sena.service.dto.CustomerDTO;
import co.edu.sena.service.dto.LogAuditDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LogAudit} and its DTO {@link LogAuditDTO}.
 */
@Mapper(componentModel = "spring")
public interface LogAuditMapper extends EntityMapper<LogAuditDTO, LogAudit> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    LogAuditDTO toDto(LogAudit s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
