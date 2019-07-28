package org.demo.maven.respository;

import org.demo.maven.model.Classes;
import org.demo.maven.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Classes, Long> {
    @Query("select c from Classes c, Teacher t where c.mainTeacher = t.id and t.name like :name")
    Page<Classes> findClassByMainTeacherName(@Param("name") String name, Pageable pageable);

//    @Query("select t from Classes c, GradeCourse g, Teacher t where c.id=:id and g in(c.gradeCourses) " +
//            "and g.id = :gradeCourseId and t in(c.teachers)")
//    Teacher getTeacherByGradeCourse(@Param("id") Long id, @Param("gradeCourseId") Long gradeCourseId);

//    @Modifying
//    @Query("update Classes c set c.teachers.elements = :teacher where c.id=:id and c.gradeCourses.elements.id = :gradeCourseId")
//    void updateCourseTeacher(@Param("id") Long id, @Param("gradeCourseId") Long gradeCourseId, @Param("teacher") Teacher teacher);
}
