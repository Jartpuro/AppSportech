package co.edu.sena.service.mapper;

import co.edu.sena.domain.TrainingStatus;
import co.edu.sena.service.dto.TrainingStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrainingStatus} and its DTO {@link TrainingStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainingStatusMapper extends EntityMapper<TrainingStatusDTO, TrainingStatus> {}
