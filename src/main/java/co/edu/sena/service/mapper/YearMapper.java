package co.edu.sena.service.mapper;

import co.edu.sena.domain.Year;
import co.edu.sena.service.dto.YearDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Year} and its DTO {@link YearDTO}.
 */
@Mapper(componentModel = "spring")
public interface YearMapper extends EntityMapper<YearDTO, Year> {}
