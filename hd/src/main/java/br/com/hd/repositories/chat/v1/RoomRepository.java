package br.com.hd.repositories.chat.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hd.model.chat.room.v1.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
