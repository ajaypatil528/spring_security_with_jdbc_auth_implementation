package in.ajay.security;

import javax.annotation.security.PermitAll;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void authManager(AuthenticationManagerBuilder auth) throws Exception{
		
			auth.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, authority from authorities where username=?");
	}
	
	@Bean
	public SecurityFilterChain configureSecurity(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests((req) ->

                     req.antMatchers("/welcome").permitAll()
                        .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                        .antMatchers("/admin").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()

        		).formLogin(withDefaults());
		
		
		return httpSecurity.build();
	}
}
