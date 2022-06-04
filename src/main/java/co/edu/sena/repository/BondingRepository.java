package co.edu.sena.repository;

import co.edu.sena.domain.Bonding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bonding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BondingRepository extends JpaRepository<Bonding, Long> {}
