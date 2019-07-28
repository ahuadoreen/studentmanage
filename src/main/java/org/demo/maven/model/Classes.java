package org.demo.maven.model;

import java.io.Serializable;
import java.util.List;
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
	private Long id;
	
	@Basic
	@Column(name = "class_name")
	private String className;
	
	@Basic
	@Column(name = "grade")
	private Integer grade;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="main_teacher")
	private Teacher mainTeacher;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Teacher getMainTeacher() {
		return mainTeacher;
	}

	public void setMainTeacher(Teacher mainTeacher) {
		this.mainTeacher = mainTeacher;
	}

}
