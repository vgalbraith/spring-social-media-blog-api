package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Query to retrieve all messages by a particluar user, given their account_id.
     * @param account_id
     * @return A list of all applicable messages.
     */
    @Query("FROM Message WHERE posted_by = :account_id")
    List<Message> findAllByAccountId(@Param("account_id") int account_id);
}
