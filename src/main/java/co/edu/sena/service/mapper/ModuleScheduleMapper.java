package co.edu.sena.service.mapper;

import co.edu.sena.domain.Module;
import co.edu.sena.domain.ModuleSchedule;
import co.edu.sena.service.dto.ModuleDTO;
import co.edu.sena.service.dto.ModuleScheduleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModuleSchedule} and its DTO {@link ModuleScheduleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModuleScheduleMapper extends EntityMapper<ModuleScheduleDTO, ModuleSchedule> {
    @Mapping(target = "module", source = "module", qualifiedByName = "moduleId")
    ModuleScheduleDTO toDto(ModuleSchedule s);

    @Named("moduleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModuleDTO toDtoModuleId(Module module);
}
