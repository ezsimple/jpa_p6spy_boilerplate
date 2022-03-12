package newapp.domain.repository;

import newapp.domain.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long>, QuerydslPredicateExecutor<ProjectEntity> {
    Optional<ProjectEntity> findById(Long projNo);
}
