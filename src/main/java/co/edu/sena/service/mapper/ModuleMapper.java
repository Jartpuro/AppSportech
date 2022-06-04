package co.edu.sena.service.mapper;

import co.edu.sena.domain.Module;
import co.edu.sena.service.dto.ModuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Module} and its DTO {@link ModuleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModuleMapper extends EntityMapper<ModuleDTO, Module> {}
