package com.semillero.semillero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.semillero.semillero.models.UserRols;

public interface IUserRoleRepository extends JpaRepository<UserRols, Long> {

    @Query("SELECT ur FROM UserRols ur WHERE ur.user.id = :userId")
    List<UserRols> getRolesByUser(@Param("userId") Long userId);    

}
