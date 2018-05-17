package app.repo;

import app.repo.entity.Animal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Transactional
public interface AnimalRepository extends CrudRepository<Animal, Long> {

    Set<Animal> findByRoomIsNull();

    @Query("select a from Animal a JOIN a.room r where r.roomId = ?1")
    Set<Animal> findByRoom(Long roomId);

}
