package br.com.citadini.tasks.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucascitadini.estudospring.resources.utils.URL;

import br.com.citadini.tasks.domain.Task;
import br.com.citadini.tasks.services.TaskService;

@RestController
@RequestMapping(value = "/api/task")
public class TaskResource {
	
	@Autowired
	private TaskService service;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Task obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestParam(value="id") Integer id, @RequestParam(value="done") boolean done) {
		Task obj = new Task();
		obj.setId(id);
		obj.setDone(done);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Task> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Task>> findByDescriptionLike(@RequestParam(value="description", defaultValue = "") String description) {
		String descriptionDecode = URL.decodeParam(description);
		List<Task> list = service.findByDescription(descriptionDecode);
		return ResponseEntity.ok(list);
	}
	
}
