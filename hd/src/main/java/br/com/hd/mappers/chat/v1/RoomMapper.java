package br.com.hd.mappers.chat.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hd.data.vo.chat.room.v1.RoomVO;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.chat.room.v1.Room;
import br.com.hd.model.chat.room.v1.SectorRoom;
import br.com.hd.model.chat.room.v1.UserRoom;
import br.com.hd.model.chat.sector.v1.Sector;

@Mapper(componentModel = "spring")
public interface RoomMapper {
	
	@Mapping(source = "id", target = "key")
	RoomVO toVO(Room entity);
	
	@Mapping(source = "key", target = "id")
	Room toEntity(RoomVO vo);
	
	@Mapping(source = "id", target = "key")
	List<RoomVO> toVOList(List<Room> entityList);
	
	SectorRoom toSectorRoom(Sector entity);
	
	UserRoom toUserRoom(User entity);

}
