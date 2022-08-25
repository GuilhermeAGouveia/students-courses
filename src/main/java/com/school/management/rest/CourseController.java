package com.school.management.rest;

import com.school.management.model.dto.CourseDto;
import com.school.management.model.dto.StudentDto;
import com.school.management.service.CourseService;
import com.school.management.service.StudentCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/courses")
public class CourseController {

	private final CourseService courseService;
	private final StudentCourseService studentCourseService;

	public CourseController(CourseService courseService, StudentCourseService studentCourseService) {
		this.courseService = courseService;
		this.studentCourseService = studentCourseService;
	}

	/**
	 * GET methods (retrieving info)
 	*/

	/**
	 * 
	 *
	 * 
	 * HTTP method: GET
	 *
	 * @param withoutStudents = true --> return the list of courses without any student (default: false).
	 * @return the list of courses.
	 */
	@GetMapping(value = "/")
	@ResponseStatus(HttpStatus.OK)
	public List<CourseDto> getCourses(@RequestParam(name = "without-students") Optional<Boolean> withoutStudents) {
		return courseService.getCourses(withoutStudents.orElse(false));
	}

	/**
	 * 
	 *
	 * 
	 * HTTP method: GET
	 *
	 * @param id = the course id.
	 * @return course info related to the id.
	 */
	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CourseDto getCourse(@PathVariable Long id) {
		return courseService.getCourse(id);
	}

	/**
	 * 
	 *
	 * 
	 * HTTP method: GET
	 *
	 * @param id = the course id.
	 * @return list of students enrolled in the course.
	 */
	@GetMapping(value = "/{id}/students")
	@ResponseStatus(HttpStatus.OK)
	public List<StudentDto> getStudentsFromCourse(@PathVariable Long id) {
		return studentCourseService.getStudentsFromCourse(id);
	}

	/**
	 * @return list of relationships between students and courses, ordered by course and student.
	 */
	@GetMapping(value = "/students")
	@ResponseStatus(HttpStatus.OK)
	public List<CourseDto> getRelations() {
		return studentCourseService.getCoursesStudents();
	}

	/**
	 * PUT methods (updating info)
	 */

	/**
	 * HTTP method: PUT
	 *
	 * @param id        = the course id.
	 * //@param courseDto = JSON containing the course's name to be updated.
	 *                  Ex: {"name":"Calculus"}
	 * @return the course's info updated.
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CourseDto updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
		courseDto.setId(id);
		return courseService.updateCourse(courseDto);
	}

	/**
	 *
	 *
	 *
	 * HTTP method: PUT
	 *
	 * @param id         = the course id.
	 * @param studentIds = the ids of the students to be enrolled in the course. Limited to 50 students
	 *                   Ex: [1, 2, 3]
	 * @return a list containing the course id and the enrolled students.
	 */
	@PutMapping(value = "/{id}/students")
	@ResponseStatus(HttpStatus.OK)
	public CourseDto updateCourseStudents(@PathVariable Long id, @RequestBody List<Long> studentIds) {
		return studentCourseService.insertStudentInCourse(id, studentIds);
	}

	/**
	 * POST methods (inserting info)
	 */

	/**
	 *
	 * TODO
	 *
	 * HTTP method: POST
	 *
	 * //@param courseDtoList = a list of courses, in JSON format, to be created.
	 *                      Ex: [{"name": "Algebra"}, {"name": "Calculus"}]
	 * @return a list of the courses that were created with the submitted request.
	 */
	@PostMapping(value = "/")
	@ResponseStatus(HttpStatus.OK)
	public List<CourseDto> createCourses(@RequestBody List<CourseDto> courseDtoList) {
		return courseService.createCourses(courseDtoList);
	}

	/**
	 * DELETE methods (removing info)
	 */

	/**
	 *
	 * TODO
	 *
	 * HTTP method: DELETE
	 *
	 * @param confirmDeletion = true --> deletes all the courses, and student-courses relations.
	 *                        The student table will not be modified.  (default: false)
	 */
	@DeleteMapping(value = "/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCourses(@RequestParam(name = "confirm-deletion") Optional<Boolean> confirmDeletion) {
		courseService.deleteAllCourses(confirmDeletion.orElse(false));
	}

	/**
	 *
	 * TODO
	 *
	 * HTTP method: DELETE
	 *
	 * @param id = the course id.
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCourse(@PathVariable Long id, @RequestParam(name = "confirm-deletion") Optional<Boolean> confirmDeletion) {
		courseService.deleteCourse(id, confirmDeletion.orElse(false));
	}
}
