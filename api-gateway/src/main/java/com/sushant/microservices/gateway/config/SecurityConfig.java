package com.sushant.microservices.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    //this freeResourceUrls paths will be accessed without authentication
    private final String[] freeResourceUrls ={ "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
            "/swagger-resources/**", "/api-docs/**", "/aggregate/**","/actuator/prometheus" };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
       return httpSecurity.authorizeHttpRequests(authorize -> authorize
                       .requestMatchers(freeResourceUrls).permitAll()
                       .anyRequest().authenticated())
               .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
//after adding above we can start this service
//but when we send request through postman it wil throw 401
//so for that we can goto authorization section of postman
//select type - oAuth2
//then scroll down to configure new token
//add new token name - ex Token
//select grant type - client credentials
//and in access token url add the url from the localhost:8181 from where we got the all details ie keyclock realm settings
//ie localhost:8181->Realm settings -> OpenID Endpoint Configuration -click on this and copy token_endpoint url
//and copy token and paste it ex - http://localhost:8181/realms/spring-microservices-security-realm/protocol/openid-connect/token
//then add client id - ie the client which we already created on keyclock (see docker compose yml file how we created client)
// ie localhost:8181 ex - spring-client-credentials-id
//then add client secret from form keyclock ->credentials tab copy client secret (see docker compose yml for more info)
//then click on get new token, we will get new bearer token in new postman window
//then click on use token button
//and then send the request -> it should display the response

//for any new request just goto auth and select oAuth2 it will autfill all details and then
// directly click on get new token and use token
