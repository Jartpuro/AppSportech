package co.edu.sena.service.impl;

import co.edu.sena.domain.AreaTrainer;
import co.edu.sena.repository.AreaTrainerRepository;
import co.edu.sena.service.AreaTrainerService;
import co.edu.sena.service.dto.AreaTrainerDTO;
import co.edu.sena.service.mapper.AreaTrainerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AreaTrainer}.
 */
@Service
@Transactional
public class AreaTrainerServiceImpl implements AreaTrainerService {

    private final Logger log = LoggerFactory.getLogger(AreaTrainerServiceImpl.class);

    private final AreaTrainerRepository areaTrainerRepository;

    private final AreaTrainerMapper areaTrainerMapper;

    public AreaTrainerServiceImpl(AreaTrainerRepository areaTrainerRepository, AreaTrainerMapper areaTrainerMapper) {
        this.areaTrainerRepository = areaTrainerRepository;
        this.areaTrainerMapper = areaTrainerMapper;
    }

    @Override
    public AreaTrainerDTO save(AreaTrainerDTO areaTrainerDTO) {
        log.debug("Request to save AreaTrainer : {}", areaTrainerDTO);
        AreaTrainer areaTrainer = areaTrainerMapper.toEntity(areaTrainerDTO);
        areaTrainer = areaTrainerRepository.save(areaTrainer);
        return areaTrainerMapper.toDto(areaTrainer);
    }

    @Override
    public AreaTrainerDTO update(AreaTrainerDTO areaTrainerDTO) {
        log.debug("Request to save AreaTrainer : {}", areaTrainerDTO);
        AreaTrainer areaTrainer = areaTrainerMapper.toEntity(areaTrainerDTO);
        areaTrainer = areaTrainerRepository.save(areaTrainer);
        return areaTrainerMapper.toDto(areaTrainer);
    }

    @Override
    public Optional<AreaTrainerDTO> partialUpdate(AreaTrainerDTO areaTrainerDTO) {
        log.debug("Request to partially update AreaTrainer : {}", areaTrainerDTO);

        return areaTrainerRepository
            .findById(areaTrainerDTO.getId())
            .map(existingAreaTrainer -> {
                areaTrainerMapper.partialUpdate(existingAreaTrainer, areaTrainerDTO);

                return existingAreaTrainer;
            })
            .map(areaTrainerRepository::save)
            .map(areaTrainerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AreaTrainerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AreaTrainers");
        return areaTrainerRepository.findAll(pageable).map(areaTrainerMapper::toDto);
    }

    public Page<AreaTrainerDTO> findAllWithEagerRelationships(Pageable pageable) {
        return areaTrainerRepository.findAllWithEagerRelationships(pageable).map(areaTrainerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AreaTrainerDTO> findOne(Long id) {
        log.debug("Request to get AreaTrainer : {}", id);
        return areaTrainerRepository.findOneWithEagerRelationships(id).map(areaTrainerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AreaTrainer : {}", id);
        areaTrainerRepository.deleteById(id);
    }
}
