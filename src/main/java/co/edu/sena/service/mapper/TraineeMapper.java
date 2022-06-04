package co.edu.sena.service.mapper;

import co.edu.sena.domain.Course;
import co.edu.sena.domain.Customer;
import co.edu.sena.domain.Trainee;
import co.edu.sena.domain.TrainingStatus;
import co.edu.sena.service.dto.CourseDTO;
import co.edu.sena.service.dto.CustomerDTO;
import co.edu.sena.service.dto.TraineeDTO;
import co.edu.sena.service.dto.TrainingStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trainee} and its DTO {@link TraineeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TraineeMapper extends EntityMapper<TraineeDTO, Trainee> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "trainingStatus", source = "trainingStatus", qualifiedByName = "trainingStatusStatusName")
    @Mapping(target = "course", source = "course", qualifiedByName = "courseId")
    TraineeDTO toDto(Trainee s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("trainingStatusStatusName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "statusName", source = "statusName")
    TrainingStatusDTO toDtoTrainingStatusStatusName(TrainingStatus trainingStatus);

    @Named("courseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CourseDTO toDtoCourseId(Course course);
}
