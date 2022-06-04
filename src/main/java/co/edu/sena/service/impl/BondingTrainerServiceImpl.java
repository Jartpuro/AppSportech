package co.edu.sena.service.impl;

import co.edu.sena.domain.BondingTrainer;
import co.edu.sena.repository.BondingTrainerRepository;
import co.edu.sena.service.BondingTrainerService;
import co.edu.sena.service.dto.BondingTrainerDTO;
import co.edu.sena.service.mapper.BondingTrainerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BondingTrainer}.
 */
@Service
@Transactional
public class BondingTrainerServiceImpl implements BondingTrainerService {

    private final Logger log = LoggerFactory.getLogger(BondingTrainerServiceImpl.class);

    private final BondingTrainerRepository bondingTrainerRepository;

    private final BondingTrainerMapper bondingTrainerMapper;

    public BondingTrainerServiceImpl(BondingTrainerRepository bondingTrainerRepository, BondingTrainerMapper bondingTrainerMapper) {
        this.bondingTrainerRepository = bondingTrainerRepository;
        this.bondingTrainerMapper = bondingTrainerMapper;
    }

    @Override
    public BondingTrainerDTO save(BondingTrainerDTO bondingTrainerDTO) {
        log.debug("Request to save BondingTrainer : {}", bondingTrainerDTO);
        BondingTrainer bondingTrainer = bondingTrainerMapper.toEntity(bondingTrainerDTO);
        bondingTrainer = bondingTrainerRepository.save(bondingTrainer);
        return bondingTrainerMapper.toDto(bondingTrainer);
    }

    @Override
    public BondingTrainerDTO update(BondingTrainerDTO bondingTrainerDTO) {
        log.debug("Request to save BondingTrainer : {}", bondingTrainerDTO);
        BondingTrainer bondingTrainer = bondingTrainerMapper.toEntity(bondingTrainerDTO);
        bondingTrainer = bondingTrainerRepository.save(bondingTrainer);
        return bondingTrainerMapper.toDto(bondingTrainer);
    }

    @Override
    public Optional<BondingTrainerDTO> partialUpdate(BondingTrainerDTO bondingTrainerDTO) {
        log.debug("Request to partially update BondingTrainer : {}", bondingTrainerDTO);

        return bondingTrainerRepository
            .findById(bondingTrainerDTO.getId())
            .map(existingBondingTrainer -> {
                bondingTrainerMapper.partialUpdate(existingBondingTrainer, bondingTrainerDTO);

                return existingBondingTrainer;
            })
            .map(bondingTrainerRepository::save)
            .map(bondingTrainerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BondingTrainerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BondingTrainers");
        return bondingTrainerRepository.findAll(pageable).map(bondingTrainerMapper::toDto);
    }

    public Page<BondingTrainerDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bondingTrainerRepository.findAllWithEagerRelationships(pageable).map(bondingTrainerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BondingTrainerDTO> findOne(Long id) {
        log.debug("Request to get BondingTrainer : {}", id);
        return bondingTrainerRepository.findOneWithEagerRelationships(id).map(bondingTrainerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BondingTrainer : {}", id);
        bondingTrainerRepository.deleteById(id);
    }
}
