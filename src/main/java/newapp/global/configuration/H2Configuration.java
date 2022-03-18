package newapp.global.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@Profile("local")
@RequiredArgsConstructor
public class H2Configuration {

//  @Bean
//  @ConfigurationProperties("spring.datasource.hikari")
//  public DataSource dataSource() throws SQLException {
//    int port = 9092;
//    Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", String.valueOf(port)).start();
//    if (server.isRunning(true)) {
//      log.info("H2 서버를 시작했습니다.");
//      return new HikariDataSource();
//    }
//    throw new SQLException("H2 서버를 시작하지 못했습니다.");
//  }

}
