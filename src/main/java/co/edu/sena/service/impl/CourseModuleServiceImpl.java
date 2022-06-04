package co.edu.sena.service.impl;

import co.edu.sena.domain.CourseModule;
import co.edu.sena.repository.CourseModuleRepository;
import co.edu.sena.service.CourseModuleService;
import co.edu.sena.service.dto.CourseModuleDTO;
import co.edu.sena.service.mapper.CourseModuleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CourseModule}.
 */
@Service
@Transactional
public class CourseModuleServiceImpl implements CourseModuleService {

    private final Logger log = LoggerFactory.getLogger(CourseModuleServiceImpl.class);

    private final CourseModuleRepository courseModuleRepository;

    private final CourseModuleMapper courseModuleMapper;

    public CourseModuleServiceImpl(CourseModuleRepository courseModuleRepository, CourseModuleMapper courseModuleMapper) {
        this.courseModuleRepository = courseModuleRepository;
        this.courseModuleMapper = courseModuleMapper;
    }

    @Override
    public CourseModuleDTO save(CourseModuleDTO courseModuleDTO) {
        log.debug("Request to save CourseModule : {}", courseModuleDTO);
        CourseModule courseModule = courseModuleMapper.toEntity(courseModuleDTO);
        courseModule = courseModuleRepository.save(courseModule);
        return courseModuleMapper.toDto(courseModule);
    }

    @Override
    public CourseModuleDTO update(CourseModuleDTO courseModuleDTO) {
        log.debug("Request to save CourseModule : {}", courseModuleDTO);
        CourseModule courseModule = courseModuleMapper.toEntity(courseModuleDTO);
        courseModule = courseModuleRepository.save(courseModule);
        return courseModuleMapper.toDto(courseModule);
    }

    @Override
    public Optional<CourseModuleDTO> partialUpdate(CourseModuleDTO courseModuleDTO) {
        log.debug("Request to partially update CourseModule : {}", courseModuleDTO);

        return courseModuleRepository
            .findById(courseModuleDTO.getId())
            .map(existingCourseModule -> {
                courseModuleMapper.partialUpdate(existingCourseModule, courseModuleDTO);

                return existingCourseModule;
            })
            .map(courseModuleRepository::save)
            .map(courseModuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseModuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseModules");
        return courseModuleRepository.findAll(pageable).map(courseModuleMapper::toDto);
    }

    public Page<CourseModuleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return courseModuleRepository.findAllWithEagerRelationships(pageable).map(courseModuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseModuleDTO> findOne(Long id) {
        log.debug("Request to get CourseModule : {}", id);
        return courseModuleRepository.findOneWithEagerRelationships(id).map(courseModuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseModule : {}", id);
        courseModuleRepository.deleteById(id);
    }
}
