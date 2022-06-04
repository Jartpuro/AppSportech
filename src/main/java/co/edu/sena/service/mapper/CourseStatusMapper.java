package co.edu.sena.service.mapper;

import co.edu.sena.domain.CourseStatus;
import co.edu.sena.service.dto.CourseStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseStatus} and its DTO {@link CourseStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseStatusMapper extends EntityMapper<CourseStatusDTO, CourseStatus> {}
