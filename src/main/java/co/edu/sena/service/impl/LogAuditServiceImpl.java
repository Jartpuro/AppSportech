package co.edu.sena.service.impl;

import co.edu.sena.domain.LogAudit;
import co.edu.sena.repository.LogAuditRepository;
import co.edu.sena.service.LogAuditService;
import co.edu.sena.service.dto.LogAuditDTO;
import co.edu.sena.service.mapper.LogAuditMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LogAudit}.
 */
@Service
@Transactional
public class LogAuditServiceImpl implements LogAuditService {

    private final Logger log = LoggerFactory.getLogger(LogAuditServiceImpl.class);

    private final LogAuditRepository logAuditRepository;

    private final LogAuditMapper logAuditMapper;

    public LogAuditServiceImpl(LogAuditRepository logAuditRepository, LogAuditMapper logAuditMapper) {
        this.logAuditRepository = logAuditRepository;
        this.logAuditMapper = logAuditMapper;
    }

    @Override
    public LogAuditDTO save(LogAuditDTO logAuditDTO) {
        log.debug("Request to save LogAudit : {}", logAuditDTO);
        LogAudit logAudit = logAuditMapper.toEntity(logAuditDTO);
        logAudit = logAuditRepository.save(logAudit);
        return logAuditMapper.toDto(logAudit);
    }

    @Override
    public LogAuditDTO update(LogAuditDTO logAuditDTO) {
        log.debug("Request to save LogAudit : {}", logAuditDTO);
        LogAudit logAudit = logAuditMapper.toEntity(logAuditDTO);
        logAudit = logAuditRepository.save(logAudit);
        return logAuditMapper.toDto(logAudit);
    }

    @Override
    public Optional<LogAuditDTO> partialUpdate(LogAuditDTO logAuditDTO) {
        log.debug("Request to partially update LogAudit : {}", logAuditDTO);

        return logAuditRepository
            .findById(logAuditDTO.getId())
            .map(existingLogAudit -> {
                logAuditMapper.partialUpdate(existingLogAudit, logAuditDTO);

                return existingLogAudit;
            })
            .map(logAuditRepository::save)
            .map(logAuditMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogAuditDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LogAudits");
        return logAuditRepository.findAll(pageable).map(logAuditMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LogAuditDTO> findOne(Long id) {
        log.debug("Request to get LogAudit : {}", id);
        return logAuditRepository.findById(id).map(logAuditMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogAudit : {}", id);
        logAuditRepository.deleteById(id);
    }
}
