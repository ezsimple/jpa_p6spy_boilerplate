package newapp.global.configuration;

import io.mkeasy.webapp.processor.MyBatisProcessor;
import io.mkeasy.webapp.processor.ProcessorServiceFactory;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class DatabaseConfiguration {
	
	private final Environment env;
	private final DataSource dataSource;
    
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		final String DbType = env.getProperty("Globals.DbType");
		Resource configLocation = applicationContext.getResource(env.getProperty("mybatis.config-location"));
		Resource[] mapperLocations = applicationContext.getResources(env.getProperty("mybatis.mapper-locations"));
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(configLocation);
		sqlSessionFactoryBean.setMapperLocations(mapperLocations);
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
	
	@Bean
	public MyBatisProcessor myBatisProcessor() {
		return new MyBatisProcessor();
	}

	@Bean
	public ProcessorServiceFactory processorServiceFactory() {
		return new ProcessorServiceFactory();
	}

	@Bean
	public QueryFactory queryFactory() {
		return new QueryFactory();
	}

}
