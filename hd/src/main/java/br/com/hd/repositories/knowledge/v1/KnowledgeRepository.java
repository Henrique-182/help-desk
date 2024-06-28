package br.com.hd.repositories.knowledge.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hd.model.knowledge.v1.Knowledge;

@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

}
