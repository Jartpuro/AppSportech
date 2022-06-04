package co.edu.sena.repository;

import co.edu.sena.domain.CourseModule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CourseModule entity.
 */
@Repository
public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
    default Optional<CourseModule> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CourseModule> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CourseModule> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct courseModule from CourseModule courseModule left join fetch courseModule.course",
        countQuery = "select count(distinct courseModule) from CourseModule courseModule"
    )
    Page<CourseModule> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct courseModule from CourseModule courseModule left join fetch courseModule.course")
    List<CourseModule> findAllWithToOneRelationships();

    @Query("select courseModule from CourseModule courseModule left join fetch courseModule.course where courseModule.id =:id")
    Optional<CourseModule> findOneWithToOneRelationships(@Param("id") Long id);
}
