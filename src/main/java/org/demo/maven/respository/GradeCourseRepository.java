package org.demo.maven.respository;

import org.demo.maven.model.GradeCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GradeCourseRepository extends JpaRepository<GradeCourse, Long> {
    List<GradeCourse> findGradeCoursesByGrade(Integer grade);
    @Transactional
    void deleteByGrade(Integer grade);

}
