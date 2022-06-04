package co.edu.sena.service.mapper;

import co.edu.sena.domain.TrainingProgram;
import co.edu.sena.service.dto.TrainingProgramDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrainingProgram} and its DTO {@link TrainingProgramDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainingProgramMapper extends EntityMapper<TrainingProgramDTO, TrainingProgram> {}
