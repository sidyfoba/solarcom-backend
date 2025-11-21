package com.slcm.solarcom.sites_management.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slcm.sites_management.domain.Recurrence;
import com.slcm.sites_management.domain.Task;
import com.slcm.solarcom.sites_management.repo.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Task updateTask(String taskId, Task updatedTask) {
		Task existingTask = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

		// Update the existing task
		existingTask.setTitle(updatedTask.getTitle());
		existingTask.setDescription(updatedTask.getDescription());
		existingTask.setDueDate(updatedTask.getDueDate());
		existingTask.setStatus(updatedTask.getStatus());
		existingTask.setRecurrence(updatedTask.getRecurrence());
		existingTask.setRecurrenceValue(updatedTask.getRecurrenceValue());
		existingTask.setActions(updatedTask.getActions());

		// Handle task recurrence if completed
		if ("completed".equals(updatedTask.getStatus()) && updatedTask.getRecurrence() != null) {
			createRecurringTasks(existingTask);
		}

		// Save the updated task
		Task savedTask = taskRepository.save(existingTask);
		return savedTask;
	}

	private void createRecurringTasks(Task completedTask) {
//        List<Task> newTasks = new ArrayList<>();
		LocalDate today = LocalDate.now();
		LocalDate dueDate = completedTask.getDueDate();
		int recurrenceValue = completedTask.getRecurrenceValue();

//        for (int i = 1; i <= recurrenceValue; i++) {
		LocalDate newDueDate = calculateCurrentDueDate(dueDate, completedTask.getRecurrence(),
				completedTask.getRecurrenceValue());
		Task newTask = new Task();
		newTask.setTitle(completedTask.getTitle());
		newTask.setDescription(completedTask.getDescription());
		newTask.setDueDate(newDueDate);
		newTask.setNextDueDate(calculateNextDueDate(completedTask.getRecurrence(), completedTask.getRecurrenceValue(),
				today.plusDays(1)));
		newTask.setStatus("not-started"); // New tasks start as not-started
		newTask.setRecurrence(completedTask.getRecurrence());
		newTask.setRecurrenceValue(completedTask.getRecurrenceValue());
		newTask.setParentTaskId(completedTask.getId()); // Link to the parent task
		

		// Set other properties if necessary

//            newTasks.add(newTask);
//        }

		newTask = taskRepository.save(newTask);
		completedTask.setChildTaskId(newTask.getId());
	}

	private LocalDate calculateCurrentDueDate(LocalDate dueDate, Recurrence recurrence, int period) {
		switch (recurrence) {
		case DAILY:
			return dueDate.plusDays(period);
		case WEEKLY:
			return dueDate.plusWeeks(period);
		case MONTHLY:
			return dueDate.plusMonths(period);
		default:
			throw new IllegalArgumentException("Unknown recurrence type: " + recurrence);
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
