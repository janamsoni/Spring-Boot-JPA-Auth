package com.croods.springbootjpaauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.croods.springbootjpaauth.vo.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

}
