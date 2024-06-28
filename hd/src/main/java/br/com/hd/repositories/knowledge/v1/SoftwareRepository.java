package br.com.hd.repositories.knowledge.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.hd.model.knowledge.v1.Software;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long>, PagingAndSortingRepository<Software, Long> {

	Page<Software> findPageableByDescriptionContainingIgnoreCase(@Param("description") String description, Pageable pageable);
	
}
