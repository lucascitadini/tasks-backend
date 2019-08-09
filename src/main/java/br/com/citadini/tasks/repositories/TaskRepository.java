package br.com.citadini.tasks.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.citadini.tasks.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Transactional(readOnly= true)
	List<Task> findByDescriptionContainingIgnoreCaseOrderByCreatedAt(String description);
	
}
