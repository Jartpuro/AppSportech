package co.edu.sena.service.mapper;

import co.edu.sena.domain.Course;
import co.edu.sena.domain.CourseModule;
import co.edu.sena.domain.Module;
import co.edu.sena.service.dto.CourseDTO;
import co.edu.sena.service.dto.CourseModuleDTO;
import co.edu.sena.service.dto.ModuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseModule} and its DTO {@link CourseModuleDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseModuleMapper extends EntityMapper<CourseModuleDTO, CourseModule> {
    @Mapping(target = "course", source = "course", qualifiedByName = "courseCourseNumber")
    @Mapping(target = "module", source = "module", qualifiedByName = "moduleId")
    CourseModuleDTO toDto(CourseModule s);

    @Named("courseCourseNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "courseNumber", source = "courseNumber")
    CourseDTO toDtoCourseCourseNumber(Course course);

    @Named("moduleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModuleDTO toDtoModuleId(Module module);
}
