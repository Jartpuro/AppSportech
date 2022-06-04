package co.edu.sena.service.mapper;

import co.edu.sena.domain.Customer;
import co.edu.sena.domain.LogError;
import co.edu.sena.service.dto.CustomerDTO;
import co.edu.sena.service.dto.LogErrorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LogError} and its DTO {@link LogErrorDTO}.
 */
@Mapper(componentModel = "spring")
public interface LogErrorMapper extends EntityMapper<LogErrorDTO, LogError> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    LogErrorDTO toDto(LogError s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
