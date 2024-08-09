package br.com.hd.repositories.chat.v1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.hd.model.chat.sector.v1.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
	
	@Query(
		" SELECT SECT FROM Sector SECT "
	  + " WHERE ELEMENT(SECT.customers).id = :id"
	)
	List<Sector> findSectorsByCustomer(@Param("id") Long id);
	
	@Query(
		" SELECT SECT FROM Sector SECT "
	  + " WHERE ELEMENT(SECT.employees).id = :id"
	)
	List<Sector> findSectorsByEmployee(@Param("id") Long id);

}
