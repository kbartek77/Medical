package com.bartek.Medical.visitRepostioryImpl;

import com.bartek.Medical.model.Patient;
import com.bartek.Medical.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRespository extends JpaRepository<Visit, Long> {
    List<Visit> findByDateTime(LocalDateTime dateTime);
    List<Visit> findByPatient(Patient patient);
    @Query("SELECT v FROM Visit v WHERE v.dateTime < :endDateTime AND v.endDateTime > :dateTime")
    List<Visit> findByDateTimeRange(@Param("dateTime") LocalDateTime dateTime, @Param("endDateTime") LocalDateTime endDateTime);
}
