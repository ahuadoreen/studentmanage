package org.demo.maven.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicUpdate
@Entity
@javax.persistence.Table(name = "grade_course")
public class GradeCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "grade")
    private Integer grade;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="subject")
    private Subject subject;

    public Long getId() {
        return id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}
