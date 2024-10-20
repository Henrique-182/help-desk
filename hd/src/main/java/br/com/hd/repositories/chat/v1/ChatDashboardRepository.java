package br.com.hd.repositories.chat.v1;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.hd.model.chat.room.v1.Room;

@Repository
public interface ChatDashboardRepository extends JpaRepository<Room, Long> {

	@Query(value = "SELECT * FROM CHAT.FUNC_SECTOR_MONTH_QUANTITY_PLPGSQL(:year)", nativeQuery = true)
	List<Object[]> findSectorMonthQuantityByYear(@Param("year") Integer year);
	
	@Query(value = "SELECT * FROM CHAT.func_room_priority_quantity_plpgsql(:initialDate, :endDate)", nativeQuery = true)
	List<Object[]> findRoomPriorityQuantityByPeriod(@Param("initialDate") Date initialDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT * FROM CHAT.func_room_employee_quantity_plpgsql(:initialDate, :endDate)", nativeQuery = true)
	List<Object[]> findRoomEmployeeQuantityByPeriod(@Param("initialDate") Date initialDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT * FROM CHAT.func_room_customer_quantity_plpgsql(:initialDate, :endDate)", nativeQuery = true)
	List<Object[]> findRoomCustomerQuantityByPeriod(@Param("initialDate") Date initialDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT * FROM CHAT.func_room_status_quantity_plpgsql(:initialDate, :endDate)", nativeQuery = true)
	List<Object[]> findRoomStatusQuantityByPeriod(@Param("initialDate") Date initialDate, @Param("endDate") Date endDate);
	
}
