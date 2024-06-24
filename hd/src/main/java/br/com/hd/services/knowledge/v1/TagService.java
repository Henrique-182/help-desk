package br.com.hd.services.knowledge.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.hd.data.vo.knowledge.v1.TagVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.knowledge.v1.TagMapper;
import br.com.hd.model.knowledge.v1.Tag;
import br.com.hd.repositories.knowledge.v1.TagRepository;

@Service
public class TagService {

	@Autowired
	private TagRepository repository;
	
	@Autowired
	private TagMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<TagVO> assembler;
	
	public PagedModel<EntityModel<TagVO>> findCustomPageable(String description, Pageable pageable) {
		Page<Tag> entityPage = repository.findPageableByDescriptionContainingIgnoreCase(description, pageable);
		
		Page<TagVO> voPage = entityPage.map(t -> mapper.toVO(t));
		
		return assembler.toModel(voPage);
	}
	
	public TagVO findById(Long id) {
		Tag persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return mapper.toVO(persistedEntity);
	}
	
	public TagVO create(TagVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Tag createdEntity = repository.save(mapper.toEntity(data));
		
		return mapper.toVO(createdEntity);
	}
	
	public TagVO updateById(Long id, TagVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Tag entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setDescription(data.getDescription());
		
		Tag updatedEntity = repository.save(entity);
		
		return mapper.toVO(updatedEntity);
	}
	
	public void deleteById(Long id) {
		Tag entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
}
