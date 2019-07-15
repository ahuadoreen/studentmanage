package org.demo.maven.respository;

import org.demo.maven.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRespository extends JpaRepository<Classes, Long> {

}
