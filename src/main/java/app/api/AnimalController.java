package app.api;

import app.service.RoomService;
import app.repo.AnimalRepository;
import app.repo.entity.Animal;
import app.repo.entity.Room;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("animals")
public class AnimalController {

    private AnimalRepository animalRepository;
    private RoomService roomService;

    public AnimalController(AnimalRepository animalRepository, RoomService roomService) {
        this.animalRepository = animalRepository;
        this.roomService = roomService;
    }

    @PostMapping
    public Animal saveAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    @DeleteMapping("{id}")
    public void deleteAnimal(@RequestParam(value = "id") Long id) {
        animalRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public Animal updateAnimal(@PathVariable(value = "id") Long id, Animal animal) {
        return animalRepository.save(animal);
    }

    @GetMapping("{id}")
    public Animal getAnimal(@PathVariable(value = "id") Long id) {
        return animalRepository.findById(id).get();
    }

    @PutMapping("{animalId}/set-favorite-room/{roomId}")
    public void setRoomAsFavorite(@PathVariable(value = "animalId") Long animalId,
                                  @PathVariable(value = "roomId") Long roomId) {
        roomService.assignRoomAsFavorite(animalId, roomId);
    }

    @PutMapping("{animalId}/remove-favorite-room/{roomId}")
    public void unassignFavoriteRoom(@PathVariable(value = "animalId") Long animalId,
                                     @PathVariable(value = "roomId") Long roomId) {
        roomService.removeRoomFromFavorites(animalId, roomId);
    }

    @PutMapping("{animalId}/move-to-room/{roomId}")
    public void moveToTheRoom(@PathVariable(value = "animalId") Long animalId,
                              @PathVariable(value = "roomId") Long roomId) {
        roomService.moveAnimalToRoom(animalId, roomId);
    }

    @PutMapping("{animalId}/remove-from-room")
    public void removeFromRoom(@PathVariable(value = "animalId") Long animalId) {
        roomService.removeFromTheRoom(animalId);
    }

    @GetMapping("{animalId}/favorite-rooms")
    public Set<Room> getFavoriteRooms(@PathVariable(value = "animalId") Long animalId) {
        return animalRepository.findById(animalId).get().getFavoriteRooms();
    }






}
