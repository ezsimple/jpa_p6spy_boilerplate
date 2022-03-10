package newapp.global.common.core;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class CoreResource {

  private static JPAQueryFactory jpaQuery;

  private static EntityManager entityManager;

  private static ApplicationContext applicationContext;

  @Autowired
  public void setJpaQuery(JPAQueryFactory jpaQuery) {
    CoreResource.jpaQuery = jpaQuery;
  }

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    CoreResource.entityManager = entityManager;
  }

  @Autowired
  public void setApplicationContext(ApplicationContext applicationContext) {
    CoreResource.applicationContext = applicationContext;
  }

  public static JPAQueryFactory jpaQuery() {
    return jpaQuery;
  }

  public static EntityManager entityManager() {
    return entityManager;
  }

  private static ApplicationContext applicationContext() {
    return applicationContext;
  }

  public static Object getBean(String name) {
    return applicationContext().getBean(name);
  }

  public static Object getBean(Class<?> clazz) {
    return applicationContext().getBean(clazz);
  }

}
