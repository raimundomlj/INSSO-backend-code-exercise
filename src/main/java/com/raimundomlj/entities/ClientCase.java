package com.raimundomlj.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id_client_case", name="pk_client_case"))
public class ClientCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_client_case" , unique = true, nullable = false)
	private Integer idClientCase;
	
	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "case_creation_date")
	private String caseCreationDate;
	
	@Column(name = "reference")
	private String reference;
	
	@OneToMany(mappedBy = "clientCase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Message> messagesList;
}
