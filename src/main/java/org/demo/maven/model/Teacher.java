package org.demo.maven.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@javax.persistence.Table(name = "teacher", schema = "studentmanage", catalog = "")
public class Teacher implements Serializable {

	private static final long serialVersionUID = 5645128909295675327L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Basic
	@Column(name = "name")
	private String name;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "teacher_subject", joinColumns = {
			@JoinColumn(name = "teacher_id", referencedColumnName = "id")}, inverseJoinColumns = {
			@JoinColumn(name = "subject_id", referencedColumnName = "id")})
	private Set<Subject> subjects;
	
	@Basic
	@Column(name = "gender")
	private Integer gender;
	
	@Basic
	@Column(name = "age")
	private Integer age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubject(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
}
