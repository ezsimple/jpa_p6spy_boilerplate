package newapp.global.configuration;

import io.mkeasy.resolver.CommandMapArgumentResolver;
import io.mkeasy.utils.AESUtil;
import io.mkeasy.utils.WebUtil;
import io.mkeasy.webapp.processor.ExcelFactory;
import io.mkeasy.webapp.processor.FileFactory;
import io.mkeasy.webapp.processor.QueryFactory;
import lombok.extern.slf4j.Slf4j;
import newapp.global.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CommandMapArgumentResolver());
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
            .addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/")
		;
//		registry
//			.addResourceHandler("/resources/**")
//			.addResourceLocations("/resources/");
	}
	
	@Bean
	LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/","/index*.do","/error/*.do");
	}

    @Value("${upload.dir}")
	private String UPLOAD_DIR;

	@Bean
	public QueryFactory queryFactory() {
		return new QueryFactory();
	}

	@Bean
	public FileFactory fileFactory() {
		FileFactory bean = new FileFactory();
		bean.setUploadDir(UPLOAD_DIR);
		return bean;
	}

	@Bean
	public ExcelFactory excelFactory() {
		return new ExcelFactory();
	}

	@Bean
	public AESUtil aesUtil() throws UnsupportedEncodingException {
		return new AESUtil();
	}

	@Bean
	public WebUtil webUtil() {
		return new WebUtil();
	}
	
//	@Bean
//	public ServerInfo ServerInfo() throws UnknownHostException, SocketException {
//		return new ServerInfo(NetUtil.getMAC());
//	}

}
