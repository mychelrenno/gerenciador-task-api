package br.com.minhasFinancas.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.minhasFinancas.model.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query(value = "select "
			+ "t.id, t.titulo, t.descricao, t.data_cadastro, data_editado, t.data_removido, t.data_concluido, t.status "
			+ "from financas.task t where t.data_removido is null ", nativeQuery = true)
	List<Task> buscarTasksComDataRemovidoNull();
}
