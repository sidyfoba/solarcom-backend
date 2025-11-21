package com.slcm.solarcom.sites_management.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.slcm.sites_management.domain.Recurrence;
import com.slcm.sites_management.domain.Task;
import com.slcm.solarcom.sites_management.repo.TaskRepository;

@Service
public class TaskSchedulerService {

	private final TaskRepository taskRepository;

	public TaskSchedulerService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void createRecurringTasks() {
		LocalDate today = LocalDate.now();
		List<Task> tasks = taskRepository.findAll();

		for (Task task : tasks) {
			if (task.getRecurrence() != null && task.getNextDueDate().isEqual(today)) {
				Task newTask = new Task();
				newTask.setTitle(task.getTitle());
				newTask.setDescription(task.getDescription());
				newTask.setRecurrence(task.getRecurrence());
				newTask.setRecurrenceValue(task.getRecurrenceValue());
				newTask.setDueDate(calculateNextDueDate(task.getRecurrence(), task.getRecurrenceValue(), today));
				newTask.setNextDueDate(
						calculateNextDueDate(task.getRecurrence(), task.getRecurrenceValue(), today.plusDays(1)));
				newTask.setStatus("open");
				newTask.setParentTaskId(task.getId()); // Link to the parent task
				taskRepository.save(newTask);
			}
		}
	}

	private LocalDate calculateNextDueDate(Recurrence recurrence, Integer value, LocalDate currentDueDate) {
		switch (recurrence) {
		case DAILY:
			return currentDueDate.plusDays(value);
		case WEEKLY:
			return currentDueDate.plusWeeks(value);
		case MONTHLY:
			return currentDueDate.plusMonths(value);
		default:
			return currentDueDate;
		}
	}
}
