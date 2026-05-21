package com.opscore.incident.repository;

import com.opscore.incident.model.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    // Aquí heredamos findById automáticamente de JpaRepository
}
