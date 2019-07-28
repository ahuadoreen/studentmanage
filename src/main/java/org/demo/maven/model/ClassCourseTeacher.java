package org.demo.maven.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@DynamicUpdate
@Entity
@javax.persistence.Table(name = "class_course_teacher")
public class ClassCourseTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="class_id")
    private Classes classes;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="gradeCourse_id")
    private GradeCourse gradeCourse;

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public GradeCourse getGradeCourse() {
        return gradeCourse;
    }

    public void setGradeCourse(GradeCourse gradeCourse) {
        this.gradeCourse = gradeCourse;
    }
}
