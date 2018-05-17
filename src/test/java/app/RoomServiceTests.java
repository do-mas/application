package app;


import app.repo.AnimalRepository;
import app.repo.RoomRepository;
import app.repo.entity.Animal;
import app.repo.entity.Room;
import app.service.RoomService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
public class RoomServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    public void assignRoomAsFavorite() {

        Mockito.when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room()));
        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(new Animal()));

        roomService.assignRoomAsFavorite(1L,1L);

        final ArgumentCaptor<Animal> argumentCaptor = ArgumentCaptor.forClass(Animal.class);
        Mockito.verify(animalRepository).save(argumentCaptor.capture());
        Assert.assertEquals(1, argumentCaptor.getValue().getFavoriteRooms().size());

//       Mockito.verify(animalRepository).save(ArgumentMatchers.argThat(a -> a.getFavoriteRooms().size() == 1));

    }


}
