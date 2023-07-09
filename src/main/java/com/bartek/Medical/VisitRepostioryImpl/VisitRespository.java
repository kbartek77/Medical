package com.bartek.Medical.VisitRepostioryImpl;

import com.bartek.Medical.Model.Patient;
import com.bartek.Medical.Model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRespository extends JpaRepository<Visit, Long> {
    List<Visit> findByDateTime(LocalDateTime dateTime);
    List<Visit> findByPatient(Patient patient);
}
