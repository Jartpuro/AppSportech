package co.edu.sena.service.impl;

import co.edu.sena.domain.Year;
import co.edu.sena.repository.YearRepository;
import co.edu.sena.service.YearService;
import co.edu.sena.service.dto.YearDTO;
import co.edu.sena.service.mapper.YearMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Year}.
 */
@Service
@Transactional
public class YearServiceImpl implements YearService {

    private final Logger log = LoggerFactory.getLogger(YearServiceImpl.class);

    private final YearRepository yearRepository;

    private final YearMapper yearMapper;

    public YearServiceImpl(YearRepository yearRepository, YearMapper yearMapper) {
        this.yearRepository = yearRepository;
        this.yearMapper = yearMapper;
    }

    @Override
    public YearDTO save(YearDTO yearDTO) {
        log.debug("Request to save Year : {}", yearDTO);
        Year year = yearMapper.toEntity(yearDTO);
        year = yearRepository.save(year);
        return yearMapper.toDto(year);
    }

    @Override
    public YearDTO update(YearDTO yearDTO) {
        log.debug("Request to save Year : {}", yearDTO);
        Year year = yearMapper.toEntity(yearDTO);
        year = yearRepository.save(year);
        return yearMapper.toDto(year);
    }

    @Override
    public Optional<YearDTO> partialUpdate(YearDTO yearDTO) {
        log.debug("Request to partially update Year : {}", yearDTO);

        return yearRepository
            .findById(yearDTO.getId())
            .map(existingYear -> {
                yearMapper.partialUpdate(existingYear, yearDTO);

                return existingYear;
            })
            .map(yearRepository::save)
            .map(yearMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<YearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Years");
        return yearRepository.findAll(pageable).map(yearMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<YearDTO> findOne(Long id) {
        log.debug("Request to get Year : {}", id);
        return yearRepository.findById(id).map(yearMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Year : {}", id);
        yearRepository.deleteById(id);
    }
}
