package co.edu.sena.service.impl;

import co.edu.sena.domain.BoundingSchedule;
import co.edu.sena.repository.BoundingScheduleRepository;
import co.edu.sena.service.BoundingScheduleService;
import co.edu.sena.service.dto.BoundingScheduleDTO;
import co.edu.sena.service.mapper.BoundingScheduleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BoundingSchedule}.
 */
@Service
@Transactional
public class BoundingScheduleServiceImpl implements BoundingScheduleService {

    private final Logger log = LoggerFactory.getLogger(BoundingScheduleServiceImpl.class);

    private final BoundingScheduleRepository boundingScheduleRepository;

    private final BoundingScheduleMapper boundingScheduleMapper;

    public BoundingScheduleServiceImpl(
        BoundingScheduleRepository boundingScheduleRepository,
        BoundingScheduleMapper boundingScheduleMapper
    ) {
        this.boundingScheduleRepository = boundingScheduleRepository;
        this.boundingScheduleMapper = boundingScheduleMapper;
    }

    @Override
    public BoundingScheduleDTO save(BoundingScheduleDTO boundingScheduleDTO) {
        log.debug("Request to save BoundingSchedule : {}", boundingScheduleDTO);
        BoundingSchedule boundingSchedule = boundingScheduleMapper.toEntity(boundingScheduleDTO);
        boundingSchedule = boundingScheduleRepository.save(boundingSchedule);
        return boundingScheduleMapper.toDto(boundingSchedule);
    }

    @Override
    public BoundingScheduleDTO update(BoundingScheduleDTO boundingScheduleDTO) {
        log.debug("Request to save BoundingSchedule : {}", boundingScheduleDTO);
        BoundingSchedule boundingSchedule = boundingScheduleMapper.toEntity(boundingScheduleDTO);
        boundingSchedule = boundingScheduleRepository.save(boundingSchedule);
        return boundingScheduleMapper.toDto(boundingSchedule);
    }

    @Override
    public Optional<BoundingScheduleDTO> partialUpdate(BoundingScheduleDTO boundingScheduleDTO) {
        log.debug("Request to partially update BoundingSchedule : {}", boundingScheduleDTO);

        return boundingScheduleRepository
            .findById(boundingScheduleDTO.getId())
            .map(existingBoundingSchedule -> {
                boundingScheduleMapper.partialUpdate(existingBoundingSchedule, boundingScheduleDTO);

                return existingBoundingSchedule;
            })
            .map(boundingScheduleRepository::save)
            .map(boundingScheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoundingScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BoundingSchedules");
        return boundingScheduleRepository.findAll(pageable).map(boundingScheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BoundingScheduleDTO> findOne(Long id) {
        log.debug("Request to get BoundingSchedule : {}", id);
        return boundingScheduleRepository.findById(id).map(boundingScheduleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BoundingSchedule : {}", id);
        boundingScheduleRepository.deleteById(id);
    }
}
