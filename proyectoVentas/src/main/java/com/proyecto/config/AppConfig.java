package com.proyecto.config;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.proyecto.entity.Producto;
import com.proyecto.entity.Usuario;
import com.proyecto.entity.Venta;
import com.proyecto.entity.VentaProducto;
import com.proyecto.ftp.ConectaFtp;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("com.proyecto.dao"), @ComponentScan("com.proyecto.service"),
		@ComponentScan("com.proyecto.entity"), @ComponentScan("com.proyecto.config"),
		@ComponentScan("com.proyecto.bean"), @ComponentScan("com.github.adminfaces.template"),
		@ComponentScan("com.proyecto.mirrorentity"), @ComponentScan("com.proyecto.sap.entity"),
		@ComponentScan("com.proyecto.entity.vistas"), @ComponentScan("com.proyecto.sap.texval.entity"),
		@ComponentScan("com.proyecto.ftp"), @ComponentScan("com.proyecto.report") })

/**
 * Clase encargada de configurar conexiones de base de datos.
 * 
 * @author marval.
 * @version 1.0.
 */

@PropertySource("file:D:\\\\proyectoTitulo\\properties\\db.properties")
public class AppConfig {

	public final static String CATALOGO_DB = "ventas";
	public final static String ECHEMA = "comercio";

	@Value("${db.url}")
	String URL;
	@Value("${db.user}")
	String USER;
	@Value("${db.pass}")
	String PASS;
	@Value("${db.ftp.server}")
	String server;
	@Value("${db.ftp.user}")
	String userFtp;
	@Value("${db.ftp.pass}")
	String passFtp;
	@Value("${db.ftp.carpeta}")
	String carpeta;
	@Value("${db.ftp.img}")
	String img;

	/**
	 * Obtiene datasource para realizar conexiÃ³n a BD.
	 * 
	 * @return DataSource.
	 */

	@Bean
	public DataSource getDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(URL + CATALOGO_DB);
		dataSource.setSchema(ECHEMA);
		dataSource.setUsername(USER);
		dataSource.setPassword(PASS);

		return dataSource;
	}

	/**
	 * crea objeto de sesión para conectar a BD.
	 * 
	 * @return LocalSessionFactoryBean.
	 * @version 1.0.
	 * @since 1.0.
	 */
	@Bean
	@Primary
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());

		Properties props = new Properties();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
		props.put("hibernate.id.new_generator_mappings", "false");
		props.put("hibernate.connection.autocommit", "true");
		props.put("hibernate.connection.pool_size", "100");

		props.put("hibernate.connection.isolation", String.valueOf(Connection.TRANSACTION_READ_UNCOMMITTED));
		factoryBean.setHibernateProperties(props);

		/* se agregan clases bean para trabajar con hibernate */
		factoryBean.setAnnotatedClasses(Usuario.class, Producto.class,Venta.class,VentaProducto.class);

		return factoryBean;
	}

	/**
	 * @return HibernateTransactionManager.
	 */
	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}

	@Bean(name = "mailSender")
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("jfernalv106@gmail.com");
		mailSender.setPassword("qwerty2123:");

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

	@Bean
	public ConectaFtp conectaFtpLocal() throws IOException {
		ConectaFtp ftp = new ConectaFtp(server, userFtp, passFtp, carpeta, img);
		return ftp;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		executor.setKeepAliveSeconds(60);
		return executor;
	}

}