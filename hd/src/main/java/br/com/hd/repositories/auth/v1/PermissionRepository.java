package br.com.hd.repositories.auth.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hd.model.auth.v1.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}
