package co.edu.sena.service.mapper;

import co.edu.sena.domain.Day;
import co.edu.sena.service.dto.DayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Day} and its DTO {@link DayDTO}.
 */
@Mapper(componentModel = "spring")
public interface DayMapper extends EntityMapper<DayDTO, Day> {}
