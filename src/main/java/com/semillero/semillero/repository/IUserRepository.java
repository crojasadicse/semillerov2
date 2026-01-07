package com.semillero.semillero.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.semillero.semillero.models.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> getByUserName(@Param("username") String username);    

}
