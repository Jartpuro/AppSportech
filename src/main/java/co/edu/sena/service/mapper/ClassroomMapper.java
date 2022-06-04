package co.edu.sena.service.mapper;

import co.edu.sena.domain.Campus;
import co.edu.sena.domain.Classroom;
import co.edu.sena.domain.ClassroomType;
import co.edu.sena.service.dto.CampusDTO;
import co.edu.sena.service.dto.ClassroomDTO;
import co.edu.sena.service.dto.ClassroomTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classroom} and its DTO {@link ClassroomDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassroomMapper extends EntityMapper<ClassroomDTO, Classroom> {
    @Mapping(target = "classroomType", source = "classroomType", qualifiedByName = "classroomTypeTypeClassroom")
    @Mapping(target = "campus", source = "campus", qualifiedByName = "campusCampusName")
    ClassroomDTO toDto(Classroom s);

    @Named("classroomTypeTypeClassroom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "typeClassroom", source = "typeClassroom")
    ClassroomTypeDTO toDtoClassroomTypeTypeClassroom(ClassroomType classroomType);

    @Named("campusCampusName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "campusName", source = "campusName")
    CampusDTO toDtoCampusCampusName(Campus campus);
}
