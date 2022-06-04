package co.edu.sena.service.impl;

import co.edu.sena.domain.Day;
import co.edu.sena.repository.DayRepository;
import co.edu.sena.service.DayService;
import co.edu.sena.service.dto.DayDTO;
import co.edu.sena.service.mapper.DayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Day}.
 */
@Service
@Transactional
public class DayServiceImpl implements DayService {

    private final Logger log = LoggerFactory.getLogger(DayServiceImpl.class);

    private final DayRepository dayRepository;

    private final DayMapper dayMapper;

    public DayServiceImpl(DayRepository dayRepository, DayMapper dayMapper) {
        this.dayRepository = dayRepository;
        this.dayMapper = dayMapper;
    }

    @Override
    public DayDTO save(DayDTO dayDTO) {
        log.debug("Request to save Day : {}", dayDTO);
        Day day = dayMapper.toEntity(dayDTO);
        day = dayRepository.save(day);
        return dayMapper.toDto(day);
    }

    @Override
    public DayDTO update(DayDTO dayDTO) {
        log.debug("Request to save Day : {}", dayDTO);
        Day day = dayMapper.toEntity(dayDTO);
        day = dayRepository.save(day);
        return dayMapper.toDto(day);
    }

    @Override
    public Optional<DayDTO> partialUpdate(DayDTO dayDTO) {
        log.debug("Request to partially update Day : {}", dayDTO);

        return dayRepository
            .findById(dayDTO.getId())
            .map(existingDay -> {
                dayMapper.partialUpdate(existingDay, dayDTO);

                return existingDay;
            })
            .map(dayRepository::save)
            .map(dayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Days");
        return dayRepository.findAll(pageable).map(dayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DayDTO> findOne(Long id) {
        log.debug("Request to get Day : {}", id);
        return dayRepository.findById(id).map(dayMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Day : {}", id);
        dayRepository.deleteById(id);
    }
}
