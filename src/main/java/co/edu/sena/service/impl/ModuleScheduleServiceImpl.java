package co.edu.sena.service.impl;

import co.edu.sena.domain.ModuleSchedule;
import co.edu.sena.repository.ModuleScheduleRepository;
import co.edu.sena.service.ModuleScheduleService;
import co.edu.sena.service.dto.ModuleScheduleDTO;
import co.edu.sena.service.mapper.ModuleScheduleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ModuleSchedule}.
 */
@Service
@Transactional
public class ModuleScheduleServiceImpl implements ModuleScheduleService {

    private final Logger log = LoggerFactory.getLogger(ModuleScheduleServiceImpl.class);

    private final ModuleScheduleRepository moduleScheduleRepository;

    private final ModuleScheduleMapper moduleScheduleMapper;

    public ModuleScheduleServiceImpl(ModuleScheduleRepository moduleScheduleRepository, ModuleScheduleMapper moduleScheduleMapper) {
        this.moduleScheduleRepository = moduleScheduleRepository;
        this.moduleScheduleMapper = moduleScheduleMapper;
    }

    @Override
    public ModuleScheduleDTO save(ModuleScheduleDTO moduleScheduleDTO) {
        log.debug("Request to save ModuleSchedule : {}", moduleScheduleDTO);
        ModuleSchedule moduleSchedule = moduleScheduleMapper.toEntity(moduleScheduleDTO);
        moduleSchedule = moduleScheduleRepository.save(moduleSchedule);
        return moduleScheduleMapper.toDto(moduleSchedule);
    }

    @Override
    public ModuleScheduleDTO update(ModuleScheduleDTO moduleScheduleDTO) {
        log.debug("Request to save ModuleSchedule : {}", moduleScheduleDTO);
        ModuleSchedule moduleSchedule = moduleScheduleMapper.toEntity(moduleScheduleDTO);
        moduleSchedule = moduleScheduleRepository.save(moduleSchedule);
        return moduleScheduleMapper.toDto(moduleSchedule);
    }

    @Override
    public Optional<ModuleScheduleDTO> partialUpdate(ModuleScheduleDTO moduleScheduleDTO) {
        log.debug("Request to partially update ModuleSchedule : {}", moduleScheduleDTO);

        return moduleScheduleRepository
            .findById(moduleScheduleDTO.getId())
            .map(existingModuleSchedule -> {
                moduleScheduleMapper.partialUpdate(existingModuleSchedule, moduleScheduleDTO);

                return existingModuleSchedule;
            })
            .map(moduleScheduleRepository::save)
            .map(moduleScheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModuleScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModuleSchedules");
        return moduleScheduleRepository.findAll(pageable).map(moduleScheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModuleScheduleDTO> findOne(Long id) {
        log.debug("Request to get ModuleSchedule : {}", id);
        return moduleScheduleRepository.findById(id).map(moduleScheduleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModuleSchedule : {}", id);
        moduleScheduleRepository.deleteById(id);
    }
}
