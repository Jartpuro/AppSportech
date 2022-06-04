package co.edu.sena.service.impl;

import co.edu.sena.domain.Modality;
import co.edu.sena.repository.ModalityRepository;
import co.edu.sena.service.ModalityService;
import co.edu.sena.service.dto.ModalityDTO;
import co.edu.sena.service.mapper.ModalityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Modality}.
 */
@Service
@Transactional
public class ModalityServiceImpl implements ModalityService {

    private final Logger log = LoggerFactory.getLogger(ModalityServiceImpl.class);

    private final ModalityRepository modalityRepository;

    private final ModalityMapper modalityMapper;

    public ModalityServiceImpl(ModalityRepository modalityRepository, ModalityMapper modalityMapper) {
        this.modalityRepository = modalityRepository;
        this.modalityMapper = modalityMapper;
    }

    @Override
    public ModalityDTO save(ModalityDTO modalityDTO) {
        log.debug("Request to save Modality : {}", modalityDTO);
        Modality modality = modalityMapper.toEntity(modalityDTO);
        modality = modalityRepository.save(modality);
        return modalityMapper.toDto(modality);
    }

    @Override
    public ModalityDTO update(ModalityDTO modalityDTO) {
        log.debug("Request to save Modality : {}", modalityDTO);
        Modality modality = modalityMapper.toEntity(modalityDTO);
        modality = modalityRepository.save(modality);
        return modalityMapper.toDto(modality);
    }

    @Override
    public Optional<ModalityDTO> partialUpdate(ModalityDTO modalityDTO) {
        log.debug("Request to partially update Modality : {}", modalityDTO);

        return modalityRepository
            .findById(modalityDTO.getId())
            .map(existingModality -> {
                modalityMapper.partialUpdate(existingModality, modalityDTO);

                return existingModality;
            })
            .map(modalityRepository::save)
            .map(modalityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModalityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modalities");
        return modalityRepository.findAll(pageable).map(modalityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModalityDTO> findOne(Long id) {
        log.debug("Request to get Modality : {}", id);
        return modalityRepository.findById(id).map(modalityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Modality : {}", id);
        modalityRepository.deleteById(id);
    }
}
