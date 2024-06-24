package br.com.hd.services.knowledge.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.hd.data.vo.knowledge.v1.SoftwareVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.knowledge.v1.SoftwareMapper;
import br.com.hd.model.knowledge.v1.Software;
import br.com.hd.repositories.knowledge.v1.SoftwareRepository;

@Service
public class SoftwareService {

	@Autowired
	private SoftwareRepository repository;
	
	@Autowired
	private SoftwareMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<SoftwareVO> assembler;
	
	public PagedModel<EntityModel<SoftwareVO>> findCustomPageable(String description, Pageable pageable) {
		Page<Software> entityPage = repository.findPageableByDescriptionContainingIgnoreCase(description, pageable);
		
		Page<SoftwareVO> voPage = entityPage.map(s -> mapper.toVO(s));
		
		return assembler.toModel(voPage);
	}
	
	public SoftwareVO findById(Long id) {
		Software persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return mapper.toVO(persistedEntity);
	}
	
	public SoftwareVO create(SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software createdEntity = repository.save(mapper.toEntity(data));
		
		return mapper.toVO(createdEntity);
	}
	
	public SoftwareVO updateById(Long id, SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setDescription(data.getDescription());
		
		Software updatedEntity = repository.save(entity);
		
		return mapper.toVO(updatedEntity);
	}
	
	public void deleteById(Long id) {
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
}
