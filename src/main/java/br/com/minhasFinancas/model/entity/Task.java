package br.com.minhasFinancas.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import br.com.minhasFinancas.model.enums.StatusTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task", schema = "financas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataCadastro;
	
	@Column(name = "data_editado")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataEditado;
	
	@Column(name = "data_removido")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataRemovido;
	
	@Column(name = "data_concluido")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataConcluido;
	
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusTask status;
}
