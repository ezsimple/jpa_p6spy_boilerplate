package newapp.global.common.core;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppContext {

  private final ApplicationContext applicationContext;

  private ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public Object getBean(String name) {
    return getApplicationContext().getBean(name);
  }

  public Object getBean(Class<?> clazz) {
    return getApplicationContext().getBean(clazz);
  }

}
