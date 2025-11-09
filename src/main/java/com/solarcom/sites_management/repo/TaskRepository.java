package com.solarcom.sites_management.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.solarcom.sites_management.domain.Task;

public interface TaskRepository extends MongoRepository<Task, String>, PagingAndSortingRepository<Task, String> {

	List<Task> findByTitle(String title);
}
