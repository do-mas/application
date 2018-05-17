package app.api;

import app.repo.entity.Animal;
import app.repo.AnimalRepository;
import app.repo.entity.Room;
import app.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("zoo")
public class ZooController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("animals/without-room")
    public Set<Animal> getAnimalWithoutRoom() {
        return animalRepository.findByRoomIsNull();
    }

}
