package br.com.hd.services.chat.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hd.controllers.chat.v1.MessageController;
import br.com.hd.data.vo.chat.message.v1.MessageCreationVO;
import br.com.hd.data.vo.chat.message.v1.MessageUpdateVO;
import br.com.hd.data.vo.chat.message.v1.MessageVO;
import br.com.hd.exceptions.generic.v1.InvalidArgumentsException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.chat.v1.MessageMapper;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.chat.message.v1.Message;
import br.com.hd.model.chat.message.v1.RoomMssg;
import br.com.hd.model.chat.message.v1.UserMssg;
import br.com.hd.repositories.chat.v1.MessageRepository;
import br.com.hd.util.service.v1.ServiceUtil;
import jakarta.transaction.Transactional;

@Service
public class MessageService {

	@Autowired
	private MessageRepository repository;
	
	@Autowired
	private ServiceUtil util;
	
	@Autowired
	private MessageMapper mapper;
	
	public MessageVO findById(Long id) {
		
		Message persistedMessage = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		return addLinkSelfRel(mapper.toVO(persistedMessage));
	}
	
	@Transactional
	public MessageVO create(User currentUser, MessageCreationVO data) {
		
		Message message = new Message();
		
		if (util.roomExists(data.getRoomKey())) {
			message.setRoom(new RoomMssg(data.getRoomKey()));
		}
		if (util.userPresentInRoom(currentUser.getId(), data.getRoomKey())) {
			message.setUser(new UserMssg(currentUser.getId()));
		}

		message.setType(data.getType());
		message.setContent(data.getContent());
		message.setCreateDatetime(new Date());
		
		Message createdMessage = repository.save(message);
		
		return addLinkSelfRel(mapper.toVO(createdMessage));
	}
	
	@Transactional
	public MessageVO updateById(Long id, User currentUser, MessageUpdateVO data) {
		
		Message persistedMessage = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		if (userSentTheMessage(persistedMessage, currentUser, "update")) {
			persistedMessage.setContent(data.getContent());
			persistedMessage.setUpdateDatetime(new Date());
		}
		
		Message updatedMessage = repository.save(persistedMessage);
		
		return addLinkSelfRel(mapper.toVO(updatedMessage));
	}
	
	@Transactional
	public MessageVO softDeleteById(Long id, User currentUser) {
		
		Message persistedMessage = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
		
		if (userSentTheMessage(persistedMessage, currentUser, "delete")) {
			persistedMessage.setDeleteDatetime(new Date());
		}
		
		Message deletedMessage = repository.save(persistedMessage);
		
		return addLinkSelfRel(mapper.toVO(deletedMessage));
	}
	
	private MessageVO addLinkSelfRel(MessageVO vo) {
		return vo.add(linkTo(methodOn(MessageController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private Boolean userSentTheMessage(Message message, User user, String methodIfError) {
		
		Boolean userSentTheMessage = message.getUser().getKey() == user.getId();;
		
		if (userSentTheMessage) return true;
		else throw new InvalidArgumentsException("The user (" + user.getUsername() + ") can't " + methodIfError + " the message because he didn't send it !");
	}
	
}
