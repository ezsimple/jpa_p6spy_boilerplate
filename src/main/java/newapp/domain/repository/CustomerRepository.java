package newapp.domain.repository;

import newapp.domain.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, QuerydslPredicateExecutor<CustomerEntity> {
}
