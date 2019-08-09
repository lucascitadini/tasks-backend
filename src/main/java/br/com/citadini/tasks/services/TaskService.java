package br.com.citadini.tasks.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.citadini.tasks.domain.Task;
import br.com.citadini.tasks.repositories.TaskRepository;
import br.com.citadini.tasks.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {

	@Autowired
	private TaskRepository repo;

	public Task insert(Task obj) {
		obj.setId(null);
		obj.setCreatedAt(new Date());
		obj = repo.save(obj);
		return obj;
	}

	public Task update(Task obj) {
		Task newObj = find(obj.getId());
		newObj.setDone(obj.getDone());
		return repo.save(newObj);
	}
	
	public Task find(Integer id) {		
		Optional<Task> obj = repo.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException(
				String.format("Objeto não encontrado: Id: %d, Tipo: %s", id, Task.class.getName())));
	}
	
	public void delete(Integer id) {
		find(id);
		repo.deleteById(id);
	}
	
	public List<Task> findByDescription(String description) {
		return repo.findByDescriptionContainingIgnoreCaseOrderByCreatedAt(description);
	}
}
