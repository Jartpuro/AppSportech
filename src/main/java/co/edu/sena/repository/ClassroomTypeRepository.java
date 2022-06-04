package co.edu.sena.repository;

import co.edu.sena.domain.ClassroomType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClassroomType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassroomTypeRepository extends JpaRepository<ClassroomType, Long> {}
