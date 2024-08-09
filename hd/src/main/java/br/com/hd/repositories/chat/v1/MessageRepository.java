package br.com.hd.repositories.chat.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hd.model.chat.message.v1.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
