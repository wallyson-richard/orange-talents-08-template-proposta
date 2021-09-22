package br.com.zupacademy.wallyson.proposta.configuration.security;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@EnableWebSecurity
@Profile("prod")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequest ->
                        authorizeRequest
                                .antMatchers(HttpMethod.GET, "/api/propostas").hasAuthority("SCOPE_propostas:read")
                                .antMatchers(HttpMethod.POST, "/api/propostas").hasAuthority("SCOPE_propostas:write")
                                .antMatchers(HttpMethod.POST, "/api/cartoes/**").hasAuthority("SCOPE_cartoes:write")
                                .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_devops:read")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
