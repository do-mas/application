package app.api;

import app.repo.entity.Animal;
import app.repo.AnimalRepository;
import app.repo.entity.Room;
import app.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private AnimalRepository animalRepository;

    @PostMapping
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @DeleteMapping("{id}")
    public void deleteRoom(@RequestParam(value = "id") Long id) {
        roomRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public Room updateRoom(@PathVariable(value = "id") Long id, Room room) {
        room.setRoomId(id);
        return roomRepository.save(room);
    }

    @GetMapping("{id}")
    public Room getRoom(@PathVariable(value = "id") Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("entity not found"));
    }

    @GetMapping("{id}/animals")
    public Set<Animal> getAnimalInRoom(@PathVariable(value = "id") Long id) {
        return animalRepository.findByRoom(id);
    }







}
