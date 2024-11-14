package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("FROM Account WHERE username = :username")
    List<Account> findUsername(@Param("username") String username);
    @Query("FROM Account WHERE username = :username AND password = :password")
    List<Account> findLogin(@Param("username") String username, @Param("password") String password);
}
