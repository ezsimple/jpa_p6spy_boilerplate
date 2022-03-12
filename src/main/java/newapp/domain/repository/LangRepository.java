package newapp.domain.repository;

import newapp.domain.entity.LangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LangRepository extends JpaRepository<LangEntity, String>, QuerydslPredicateExecutor<LangEntity> {
}
