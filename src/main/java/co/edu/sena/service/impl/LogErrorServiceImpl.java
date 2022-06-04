package co.edu.sena.service.impl;

import co.edu.sena.domain.LogError;
import co.edu.sena.repository.LogErrorRepository;
import co.edu.sena.service.LogErrorService;
import co.edu.sena.service.dto.LogErrorDTO;
import co.edu.sena.service.mapper.LogErrorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LogError}.
 */
@Service
@Transactional
public class LogErrorServiceImpl implements LogErrorService {

    private final Logger log = LoggerFactory.getLogger(LogErrorServiceImpl.class);

    private final LogErrorRepository logErrorRepository;

    private final LogErrorMapper logErrorMapper;

    public LogErrorServiceImpl(LogErrorRepository logErrorRepository, LogErrorMapper logErrorMapper) {
        this.logErrorRepository = logErrorRepository;
        this.logErrorMapper = logErrorMapper;
    }

    @Override
    public LogErrorDTO save(LogErrorDTO logErrorDTO) {
        log.debug("Request to save LogError : {}", logErrorDTO);
        LogError logError = logErrorMapper.toEntity(logErrorDTO);
        logError = logErrorRepository.save(logError);
        return logErrorMapper.toDto(logError);
    }

    @Override
    public LogErrorDTO update(LogErrorDTO logErrorDTO) {
        log.debug("Request to save LogError : {}", logErrorDTO);
        LogError logError = logErrorMapper.toEntity(logErrorDTO);
        logError = logErrorRepository.save(logError);
        return logErrorMapper.toDto(logError);
    }

    @Override
    public Optional<LogErrorDTO> partialUpdate(LogErrorDTO logErrorDTO) {
        log.debug("Request to partially update LogError : {}", logErrorDTO);

        return logErrorRepository
            .findById(logErrorDTO.getId())
            .map(existingLogError -> {
                logErrorMapper.partialUpdate(existingLogError, logErrorDTO);

                return existingLogError;
            })
            .map(logErrorRepository::save)
            .map(logErrorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogErrorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LogErrors");
        return logErrorRepository.findAll(pageable).map(logErrorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LogErrorDTO> findOne(Long id) {
        log.debug("Request to get LogError : {}", id);
        return logErrorRepository.findById(id).map(logErrorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogError : {}", id);
        logErrorRepository.deleteById(id);
    }
}
