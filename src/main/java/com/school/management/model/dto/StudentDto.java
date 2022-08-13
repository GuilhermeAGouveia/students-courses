package com.school.management.model.dto;

import com.school.management.model.Course;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class StudentDto {

	private Long id;

	private String name;

	private String address;

	private Set<CourseDto> courses;

	private Timestamp createdAt;

	private Timestamp updatedAt;


	public StudentDto(){}
	public StudentDto(String name, String address) {
		Timestamp ts = Timestamp.from(Instant.now());
		this.id = 0L;
		this.name = name;
		this.address = address;
		this.createdAt = ts;
		this.updatedAt = ts;
	}

	public StudentDto(Long id, String name, String address, Timestamp createdAt, Timestamp updatedAt) {
		this(name, address);
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public StudentDto(Long id, String name, String address, Set<Course> courses, Timestamp createdAt, Timestamp updatedAt) {
		this(name, address);
		this.id = id;
		this.courses = courses.stream()
				.map(course ->
						new CourseDto(course.getId(), course.getName(), course.getUpdatedAt(), course.getCreatedAt()))
				.collect(Collectors.toSet());
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
