package br.com.hd.mappers.knowledge.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hd.data.vo.knowledge.v1.KnowledgeVO;
import br.com.hd.model.knowledge.v1.Knowledge;

@Mapper(componentModel = "spring")
public interface KnowledgeMapper {
	
	@Mapping(source = "id", target = "key")
	KnowledgeVO toVO(Knowledge entity);
	
	@Mapping(source = "id", target = "key")
	List<KnowledgeVO> toVOList(List<Knowledge> entityList);
	
	@Mapping(source = "key", target = "id")
	Knowledge toEntity(KnowledgeVO vo);

	@Mapping(source = "key", target = "id")
	List<Knowledge> toEntityList(List<KnowledgeVO> voList);
	 

}
