package com.raimundomlj.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.raimundomlj.enums.Channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id_message", name="pk_message"))
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_message")
	private Integer idMessage;
	
	@Column(name = "creation_date")
	private String creationDate;
	
	@Column(name = "author_name")
	private String authorName;

	@Column(name = "message_content")
	private String messageContent;
	
	@Column(name = "channel")
	private Channel channel;
	
	@ManyToOne
	@JoinColumn(name = "id_client_case", foreignKey = @ForeignKey(name = "fk_client_case"))
	private ClientCase clientCase;
}
