package stee.monolith.auth.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import stee.monolith.auth.repository.AppUserRepository;
import stee.monolith.auth.service.AppUserDetailsService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private AppUserRepository appUserRepository;
    private RSAKeysConfig rsaKeysConfig;
    private AppUserDetailsService appUserDetailsService;
    private PasswordEncoder passwordEncoder;


    @Bean
    public UserDetailsService userDetailsService(){
        return new AppUserDetailsService(appUserRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager (){
        var authProvider = new DaoAuthenticationProvider();

        authProvider.setPasswordEncoder( passwordEncoder );
        authProvider.setUserDetailsService( userDetailsService() );
        return new ProviderManager( authProvider );

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
//              .csrf( csrf -> csrf.ignoringRequestMatchers( toH2Console() )) //no csrf protection for H2 app
                .csrf( csrf -> csrf.disable() ) //disable csrf globaly( it's my current understand
                .sessionManagement( sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .headers( headers -> headers.frameOptions((frameOptions) -> frameOptions.disable()))//disable frameOptions protection

                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console")).permitAll()
                        .requestMatchers(AntPathRequestMatcher
                                .antMatcher("/login/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2ResourceServerConfigurer ->
                        oauth2ResourceServerConfigurer.jwt(Customizer.withDefaults()))
                .httpBasic( Customizer.withDefaults() )
                .userDetailsService( appUserDetailsService )
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );

        return http.build();
    }


    @Bean
    public JwtDecoder jwtDecoder (){
        return NimbusJwtDecoder.withPublicKey( rsaKeysConfig.publicKey() ).build();
    }

    @Bean
    public JwtEncoder jwtEncoder (){
        JWK jwk = new RSAKey.Builder( rsaKeysConfig.publicKey() ).privateKey( rsaKeysConfig.privateKey() ).build();
        JWKSource<SecurityContext> jwkSource;
        jwkSource = new ImmutableJWKSet<>( new JWKSet( jwk ) );
        return new NimbusJwtEncoder( jwkSource );
    }


}
