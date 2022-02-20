package newapp.domain.repository;

import newapp.domain.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long>, QuerydslPredicateExecutor<CompanyEntity> {
}
