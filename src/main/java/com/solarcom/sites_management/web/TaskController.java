package com.solarcom.sites_management.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solarcom.sites_management.domain.Task;
import com.solarcom.sites_management.repo.TaskRepository;
import com.solarcom.sites_management.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskService taskService;

	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		
		Task savedTask = taskRepository.save(task);
		return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
	}

	@GetMapping
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable("id") String id) {
		Optional<Task> task = taskRepository.findById(id);
		return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	@GetMapping("/title/{title}")
	public List<Task> getTaskByTitle(@PathVariable("title") String title) {
		List<Task> tasks = taskRepository.findByTitle(title);
		return  tasks;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable("id") String id, @RequestBody Task task) {
		if (!taskRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		task.setId(id);
//		Task updatedTask = taskRepository.save(task);
		
		Task updatedTask =  taskService.updateTask(id, task);
		return ResponseEntity.ok(updatedTask);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable("id") String id) {
		if (!taskRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		taskRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
