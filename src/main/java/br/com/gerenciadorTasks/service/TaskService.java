package br.com.gerenciadorTasks.service;

import java.util.List;
import java.util.Optional;

import br.com.gerenciadorTasks.model.entity.Task;
import br.com.gerenciadorTasks.model.enums.StatusTask;

public interface TaskService {

	Task salvar(Task task);
	
	Task atualizar(Task task);
	
	void deletar(Task lancamento);
	
	List<Task> buscar(Task taskFiltro);
	
	void atualizarStatus(Task task, StatusTask eStatusTask);
	
	void validar(Task task);
	
	Optional<Task> buscarPorId(Long id);

}
