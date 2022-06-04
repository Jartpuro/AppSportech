package co.edu.sena.service.mapper;

import co.edu.sena.domain.Course;
import co.edu.sena.domain.CourseStatus;
import co.edu.sena.domain.TrainingProgram;
import co.edu.sena.service.dto.CourseDTO;
import co.edu.sena.service.dto.CourseStatusDTO;
import co.edu.sena.service.dto.TrainingProgramDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {
    @Mapping(target = "courseStatus", source = "courseStatus", qualifiedByName = "courseStatusNameCourseStatus")
    @Mapping(target = "trainingProgram", source = "trainingProgram", qualifiedByName = "trainingProgramId")
    CourseDTO toDto(Course s);

    @Named("courseStatusNameCourseStatus")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nameCourseStatus", source = "nameCourseStatus")
    CourseStatusDTO toDtoCourseStatusNameCourseStatus(CourseStatus courseStatus);

    @Named("trainingProgramId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrainingProgramDTO toDtoTrainingProgramId(TrainingProgram trainingProgram);
}
