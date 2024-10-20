package br.com.hd.mappers.chat.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hd.data.vo.chat.message.v1.MessageVO;
import br.com.hd.model.chat.message.v1.Message;
import br.com.hd.model.chat.message.v1.RoomMssg;
import br.com.hd.model.chat.room.v1.Room;

@Mapper(componentModel = "spring")
public interface MessageMapper {
	
	@Mapping(source = "id", target = "key")
	MessageVO toVO(Message entity);
	
	@Mapping(source = "key", target = "id")
	Message toEntity(MessageVO vo);
	
	@Mapping(source = "id", target = "key")
	List<MessageVO> toVOList(List<Message> entityList);
	
	@Mapping(source = "id", target = "key")
	RoomMssg toRoomMssg(Room entity);
}
