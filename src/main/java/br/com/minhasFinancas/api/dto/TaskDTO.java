package br.com.minhasFinancas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

	private Long id;
	private String titulo;
	private String descricao;
	private String status;
	private String dataCadastro;
	private String dataEditado;
	private String dataRemovido;
	private String dataConcluido;
}
