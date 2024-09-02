package br.com.hd.repositories.chat.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.hd.model.chat.room.v1.Room;
import br.com.hd.model.chat.room.v1.RoomStatus;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	Optional<Room> findByCode(@Param("code") Integer code);
	
	@Query(
		" 	SELECT COUNT(ROOM) FROM Room ROOM "
	  + "    WHERE ROOM.employee.key = :employeeKey "
	  + "      AND ROOM.id = :roomKey "
	)
	long countByUserKeyAndRoomKey(@Param("employeeKey") Long employeeKey, @Param("roomKey") Long roomKey);
	
	@Query(
		" 	SELECT ROOM FROM Room ROOM "
	  + "    WHERE ROOM.sector.key = :sectorKey "
	  + "      AND ROOM.status IN :statusList "
	)
	List<Room> findBySectorKeyAndStatusIn(@Param("sectorKey") Long sectorKey, @Param("statusList") List<RoomStatus> statusList);
	
	@Query(
		" 	SELECT ROOM FROM Room ROOM "
	  + "    WHERE ROOM.sector.key = :sectorKey "
	  + "      AND ROOM.employee.key = :employeeKey "
	  + "      AND ROOM.status IN :statusList "
	)
	List<Room> findBySectorKeyAndEmployeeKeyAndStatusIn(@Param("sectorKey") Long sectorKey, @Param("employeeKey") Long employeeKey, @Param("statusList") List<RoomStatus> status);
	
	@Query(
		" 	SELECT ROOM.code FROM Room ROOM "
	  + " ORDER BY ROOM.code DESC"
	  + "    LIMIT 1 "
	)
	int findMaxCode();
}
