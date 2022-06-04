package co.edu.sena.service.impl;

import co.edu.sena.domain.ScheduleVersion;
import co.edu.sena.repository.ScheduleVersionRepository;
import co.edu.sena.service.ScheduleVersionService;
import co.edu.sena.service.dto.ScheduleVersionDTO;
import co.edu.sena.service.mapper.ScheduleVersionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ScheduleVersion}.
 */
@Service
@Transactional
public class ScheduleVersionServiceImpl implements ScheduleVersionService {

    private final Logger log = LoggerFactory.getLogger(ScheduleVersionServiceImpl.class);

    private final ScheduleVersionRepository scheduleVersionRepository;

    private final ScheduleVersionMapper scheduleVersionMapper;

    public ScheduleVersionServiceImpl(ScheduleVersionRepository scheduleVersionRepository, ScheduleVersionMapper scheduleVersionMapper) {
        this.scheduleVersionRepository = scheduleVersionRepository;
        this.scheduleVersionMapper = scheduleVersionMapper;
    }

    @Override
    public ScheduleVersionDTO save(ScheduleVersionDTO scheduleVersionDTO) {
        log.debug("Request to save ScheduleVersion : {}", scheduleVersionDTO);
        ScheduleVersion scheduleVersion = scheduleVersionMapper.toEntity(scheduleVersionDTO);
        scheduleVersion = scheduleVersionRepository.save(scheduleVersion);
        return scheduleVersionMapper.toDto(scheduleVersion);
    }

    @Override
    public ScheduleVersionDTO update(ScheduleVersionDTO scheduleVersionDTO) {
        log.debug("Request to save ScheduleVersion : {}", scheduleVersionDTO);
        ScheduleVersion scheduleVersion = scheduleVersionMapper.toEntity(scheduleVersionDTO);
        scheduleVersion = scheduleVersionRepository.save(scheduleVersion);
        return scheduleVersionMapper.toDto(scheduleVersion);
    }

    @Override
    public Optional<ScheduleVersionDTO> partialUpdate(ScheduleVersionDTO scheduleVersionDTO) {
        log.debug("Request to partially update ScheduleVersion : {}", scheduleVersionDTO);

        return scheduleVersionRepository
            .findById(scheduleVersionDTO.getId())
            .map(existingScheduleVersion -> {
                scheduleVersionMapper.partialUpdate(existingScheduleVersion, scheduleVersionDTO);

                return existingScheduleVersion;
            })
            .map(scheduleVersionRepository::save)
            .map(scheduleVersionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleVersionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScheduleVersions");
        return scheduleVersionRepository.findAll(pageable).map(scheduleVersionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleVersionDTO> findOne(Long id) {
        log.debug("Request to get ScheduleVersion : {}", id);
        return scheduleVersionRepository.findById(id).map(scheduleVersionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleVersion : {}", id);
        scheduleVersionRepository.deleteById(id);
    }
}
