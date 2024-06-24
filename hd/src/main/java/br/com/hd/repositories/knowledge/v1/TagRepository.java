package br.com.hd.repositories.knowledge.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.hd.model.knowledge.v1.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>, PagingAndSortingRepository<Tag, Integer> {

	Page<Tag> findPageableByDescriptionContainingIgnoreCase(@Param("description") String description, Pageable pageable);
	
}
