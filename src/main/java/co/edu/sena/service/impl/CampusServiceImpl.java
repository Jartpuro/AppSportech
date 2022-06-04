package co.edu.sena.service.impl;

import co.edu.sena.domain.Campus;
import co.edu.sena.repository.CampusRepository;
import co.edu.sena.service.CampusService;
import co.edu.sena.service.dto.CampusDTO;
import co.edu.sena.service.mapper.CampusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Campus}.
 */
@Service
@Transactional
public class CampusServiceImpl implements CampusService {

    private final Logger log = LoggerFactory.getLogger(CampusServiceImpl.class);

    private final CampusRepository campusRepository;

    private final CampusMapper campusMapper;

    public CampusServiceImpl(CampusRepository campusRepository, CampusMapper campusMapper) {
        this.campusRepository = campusRepository;
        this.campusMapper = campusMapper;
    }

    @Override
    public CampusDTO save(CampusDTO campusDTO) {
        log.debug("Request to save Campus : {}", campusDTO);
        Campus campus = campusMapper.toEntity(campusDTO);
        campus = campusRepository.save(campus);
        return campusMapper.toDto(campus);
    }

    @Override
    public CampusDTO update(CampusDTO campusDTO) {
        log.debug("Request to save Campus : {}", campusDTO);
        Campus campus = campusMapper.toEntity(campusDTO);
        campus = campusRepository.save(campus);
        return campusMapper.toDto(campus);
    }

    @Override
    public Optional<CampusDTO> partialUpdate(CampusDTO campusDTO) {
        log.debug("Request to partially update Campus : {}", campusDTO);

        return campusRepository
            .findById(campusDTO.getId())
            .map(existingCampus -> {
                campusMapper.partialUpdate(existingCampus, campusDTO);

                return existingCampus;
            })
            .map(campusRepository::save)
            .map(campusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Campuses");
        return campusRepository.findAll(pageable).map(campusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampusDTO> findOne(Long id) {
        log.debug("Request to get Campus : {}", id);
        return campusRepository.findById(id).map(campusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Campus : {}", id);
        campusRepository.deleteById(id);
    }
}
