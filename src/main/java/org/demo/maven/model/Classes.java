package org.demo.maven.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@javax.persistence.Table(name = "class")
public class Classes implements Serializable {

	private static final long serialVersionUID = -6577572503351054497L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long classId;
	
	@Basic
	@Column(name = "class_name")
	private String className;
	
	@Basic
	@Column(name = "grade")
	private String grade;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="main_teacher")
	private Teacher mainTeacher;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "class_subject", joinColumns = {
			@JoinColumn(name = "class_id", referencedColumnName = "id")}, inverseJoinColumns = {
			@JoinColumn(name = "subject_id", referencedColumnName = "id")})
	private Set<Subject> subjects;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "class_teacher", joinColumns = {
			@JoinColumn(name = "class_id", referencedColumnName = "id")}, inverseJoinColumns = {
			@JoinColumn(name = "teacher_id", referencedColumnName = "id")})
	private Set<Teacher> teachers;

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Teacher getMainTeacher() {
		return mainTeacher;
	}

	public void setMainTeacher(Teacher mainTeacher) {
		this.mainTeacher = mainTeacher;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
}
