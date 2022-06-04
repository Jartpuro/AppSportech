package co.edu.sena.service.mapper;

import co.edu.sena.domain.BondingTrainer;
import co.edu.sena.domain.BoundingSchedule;
import co.edu.sena.service.dto.BondingTrainerDTO;
import co.edu.sena.service.dto.BoundingScheduleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BoundingSchedule} and its DTO {@link BoundingScheduleDTO}.
 */
@Mapper(componentModel = "spring")
public interface BoundingScheduleMapper extends EntityMapper<BoundingScheduleDTO, BoundingSchedule> {
    @Mapping(target = "bondingTrainer", source = "bondingTrainer", qualifiedByName = "bondingTrainerId")
    BoundingScheduleDTO toDto(BoundingSchedule s);

    @Named("bondingTrainerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BondingTrainerDTO toDtoBondingTrainerId(BondingTrainer bondingTrainer);
}
