package newapp.domain.repository;

import newapp.domain.entity.CustomerReqEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CustomerReqRepository extends JpaRepository<CustomerReqEntity, Long>, QuerydslPredicateExecutor<CustomerReqEntity> {
}
