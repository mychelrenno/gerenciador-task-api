package br.com.minhasFinancas.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.minhasFinancas.api.dto.AtualizaStatusDTO;
import br.com.minhasFinancas.api.dto.TaskDTO;
import br.com.minhasFinancas.exception.RegraNegocioException;
import br.com.minhasFinancas.model.entity.Task;
import br.com.minhasFinancas.model.enums.StatusTask;
import br.com.minhasFinancas.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskResource {

	private TaskService service;
	
	public TaskResource(TaskService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody TaskDTO dto ) {
		try {
			Task task = converter(dto);
			task = service.salvar(task);
			return new ResponseEntity(task, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity atualizar( @PathVariable Long id, @RequestBody TaskDTO dto) {
		return service.buscarPorId(id).map( entity -> {
			try {
				Task task = converter(dto);
				task.setId(entity.getId());
				service.atualizar(task);
				return ResponseEntity.ok(task);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> new ResponseEntity("Task não encontrado na base de dados.", HttpStatus.BAD_REQUEST) );
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar ( @PathVariable("id") Long id ) {
		return service.buscarPorId(id).map( entity -> {
			try {
				service.deletar(entity);
				return new ResponseEntity( HttpStatus.NO_CONTENT );
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> new ResponseEntity("Task não encontrado na base de dados.", HttpStatus.BAD_REQUEST) );
	}
	
	@GetMapping
	public ResponseEntity buscar(
			//@RequestParam java.util.Map<String, String> params, aqui todos são opcionais
			//ou
			@RequestParam(value="task", required=false) Long task,
			@RequestParam(value="titulo", required=false) String titulo,
			@RequestParam(value="descricao", required=false) String descricao
			) {
		Task taskFiltro = new Task();
		taskFiltro.setId(task);
		taskFiltro.setTitulo(titulo);
		taskFiltro.setDescricao(descricao);
		
		List<Task> tasks = service.buscar(taskFiltro);
		
		return ResponseEntity.ok(tasks);
	}
	
	@PostMapping("/buscar")
	public ResponseEntity buscarPorId(@RequestBody TaskDTO dto) {
		
		Optional<Task> task = service.buscarPorId(dto.getId());
		if (!task.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Task não encontrada para o id informado.");
		}
		
		return ResponseEntity.ok(task);
	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus( @PathVariable Long id, @RequestBody AtualizaStatusDTO dto ) {
		return service.buscarPorId(id).map( entity -> {
			StatusTask statusSelecionado = StatusTask.valueOf(dto.getStatus());
			if (statusSelecionado == null) {
				ResponseEntity.badRequest().body("Não foi possível atualizar o status da task, envie um status válido.");
			}
			entity.setStatus(statusSelecionado);
			service.atualizar(entity);
			return ResponseEntity.ok(entity);
		}).orElseGet( () -> new ResponseEntity("Task não encontrada na base de dados.", HttpStatus.BAD_REQUEST) );
	}
	
	private Task converter(TaskDTO dto) {

		Task task = new Task();
		task.setId(dto.getId());
		task.setTitulo(dto.getTitulo());
		task.setDescricao(dto.getDescricao());
		
		if (dto.getStatus() != null) {
			task.setStatus(StatusTask.valueOf(dto.getStatus()));
		}
		
		return task;
	}
}
