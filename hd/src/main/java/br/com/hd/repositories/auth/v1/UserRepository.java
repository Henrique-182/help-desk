package br.com.hd.repositories.auth.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.hd.model.auth.v1.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(@Param("username") String username);
	
}
