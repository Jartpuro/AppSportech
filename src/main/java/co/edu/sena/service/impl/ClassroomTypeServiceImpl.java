package co.edu.sena.service.impl;

import co.edu.sena.domain.ClassroomType;
import co.edu.sena.repository.ClassroomTypeRepository;
import co.edu.sena.service.ClassroomTypeService;
import co.edu.sena.service.dto.ClassroomTypeDTO;
import co.edu.sena.service.mapper.ClassroomTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClassroomType}.
 */
@Service
@Transactional
public class ClassroomTypeServiceImpl implements ClassroomTypeService {

    private final Logger log = LoggerFactory.getLogger(ClassroomTypeServiceImpl.class);

    private final ClassroomTypeRepository classroomTypeRepository;

    private final ClassroomTypeMapper classroomTypeMapper;

    public ClassroomTypeServiceImpl(ClassroomTypeRepository classroomTypeRepository, ClassroomTypeMapper classroomTypeMapper) {
        this.classroomTypeRepository = classroomTypeRepository;
        this.classroomTypeMapper = classroomTypeMapper;
    }

    @Override
    public ClassroomTypeDTO save(ClassroomTypeDTO classroomTypeDTO) {
        log.debug("Request to save ClassroomType : {}", classroomTypeDTO);
        ClassroomType classroomType = classroomTypeMapper.toEntity(classroomTypeDTO);
        classroomType = classroomTypeRepository.save(classroomType);
        return classroomTypeMapper.toDto(classroomType);
    }

    @Override
    public ClassroomTypeDTO update(ClassroomTypeDTO classroomTypeDTO) {
        log.debug("Request to save ClassroomType : {}", classroomTypeDTO);
        ClassroomType classroomType = classroomTypeMapper.toEntity(classroomTypeDTO);
        classroomType = classroomTypeRepository.save(classroomType);
        return classroomTypeMapper.toDto(classroomType);
    }

    @Override
    public Optional<ClassroomTypeDTO> partialUpdate(ClassroomTypeDTO classroomTypeDTO) {
        log.debug("Request to partially update ClassroomType : {}", classroomTypeDTO);

        return classroomTypeRepository
            .findById(classroomTypeDTO.getId())
            .map(existingClassroomType -> {
                classroomTypeMapper.partialUpdate(existingClassroomType, classroomTypeDTO);

                return existingClassroomType;
            })
            .map(classroomTypeRepository::save)
            .map(classroomTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClassroomTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassroomTypes");
        return classroomTypeRepository.findAll(pageable).map(classroomTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClassroomTypeDTO> findOne(Long id) {
        log.debug("Request to get ClassroomType : {}", id);
        return classroomTypeRepository.findById(id).map(classroomTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassroomType : {}", id);
        classroomTypeRepository.deleteById(id);
    }
}
