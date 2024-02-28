package org.jetbrains.assignment.repository;

import org.jetbrains.assignment.entity.RequestResponseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestResponseLogRepository extends JpaRepository<RequestResponseLog, Long> {
}
