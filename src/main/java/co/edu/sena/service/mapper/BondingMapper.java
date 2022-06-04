package co.edu.sena.service.mapper;

import co.edu.sena.domain.Bonding;
import co.edu.sena.service.dto.BondingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bonding} and its DTO {@link BondingDTO}.
 */
@Mapper(componentModel = "spring")
public interface BondingMapper extends EntityMapper<BondingDTO, Bonding> {}
