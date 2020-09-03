package br.com.gerenciadorTasks.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gerenciadorTasks.exception.RegraNegocioException;
import br.com.gerenciadorTasks.model.entity.Task;
import br.com.gerenciadorTasks.model.enums.StatusTask;
import br.com.gerenciadorTasks.model.repository.TaskRepository;
import br.com.gerenciadorTasks.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	private TaskRepository repository;
	
	public TaskServiceImpl(TaskRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Task salvar(Task task) {
		validar(task);
		return repository.save(task);
	}

	@Override
	@Transactional
	public Task atualizar(Task task) {
		Objects.requireNonNull(task.getId());
		validar(task);
		return repository.save(task);
	}

	@Override
	@Transactional
	public void deletar(Task task) {
		Objects.requireNonNull(task.getId());
		task.setDataRemovido( LocalDate.now() );
		atualizar(task);
	}

	@Override
	@Transactional( readOnly = true )
	public List<Task> buscar(Task taskFiltro) {
		Example example = Example.of( taskFiltro,
				ExampleMatcher.matching()
				.withIgnoreCase().withStringMatcher( StringMatcher.CONTAINING ) );
		
//		return repository.findAll(example);
		return repository.buscarTasksComDataRemovidoNull();
	}
	
	@Override
	public void atualizarStatus(Task task, StatusTask eStatusTask) {
		task.setStatus(eStatusTask);
		atualizar(task);
	}

	@Override
	public void validar(Task task) {
		if (task.getTitulo() == null || task.getTitulo().trim().equals("")) {
			throw new RegraNegocioException("Informe um título válido.");
		}
		if (task.getStatus() == null) {
			throw new RegraNegocioException("Informe um status da task.");
		}
	}

	@Override
	public Optional<Task> buscarPorId(Long id) {
		return repository.findById(id);
	}

}
