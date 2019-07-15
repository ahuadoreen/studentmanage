package org.demo.maven.respository;

import org.demo.maven.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRespository extends JpaRepository<Subject, Long> {
}
