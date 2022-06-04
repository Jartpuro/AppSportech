package co.edu.sena.service.mapper;

import co.edu.sena.domain.Bonding;
import co.edu.sena.domain.BondingTrainer;
import co.edu.sena.domain.Trainer;
import co.edu.sena.domain.Year;
import co.edu.sena.service.dto.BondingDTO;
import co.edu.sena.service.dto.BondingTrainerDTO;
import co.edu.sena.service.dto.TrainerDTO;
import co.edu.sena.service.dto.YearDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BondingTrainer} and its DTO {@link BondingTrainerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BondingTrainerMapper extends EntityMapper<BondingTrainerDTO, BondingTrainer> {
    @Mapping(target = "year", source = "year", qualifiedByName = "yearYearNumber")
    @Mapping(target = "trainer", source = "trainer", qualifiedByName = "trainerId")
    @Mapping(target = "bonding", source = "bonding", qualifiedByName = "bondingBondingType")
    BondingTrainerDTO toDto(BondingTrainer s);

    @Named("yearYearNumber")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "yearNumber", source = "yearNumber")
    YearDTO toDtoYearYearNumber(Year year);

    @Named("trainerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrainerDTO toDtoTrainerId(Trainer trainer);

    @Named("bondingBondingType")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "bondingType", source = "bondingType")
    BondingDTO toDtoBondingBondingType(Bonding bonding);
}
