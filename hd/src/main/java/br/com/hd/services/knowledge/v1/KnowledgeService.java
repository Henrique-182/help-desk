package br.com.hd.services.knowledge.v1;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.hd.data.vo.knowledge.v1.KnowledgeVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.knowledge.v1.KnowledgeMapper;
import br.com.hd.model.knowledge.v1.Knowledge;
import br.com.hd.repositories.knowledge.v1.KnowledgeCustomRepository;
import br.com.hd.repositories.knowledge.v1.KnowledgeRepository;

@Service
public class KnowledgeService {

	@Autowired
	private KnowledgeRepository repository;
	
	@Autowired
	private KnowledgeCustomRepository customRepository;
	
	@Autowired
	private KnowledgeMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<KnowledgeVO> assembler;
	
	@SuppressWarnings("unchecked")
	public PagedModel<EntityModel<KnowledgeVO>> findCustomPageable(Map<String, Object> queryParams, Pageable pageable) {
		Map<String, Object> resultMap = customRepository.findCustomPageable(queryParams, pageable);
		
		List<KnowledgeVO> voList = mapper.toVOList((List<Knowledge>) resultMap.get("resultList"));
		
		return assembler.toModel(
				new PageImpl<>(
						voList,
						pageable, 
						(long) resultMap.get("totalElements")
					)
				);
	}
	
	public KnowledgeVO findById(Long id) {
		Knowledge persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return mapper.toVO(persistedEntity);
	}
	
	public KnowledgeVO create(KnowledgeVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Knowledge createdEntity = repository.save(mapper.toEntity(data));
		
		return mapper.toVO(createdEntity);
	}
	
	public KnowledgeVO updateById(Long id, KnowledgeVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Knowledge entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setTitle(data.getTitle());
		entity.setSoftware(data.getSoftware());
		entity.setContent(data.getContent());
		entity.setTags(data.getTags());
		
		Knowledge updatedEntity = repository.save(entity);
		
		return mapper.toVO(updatedEntity);
	}
	
	public void deleteById(Long id) {
		Knowledge entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
}
