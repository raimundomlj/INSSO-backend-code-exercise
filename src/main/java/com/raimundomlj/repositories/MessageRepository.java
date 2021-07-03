package com.raimundomlj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raimundomlj.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

	@Query("SELECT m "
			+ "FROM Message m "
			+ "JOIN FETCH m.clientCase c "
			+ "WHERE c.idClientCase = :idClientCase ")
	public List<Message> getMessageByClientCase(@Param("idClientCase") Integer idClientCase);
}
