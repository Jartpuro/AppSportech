package co.edu.sena.service.mapper;

import co.edu.sena.domain.Customer;
import co.edu.sena.domain.Trainer;
import co.edu.sena.service.dto.CustomerDTO;
import co.edu.sena.service.dto.TrainerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trainer} and its DTO {@link TrainerDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainerMapper extends EntityMapper<TrainerDTO, Trainer> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    TrainerDTO toDto(Trainer s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
