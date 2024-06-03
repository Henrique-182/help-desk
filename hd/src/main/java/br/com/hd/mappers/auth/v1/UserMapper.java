package br.com.hd.mappers.auth.v1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hd.data.vo.auth.v1.UserVO;
import br.com.hd.model.auth.v1.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(source = "id", target = "key")
	UserVO toVO(User entity);
	
	@Mapping(source = "key", target = "id")
	User toEntity(UserVO vo);

}
