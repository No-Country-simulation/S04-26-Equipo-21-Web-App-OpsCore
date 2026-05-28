package com.opscore.incident.repository;

import com.opscore.incident.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

<<<<<<< HEAD
public interface AreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByNombre(String nombre);

=======
>>>>>>> upstream/develop
}
