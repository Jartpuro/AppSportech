package co.edu.sena.service.impl;

import co.edu.sena.domain.TrainingStatus;
import co.edu.sena.repository.TrainingStatusRepository;
import co.edu.sena.service.TrainingStatusService;
import co.edu.sena.service.dto.TrainingStatusDTO;
import co.edu.sena.service.mapper.TrainingStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TrainingStatus}.
 */
@Service
@Transactional
public class TrainingStatusServiceImpl implements TrainingStatusService {

    private final Logger log = LoggerFactory.getLogger(TrainingStatusServiceImpl.class);

    private final TrainingStatusRepository trainingStatusRepository;

    private final TrainingStatusMapper trainingStatusMapper;

    public TrainingStatusServiceImpl(TrainingStatusRepository trainingStatusRepository, TrainingStatusMapper trainingStatusMapper) {
        this.trainingStatusRepository = trainingStatusRepository;
        this.trainingStatusMapper = trainingStatusMapper;
    }

    @Override
    public TrainingStatusDTO save(TrainingStatusDTO trainingStatusDTO) {
        log.debug("Request to save TrainingStatus : {}", trainingStatusDTO);
        TrainingStatus trainingStatus = trainingStatusMapper.toEntity(trainingStatusDTO);
        trainingStatus = trainingStatusRepository.save(trainingStatus);
        return trainingStatusMapper.toDto(trainingStatus);
    }

    @Override
    public TrainingStatusDTO update(TrainingStatusDTO trainingStatusDTO) {
        log.debug("Request to save TrainingStatus : {}", trainingStatusDTO);
        TrainingStatus trainingStatus = trainingStatusMapper.toEntity(trainingStatusDTO);
        trainingStatus = trainingStatusRepository.save(trainingStatus);
        return trainingStatusMapper.toDto(trainingStatus);
    }

    @Override
    public Optional<TrainingStatusDTO> partialUpdate(TrainingStatusDTO trainingStatusDTO) {
        log.debug("Request to partially update TrainingStatus : {}", trainingStatusDTO);

        return trainingStatusRepository
            .findById(trainingStatusDTO.getId())
            .map(existingTrainingStatus -> {
                trainingStatusMapper.partialUpdate(existingTrainingStatus, trainingStatusDTO);

                return existingTrainingStatus;
            })
            .map(trainingStatusRepository::save)
            .map(trainingStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainingStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainingStatuses");
        return trainingStatusRepository.findAll(pageable).map(trainingStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingStatusDTO> findOne(Long id) {
        log.debug("Request to get TrainingStatus : {}", id);
        return trainingStatusRepository.findById(id).map(trainingStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainingStatus : {}", id);
        trainingStatusRepository.deleteById(id);
    }
}
