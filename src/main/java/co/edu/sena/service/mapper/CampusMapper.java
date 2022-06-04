package co.edu.sena.service.mapper;

import co.edu.sena.domain.Campus;
import co.edu.sena.service.dto.CampusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campus} and its DTO {@link CampusDTO}.
 */
@Mapper(componentModel = "spring")
public interface CampusMapper extends EntityMapper<CampusDTO, Campus> {}
