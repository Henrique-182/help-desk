package br.com.hd.unittests.services.chat.v1;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.hd.data.vo.chat.message.v1.MessageCreationVO;
import br.com.hd.data.vo.chat.message.v1.MessageUpdateVO;
import br.com.hd.data.vo.chat.message.v1.MessageVO;
import br.com.hd.exceptions.generic.v1.InvalidArgumentsException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.chat.v1.MessageMapper;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.chat.message.v1.Message;
import br.com.hd.model.chat.message.v1.MessageType;
import br.com.hd.repositories.chat.v1.MessageRepository;
import br.com.hd.services.chat.v1.MessageService;
import br.com.hd.unittests.mocks.auth.v1.UserMock;
import br.com.hd.unittests.mocks.chat.message.v1.MessageCreationVOMock;
import br.com.hd.unittests.mocks.chat.message.v1.MessageMock;
import br.com.hd.unittests.mocks.chat.message.v1.MessageUpdateVOMock;
import br.com.hd.unittests.mocks.chat.message.v1.RoomMssgMock;
import br.com.hd.unittests.mocks.chat.message.v1.UserMssgMock;
import br.com.hd.util.service.v1.ServiceUtil;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
	
	@Autowired
	@InjectMocks
	MessageService service;
	
	@Mock
	MessageRepository repository;
	
	@Mock
	MessageMapper mapper;
	
	@Mock
	ServiceUtil util;
	
	@Test
	void testFindById() {
		
		Long id = 1L;
		
		Message mockEntity = MessageMock.entity(id);
		MessageVO mockVO = MessageMock.vo(id);
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		MessageVO persistedMessage = service.findById(id);
		
		assertNotNull(persistedMessage);
		
		assertEquals(1L, persistedMessage.getKey());
		assertEquals(MessageType.TEXT, persistedMessage.getType());
		assertEquals("Content1", persistedMessage.getContent());
		assertEquals("Username1", persistedMessage.getUser().getUsername());
		assertEquals(new Date(id), persistedMessage.getCreateDatetime());
		assertNull(persistedMessage.getUpdateDatetime());
		assertNull(persistedMessage.getDeleteDatetime());
		
		assertEquals("</v1/message/1>;rel=\"self\"", persistedMessage.getLinks().toString());
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		
		Long id = 0L;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testCreate() {
		
		User currentUser = UserMock.entity();
		
		MessageCreationVO data = MessageCreationVOMock.vo();
		
		Message mockEntity = MessageMock.entity();
		Message persistedEntity = mockEntity;
		
		MessageVO mockVO = MessageMock.vo();
		
		when(util.returnRoomIfExists(data.getRoomKey())).thenReturn(RoomMssgMock.entity());
		when(util.returnUserIfPresentInRoom(currentUser.getId(), data.getRoomKey())).thenReturn(UserMssgMock.entity());
		when(repository.save(any(Message.class))).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		MessageVO createdMessage = service.create(currentUser, data);
		
		assertNotNull(createdMessage);
		
		assertEquals(0L, createdMessage.getKey());
		assertEquals(MessageType.TEXT, createdMessage.getType());
		assertEquals("Content0", createdMessage.getContent());
		assertEquals("Username0", createdMessage.getUser().getUsername());
		assertEquals(new Date(0), createdMessage.getCreateDatetime());
		assertNull(createdMessage.getUpdateDatetime());
		assertNull(createdMessage.getDeleteDatetime());
		
		assertEquals("</v1/message/0>;rel=\"self\"", createdMessage.getLinks().toString());
	}
	
	@Test
	void testUpdateById() {
		
		Long id = 0L;
		
		User currentUser = UserMock.entity();
		
		MessageUpdateVO data = MessageUpdateVOMock.vo();
		
		Message mockEntity = MessageMock.entity();
		mockEntity.setUpdateDatetime(new Date());
		
		Message persistedEntity = mockEntity;
		
		MessageVO mockVO = MessageMock.vo();
		mockVO.setUpdateDatetime(new Date());
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(mockEntity));
		when(repository.save(any(Message.class))).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		MessageVO updatedMessage = service.updateById(id, currentUser, data);
		
		assertNotNull(updatedMessage);
		
		assertEquals(0L, updatedMessage.getKey());
		assertEquals(MessageType.TEXT, updatedMessage.getType());
		assertEquals("Content0", updatedMessage.getContent());
		assertEquals("Username0", updatedMessage.getUser().getUsername());
		assertEquals(new Date(0), updatedMessage.getCreateDatetime());
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(updatedMessage.getUpdateDatetime())
		);
		assertNull(updatedMessage.getDeleteDatetime());
		
		assertEquals("</v1/message/0>;rel=\"self\"", updatedMessage.getLinks().toString());
	}
	
	@Test
	void testUpdateByIdWithInvalidArgumentsException() {
		
		Long id = 0L;
		
		User currentUser = UserMock.entity(1L);
		
		MessageUpdateVO data = MessageUpdateVOMock.vo();
		
		Message mockEntity = MessageMock.entity();
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(mockEntity));
		
		Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
			service.updateById(id, currentUser, data);
		});
		
		String expectedMessage = "The user (Username1) can't update the message because he didn't send it !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testSoftDeleteById() {
		
		Long id = 0L;
		
		User currentUser = UserMock.entity();
		
		Message mockEntity = MessageMock.entity();
		mockEntity.setDeleteDatetime(new Date());
		
		Message persistedEntity = mockEntity;
		
		MessageVO mockVO = MessageMock.vo();
		mockVO.setDeleteDatetime(new Date());
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(mockEntity));
		when(repository.save(any(Message.class))).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		MessageVO deletedMessage = service.softDeleteById(id, currentUser);
		
		assertNotNull(deletedMessage);
		
		assertEquals(0L, deletedMessage.getKey());
		assertEquals(MessageType.TEXT, deletedMessage.getType());
		assertEquals("Content0", deletedMessage.getContent());
		assertEquals("Username0", deletedMessage.getUser().getUsername());
		assertEquals(new Date(0), deletedMessage.getCreateDatetime());
		assertNull(deletedMessage.getUpdateDatetime());
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(deletedMessage.getDeleteDatetime())
		);
		
		assertEquals("</v1/message/0>;rel=\"self\"", deletedMessage.getLinks().toString());
	}
	
	@Test
	void testSoftDeleteByIdWithInvalidArgumentsException() {
		
		Long id = 0L;
		
		User currentUser = UserMock.entity(1L);
		
		Message mockEntity = MessageMock.entity();
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(mockEntity));
		
		Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
			service.softDeleteById(id, currentUser);
		});
		
		String expectedMessage = "The user (Username1) can't delete the message because he didn't send it !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}

}
