package br.com.hd.mappers.chat.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hd.data.vo.chat.v1.SectorVO;
import br.com.hd.data.vo.chat.v1.SimpleSectorVO;
import br.com.hd.model.chat.v1.Sector;

@Mapper(componentModel = "spring")
public interface SectorMapper {
	
	@Mapping(source = "id", target = "key")
	SectorVO toVO(Sector entity);
	
	@Mapping(source = "id", target = "key")
	SimpleSectorVO toSimpleVO(Sector entity);
	
	@Mapping(source = "key", target = "id")
	Sector toEntity(SectorVO vo);
	
	@Mapping(source = "id", target = "key")
	List<SectorVO> toVOList(List<Sector> entityList);
	
	@Mapping(source = "id", target = "key")
	List<SimpleSectorVO> toSimpleVOList(List<Sector> entityList);

}
