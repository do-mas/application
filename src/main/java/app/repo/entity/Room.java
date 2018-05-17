package app.repo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long roomId;

    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private Set<Animal> animals = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "favoriteRooms")
    private Set<Animal> favoriteForAnimals = new HashSet<>();

    public Long getRoomId() {
        return roomId;
    }

    public Room setRoomId(Long roomId) {
        this.roomId = roomId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Room setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public Room setAnimals(Set<Animal> animals) {
        this.animals = animals;
        return this;
    }

    public Set<Animal> getFavoriteForAnimals() {
        return favoriteForAnimals;
    }

    public Room setFavoriteForAnimals(Set<Animal> favoriteForAnimals) {
        this.favoriteForAnimals = favoriteForAnimals;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (roomId != null ? !roomId.equals(room.roomId) : room.roomId != null) return false;
        return title != null ? title.equals(room.title) : room.title == null;
    }

    @Override
    public int hashCode() {
        int result = roomId != null ? roomId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}

