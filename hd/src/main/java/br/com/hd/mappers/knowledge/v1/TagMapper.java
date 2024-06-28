package br.com.hd.mappers.knowledge.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hd.data.vo.knowledge.v1.TagVO;
import br.com.hd.model.knowledge.v1.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
	
	@Mapping(source = "id", target = "key")
	TagVO toVO(Tag entity);
	
	@Mapping(source = "id", target = "key")
	List<TagVO> toVOList(List<Tag> entityList);
	
	@Mapping(source = "key", target = "id")
	Tag toEntity(TagVO vo);

	@Mapping(source = "key", target = "id")
	List<Tag> toEntityList(List<TagVO> voList);

}
