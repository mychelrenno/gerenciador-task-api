package br.com.gerenciadorTasks.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gerenciadorTasks.model.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query(value = "select "
			+ "t.id, t.titulo, t.descricao, t.data_cadastro, data_editado, t.data_removido, t.data_concluido, t.status "
			+ "from taskmanager.task t where t.data_removido is null ", nativeQuery = true)
	List<Task> buscarTasksComDataRemovidoNull();
}
