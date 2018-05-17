package app.service;

import app.repo.AnimalRepository;
import app.repo.RoomRepository;
import app.repo.entity.Animal;
import app.repo.entity.Room;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private AnimalRepository animalRepository;
    private RoomRepository roomRepository;

    public RoomService(AnimalRepository animalRepository, RoomRepository roomRepository) {
        this.animalRepository = animalRepository;
        this.roomRepository = roomRepository;
    }

    public Animal assignRoomAsFavorite(Long animalId, Long roomId) {
        Animal animal = fetchAnimal(animalId);
        Room room = fetchRoom(roomId);
        animal.getFavoriteRooms().add(room);
        return animalRepository.save(animal);
    }

    public Animal moveAnimalToRoom(Long animalId, Long roomId) {
        Room room = fetchRoom(roomId);
        Animal animal = setRoom(animalId, room);
        return animalRepository.save(animal);
    }

    public Animal removeFromTheRoom(Long animalId) {
        Animal animal = setRoom(animalId, null);
        return animalRepository.save(animal);
    }

    public Animal removeRoomFromFavorites(Long animalId, Long roomId) {
        Animal animal = fetchAnimal(animalId);
        Room room = fetchRoom(roomId);
        animal.getFavoriteRooms().remove(room);
        return animalRepository.save(animal);
    }


    private Animal setRoom(Long animalId, Room room) {
        Animal animal = fetchAnimal(animalId);
        animal.setRoom(room);
        return animal;
    }


    private Room fetchRoom(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("entity not found"));
    }

    private Animal fetchAnimal(Long animalId) {
        return animalRepository.findById(animalId).orElseThrow(() -> new RuntimeException("entity not found"));
    }

}
