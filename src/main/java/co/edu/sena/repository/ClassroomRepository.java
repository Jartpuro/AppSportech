package co.edu.sena.repository;

import co.edu.sena.domain.Classroom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Classroom entity.
 */
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    default Optional<Classroom> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Classroom> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Classroom> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct classroom from Classroom classroom left join fetch classroom.classroomType left join fetch classroom.campus",
        countQuery = "select count(distinct classroom) from Classroom classroom"
    )
    Page<Classroom> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct classroom from Classroom classroom left join fetch classroom.classroomType left join fetch classroom.campus")
    List<Classroom> findAllWithToOneRelationships();

    @Query(
        "select classroom from Classroom classroom left join fetch classroom.classroomType left join fetch classroom.campus where classroom.id =:id"
    )
    Optional<Classroom> findOneWithToOneRelationships(@Param("id") Long id);
}
