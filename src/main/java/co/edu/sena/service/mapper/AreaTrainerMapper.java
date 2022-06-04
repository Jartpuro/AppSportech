package co.edu.sena.service.mapper;

import co.edu.sena.domain.Area;
import co.edu.sena.domain.AreaTrainer;
import co.edu.sena.domain.Trainer;
import co.edu.sena.service.dto.AreaDTO;
import co.edu.sena.service.dto.AreaTrainerDTO;
import co.edu.sena.service.dto.TrainerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaTrainer} and its DTO {@link AreaTrainerDTO}.
 */
@Mapper(componentModel = "spring")
public interface AreaTrainerMapper extends EntityMapper<AreaTrainerDTO, AreaTrainer> {
    @Mapping(target = "area", source = "area", qualifiedByName = "areaAreaName")
    @Mapping(target = "trainer", source = "trainer", qualifiedByName = "trainerId")
    AreaTrainerDTO toDto(AreaTrainer s);

    @Named("areaAreaName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "areaName", source = "areaName")
    AreaDTO toDtoAreaAreaName(Area area);

    @Named("trainerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrainerDTO toDtoTrainerId(Trainer trainer);
}
