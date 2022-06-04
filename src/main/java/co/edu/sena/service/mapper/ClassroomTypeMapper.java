package co.edu.sena.service.mapper;

import co.edu.sena.domain.ClassroomType;
import co.edu.sena.service.dto.ClassroomTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassroomType} and its DTO {@link ClassroomTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassroomTypeMapper extends EntityMapper<ClassroomTypeDTO, ClassroomType> {}
