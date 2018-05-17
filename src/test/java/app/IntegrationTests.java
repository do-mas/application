package app;

import app.api.AnimalController;
import app.api.RoomController;
import app.api.ZooController;
import app.repo.entity.Animal;
import app.repo.AnimalRepository;
import app.repo.entity.Room;
import app.repo.RoomRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IntegrationTests {

    @Autowired
    private AnimalController animalController;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private ZooController zooController;
    @Autowired
    private RoomController roomController;
    @Autowired
    private RoomRepository roomRepository;


    @Before
    public void beforeEach() {
        animalRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    @Transactional
    public void placeAnimalIntoRoom() {
        Animal animal = saveAnimal();
        Room room = saveRoom();

        animalController.moveToTheRoom(animal.getAnimalId(), room.getRoomId());

        Animal animalInTheRoom = animalController.getAnimal(animal.getAnimalId());
        Assert.assertEquals(room, animalInTheRoom.getRoom());
    }

    @Test
    @Transactional
    public void placeAnimalIntoNewRoom() {
        Animal animal = saveAnimal();
        Room oldRoom = saveRoom();
        animalController.moveToTheRoom(animal.getAnimalId(), oldRoom.getRoomId());

        Room newRoom = saveRoom();
        animalController.moveToTheRoom(animal.getAnimalId(), newRoom.getRoomId());

        Animal animalWithChangedRoom = animalController.getAnimal(animal.getAnimalId());
        Assert.assertEquals(animalWithChangedRoom.getRoom(), newRoom);
    }

    @Test
    @Transactional
    public void removeAnimalFromTheRoom() {
        Animal animal = saveAnimal();
        Room room1 = saveRoom();
        animalController.moveToTheRoom(animal.getAnimalId(), room1.getRoomId());

        Animal animalInTheRoom = animalController.getAnimal(animal.getAnimalId());
        Assert.assertEquals(animalInTheRoom.getRoom().getRoomId(), room1.getRoomId());

        animalController.removeFromRoom(animal.getAnimalId());

        Animal animalWithoutTheRoom = animalController.getAnimal(animal.getAnimalId());
        Assert.assertNull(animalWithoutTheRoom.getRoom());
    }

    @Test
    public void assignRoomAsFavorite() {
        Animal animal = saveAnimal();
        Room room1 = saveRoom();
        animalController.setRoomAsFavorite(animal.getAnimalId(), room1.getRoomId());

        animal = animalController.getAnimal(animal.getAnimalId());
        Assert.assertEquals(animal.getFavoriteRooms().size(), 1);
    }

    @Test
    public void unAssignRoomAsFavorite() {
        Animal animal = saveAnimal();
        Room room1 = saveRoom();
        animalController.setRoomAsFavorite(animal.getAnimalId(), room1.getRoomId());

        animal = animalController.getAnimal(animal.getAnimalId());
        Assert.assertEquals(animal.getFavoriteRooms().size(), 1);

        animalController.unassignFavoriteRoom(animal.getAnimalId(), room1.getRoomId());

        animal = animalController.getAnimal(animal.getAnimalId());
        Assert.assertEquals(animal.getFavoriteRooms().size(), 0);
    }

    @Test
    public void listOfAnimalWithoutRoom() {
        Animal animal = saveAnimal();
        Room room1 = saveRoom();
        animalController.moveToTheRoom(animal.getAnimalId(), room1.getRoomId());

        saveTwoAnimalsWithoutTheRoom();

        Set<Animal> animalWithoutRoom = zooController.getAnimalWithoutRoom();

        Assert.assertEquals(2, animalWithoutRoom.size());
    }

    @Test
    public void listOfAnimalInTheRoom() {
        Animal animal = saveAnimal();
        Animal animal2 = saveAnimal();
        Room room1 = saveRoom();

        animalController.moveToTheRoom(animal.getAnimalId(), room1.getRoomId());
        animalController.moveToTheRoom(animal2.getAnimalId(), room1.getRoomId());

        Set<Animal> animalsInTheRoom = roomController.getAnimalInRoom(room1.getRoomId());

        Assert.assertEquals(2, animalsInTheRoom.size());
    }

    @Test
    public void listOfAnimalFavoriteRooms() {
        Animal animal = saveAnimal();
        Room room1 = saveRoom();
        Room room2 = saveRoom();
        Room room3 = saveRoom();

        animalController.setRoomAsFavorite(animal.getAnimalId(), room1.getRoomId());
        animalController.setRoomAsFavorite(animal.getAnimalId(), room2.getRoomId());
        animalController.setRoomAsFavorite(animal.getAnimalId(), room3.getRoomId());

        Set<Room> favoriteRooms = animalController.getFavoriteRooms(animal.getAnimalId());
        Assert.assertEquals(3, favoriteRooms.size());
    }

    private void saveTwoAnimalsWithoutTheRoom() {
        saveAnimal();
        saveAnimal();
    }

    private Room saveRoom() {
        Room room = new Room();
        return roomController.saveRoom(room);
    }

    private Animal saveAnimal() {
        Animal animal = new Animal();
        return animalController.saveAnimal(animal);
    }



}
