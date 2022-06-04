package co.edu.sena.service.mapper;

import co.edu.sena.domain.Modality;
import co.edu.sena.service.dto.ModalityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Modality} and its DTO {@link ModalityDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModalityMapper extends EntityMapper<ModalityDTO, Modality> {}
