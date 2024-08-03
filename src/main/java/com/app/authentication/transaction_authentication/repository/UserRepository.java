package com.app.authentication.transaction_authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.authentication.transaction_authentication.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    @Query(value ="select * from user_details where username = :username and password = :password",nativeQuery = true)
    public User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
    
}
