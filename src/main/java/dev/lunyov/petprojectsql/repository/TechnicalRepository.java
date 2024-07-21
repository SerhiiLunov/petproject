package dev.lunyov.petprojectsql.repository;

import dev.lunyov.petprojectsql.entity.Technical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalRepository extends JpaRepository<Technical, Long> {
}
