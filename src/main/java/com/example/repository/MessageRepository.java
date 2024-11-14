package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("FROM Message WHERE postedBy = :postedBy")
    List<Message> findByFk(@Param("postedBy") Integer postedBy);
}
