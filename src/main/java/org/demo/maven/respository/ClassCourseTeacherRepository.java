package org.demo.maven.respository;

import org.demo.maven.model.ClassCourseTeacher;
import org.demo.maven.model.Classes;
import org.demo.maven.model.GradeCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClassCourseTeacherRepository extends JpaRepository<ClassCourseTeacher, Long> {
    @Query("select c from ClassCourseTeacher c where c.classes.id = :classId and c.gradeCourse.id = :gradeCourseId")
    ClassCourseTeacher findClassCourseTeacherByClassesAndAndGradeCourse(@Param("classId")Long classId, @Param("gradeCourseId")Long gradeCourseId);
    @Transactional
    void deleteByClassesAndAndGradeCourse(Classes classes, GradeCourse gradeCourse);
    @Transactional
    void deleteByClasses(Classes classes);
}
