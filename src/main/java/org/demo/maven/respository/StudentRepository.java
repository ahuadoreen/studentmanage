package org.demo.maven.respository;

import org.demo.maven.model.Student;
import org.demo.maven.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>  {
}
