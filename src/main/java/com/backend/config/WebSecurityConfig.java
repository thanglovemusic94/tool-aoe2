package com.backend.config;

import com.backend.models.ERole;
import com.backend.security.jwt.AuthEntryPointJwt;
import com.backend.security.jwt.AuthTokenFilter;
import com.backend.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
//     securedEnabled = true,
//     jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig{
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean(name="myPasswordEncoder")
  public PasswordEncoder getPasswordEncoder() {

    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .requestMatchers("/api/auth/admin/*").hasAuthority(ERole.ROLE_ADMIN.getAuthority())
            .requestMatchers("/api/auth/*").hasAnyAuthority(ERole.ROLE_ADMIN.getAuthority(), ERole.ROLE_USER.getAuthority())
            .anyRequest().permitAll();

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }


  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods(HttpMethod.DELETE.name(), HttpMethod.POST.name(),
                        HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(), HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name())
               .allowedOrigins(
                       "http://toolaoe.s3-website-ap-southeast-1.amazonaws.com/",
                       "http://checovuive.top/",
                       "https://checovuive.top/",
                       "http://aoeranking.top/",
                       "https://aoeranking.top/"
                       );
      }
    };
  }

  @Bean
  @Profile("dev")
  public WebMvcConfigurer corsConfigurer2() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods(HttpMethod.DELETE.name(), HttpMethod.POST.name(),
                        HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(), HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name())
                .allowedOrigins(
                        "http://localhost:3000/"
                );

      }
    };
  }
}
