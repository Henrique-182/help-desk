package br.com.hd.mappers.knowledge.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hd.data.vo.knowledge.v1.SoftwareVO;
import br.com.hd.model.knowledge.v1.Software;

@Mapper(componentModel = "spring")
public interface SoftwareMapper {
	
	@Mapping(source = "id", target = "key")
	SoftwareVO toVO(Software entity);
	
	@Mapping(source = "id", target = "key")
	List<SoftwareVO> toVOList(List<Software> entityList);
	
	@Mapping(source = "key", target = "id")
	Software toEntity(SoftwareVO vo);

	@Mapping(source = "key", target = "id")
	List<Software> toEntityList(List<SoftwareVO> voList);

}
