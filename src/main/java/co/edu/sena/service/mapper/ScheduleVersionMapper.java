package co.edu.sena.service.mapper;

import co.edu.sena.domain.ScheduleVersion;
import co.edu.sena.service.dto.ScheduleVersionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScheduleVersion} and its DTO {@link ScheduleVersionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScheduleVersionMapper extends EntityMapper<ScheduleVersionDTO, ScheduleVersion> {}
