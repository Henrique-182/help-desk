package br.com.hd.services.chat.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.hd.controllers.chat.v1.SectorController;
import br.com.hd.data.vo.chat.v1.SectorVO;
import br.com.hd.data.vo.chat.v1.SimpleSectorVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.chat.v1.SectorMapper;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.auth.v1.UserType;
import br.com.hd.model.chat.sector.v1.Sector;
import br.com.hd.repositories.chat.v1.SectorCustomRepository;
import br.com.hd.repositories.chat.v1.SectorRepository;

@Service
public class SectorService {

	@Autowired
	private SectorRepository repository;
	
	@Autowired
	private SectorCustomRepository customRepository;
	
	@Autowired
	private SectorMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<SectorVO> assembler;
	
	@SuppressWarnings("unchecked")
	public PagedModel<EntityModel<SectorVO>> findCustomPageable(Map<String, Object> queryParams, Pageable pageable) {
		
		Map<String, Object> resultMap = customRepository.findCustomPageable(queryParams, pageable);
		
		List<SectorVO> voList = mapper
				.toVOList((List<Sector>) resultMap.get("resultList"))
				.stream()
				.map(k -> addLinkSelfRel(k))
				.toList();
		
		return assembler.toModel(
				new PageImpl<>(
						voList,
						pageable, 
						(long) resultMap.get("totalElements")
					)
				);
	}
	
	public SectorVO findById(Long id) {
		
		Sector persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public List<SimpleSectorVO> findSectorsByUser(User currentUser) {
		
		return currentUser.getType() == UserType.Customer
				? mapper
					.toSimpleVOList(repository.findSectorsByCustomer(currentUser.getId()))
					.stream()
					.map(s -> addLinkSelfRel(s))
					.toList()
				: mapper
					.toSimpleVOList(repository.findSectorsByEmployee(currentUser.getId()))
					.stream()
					.map(s -> addLinkSelfRel(s))
					.toList()
				;
	}
	
	public SectorVO create(SectorVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Sector createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public SectorVO updateById(Long id, SectorVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Sector entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		entity.setDescription(data.getDescription());
		entity.setCustomers(data.getCustomers());
		entity.setEmployees(data.getEmployees());
		
		Sector updatedEntity = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(updatedEntity));
	}
	
	public void deleteById(Long id) {
		Sector entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		
		repository.delete(entity);
	}
	
	private SectorVO addLinkSelfRel(SectorVO vo) {
		return vo.add(linkTo(methodOn(SectorController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private SimpleSectorVO addLinkSelfRel(SimpleSectorVO vo) {
		return vo.add(linkTo(methodOn(SectorController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private SectorVO addLinkVOList(SectorVO vo) {
		return vo.add(linkTo(methodOn(SectorController.class).findCustomPageable(0, 10, "description", "asc", null, null, null)).withRel("sectorVOList").expand());
	} 
	
}
