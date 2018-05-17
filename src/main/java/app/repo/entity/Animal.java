package app.repo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Animal {

    @Id
    @GeneratedValue
    @Column(name="animal_id")
    private Long animalId;

    private String title;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "favorite_rooms",
            joinColumns = { @JoinColumn(name = "animal_id") },
            inverseJoinColumns = { @JoinColumn(name = "room_id") }
    )
    private Set<Room> favoriteRooms = new HashSet<>();

    public Set<Room> getFavoriteRooms() {
        return favoriteRooms;
    }

    public Animal setFavoriteRooms(Set<Room> favoriteRooms) {
        this.favoriteRooms = favoriteRooms;
        return this;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public Animal setAnimalId(Long animalId) {
        this.animalId = animalId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Animal setTitle(String title) {
        this.title = title;
        return this;
    }

    public Room getRoom() {
        return room;
    }

    public Animal setRoom(Room room) {
        this.room = room;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal animal = (Animal) o;

        if (animalId != null ? !animalId.equals(animal.animalId) : animal.animalId != null) return false;
        if (title != null ? !title.equals(animal.title) : animal.title != null) return false;
        if (room != null ? !room.equals(animal.room) : animal.room != null) return false;
        return favoriteRooms != null ? favoriteRooms.equals(animal.favoriteRooms) : animal.favoriteRooms == null;
    }

    @Override
    public int hashCode() {
        int result = animalId != null ? animalId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (favoriteRooms != null ? favoriteRooms.hashCode() : 0);
        return result;
    }
}

