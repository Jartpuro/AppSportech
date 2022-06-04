package co.edu.sena.service.impl;

import co.edu.sena.domain.Trainer;
import co.edu.sena.repository.TrainerRepository;
import co.edu.sena.service.TrainerService;
import co.edu.sena.service.dto.TrainerDTO;
import co.edu.sena.service.mapper.TrainerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Trainer}.
 */
@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final Logger log = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final TrainerRepository trainerRepository;

    private final TrainerMapper trainerMapper;

    public TrainerServiceImpl(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }

    @Override
    public TrainerDTO save(TrainerDTO trainerDTO) {
        log.debug("Request to save Trainer : {}", trainerDTO);
        Trainer trainer = trainerMapper.toEntity(trainerDTO);
        trainer = trainerRepository.save(trainer);
        return trainerMapper.toDto(trainer);
    }

    @Override
    public TrainerDTO update(TrainerDTO trainerDTO) {
        log.debug("Request to save Trainer : {}", trainerDTO);
        Trainer trainer = trainerMapper.toEntity(trainerDTO);
        trainer = trainerRepository.save(trainer);
        return trainerMapper.toDto(trainer);
    }

    @Override
    public Optional<TrainerDTO> partialUpdate(TrainerDTO trainerDTO) {
        log.debug("Request to partially update Trainer : {}", trainerDTO);

        return trainerRepository
            .findById(trainerDTO.getId())
            .map(existingTrainer -> {
                trainerMapper.partialUpdate(existingTrainer, trainerDTO);

                return existingTrainer;
            })
            .map(trainerRepository::save)
            .map(trainerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trainers");
        return trainerRepository.findAll(pageable).map(trainerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainerDTO> findOne(Long id) {
        log.debug("Request to get Trainer : {}", id);
        return trainerRepository.findById(id).map(trainerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trainer : {}", id);
        trainerRepository.deleteById(id);
    }
}
