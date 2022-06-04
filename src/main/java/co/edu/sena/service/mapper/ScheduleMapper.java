package co.edu.sena.service.mapper;

import co.edu.sena.domain.Classroom;
import co.edu.sena.domain.CourseModule;
import co.edu.sena.domain.Day;
import co.edu.sena.domain.Modality;
import co.edu.sena.domain.Schedule;
import co.edu.sena.domain.ScheduleVersion;
import co.edu.sena.domain.Trainer;
import co.edu.sena.service.dto.ClassroomDTO;
import co.edu.sena.service.dto.CourseModuleDTO;
import co.edu.sena.service.dto.DayDTO;
import co.edu.sena.service.dto.ModalityDTO;
import co.edu.sena.service.dto.ScheduleDTO;
import co.edu.sena.service.dto.ScheduleVersionDTO;
import co.edu.sena.service.dto.TrainerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Schedule} and its DTO {@link ScheduleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ScheduleMapper extends EntityMapper<ScheduleDTO, Schedule> {
    @Mapping(target = "scheduleVersion", source = "scheduleVersion", qualifiedByName = "scheduleVersionId")
    @Mapping(target = "modality", source = "modality", qualifiedByName = "modalityModalityName")
    @Mapping(target = "day", source = "day", qualifiedByName = "dayDayName")
    @Mapping(target = "courseModule", source = "courseModule", qualifiedByName = "courseModuleId")
    @Mapping(target = "classroom", source = "classroom", qualifiedByName = "classroomId")
    @Mapping(target = "trainer", source = "trainer", qualifiedByName = "trainerId")
    ScheduleDTO toDto(Schedule s);

    @Named("scheduleVersionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ScheduleVersionDTO toDtoScheduleVersionId(ScheduleVersion scheduleVersion);

    @Named("modalityModalityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "modalityName", source = "modalityName")
    ModalityDTO toDtoModalityModalityName(Modality modality);

    @Named("dayDayName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "dayName", source = "dayName")
    DayDTO toDtoDayDayName(Day day);

    @Named("courseModuleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseModuleDTO toDtoCourseModuleId(CourseModule courseModule);

    @Named("classroomId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClassroomDTO toDtoClassroomId(Classroom classroom);

    @Named("trainerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrainerDTO toDtoTrainerId(Trainer trainer);
}
