package stee.monolith.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import stee.monolith.auth.config.TokenConfig;
import stee.monolith.auth.entity.AppUser;
import stee.monolith.common.utils.GrantType;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;
    private final TokenConfig tokenConfig;

    public Map<String, String> authenticate(GrantType grantType, String username, String password) {
        return authenticate(grantType, username, password, false);
    }

    public Map<String, String> authenticate(GrantType grantType, String username, String password, boolean withRefreshToken) {
        String subject;
        String scope;

//        if (grantType == GrantType.PASSWORD) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            subject = authentication.getName();
            scope = getScope(authentication.getAuthorities());
//        } else {
//            throw new IllegalArgumentException("Unsupported grant type");
//        }

        return generateTokens(subject, scope, withRefreshToken);
    }

    public Map<String, String> refreshToken(String refreshToken) {
        try {
            Jwt decodedJWT = jwtDecoder.decode(refreshToken);
            String subject = decodedJWT.getSubject();
            UserDetails userDetails = appUserDetailsService.loadUserByUsername(subject);
            String scope = getScope(userDetails.getAuthorities());
            return generateTokens(subject, scope, false);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid refresh token", e);
        }
    }


    private Map<String, String> generateTokens(String subject, String scope, boolean withRefreshToken) {
        Map<String, String> tokens = new HashMap<>();
        Instant now = Instant.now();

        String accessToken = generateToken(subject, scope, now, tokenConfig.tokenValidity());
        tokens.put("accessToken", accessToken);
        tokens.put("tokenValidity", String.valueOf(tokenConfig.tokenValidity()));

        if (withRefreshToken) {
            String refreshToken = generateToken(subject, null, now, tokenConfig.tokenValidity() * 2);
            tokens.put("refreshToken", refreshToken);
        }

        return tokens;
    }

    private String generateToken(String subject, String scope, Instant issuedAt, long validityInMinutes) {
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plus(validityInMinutes, ChronoUnit.MINUTES))
                .issuer("stee-security-service");

        if (scope != null) {
            claimsBuilder.claim("scope", scope);
        }

        JwtClaimsSet claims = claimsBuilder.build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String getScope(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }

//    public Map<String, String> authenticate(
//            GrantType grantType,
//            String username,
//            String password,
//            boolean withRefreshToken,
//            String refreshToken
//    ){
//        String subject = null;
//        String scope = null;
//
//        if (grantType == GrantType.PASSWORD) {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//            subject = authentication.getName();
//            scope = authentication.getAuthorities()
//                    .stream().map( auth -> auth.getAuthority() ).collect( Collectors.joining( " " ) );
//
//        } else if (grantType == GrantType.REFRESH_TOKEN) {
//
//            Jwt decodeJWT = null;
//            try {
//                decodeJWT = jwtDecoder.decode( refreshToken );
//            } catch (JwtException e) {
//                //
//            }
//            subject = decodeJWT.getSubject();
//            UserDetails userDetails = userDetailsService.loadUserByUsername( subject );
//            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//            scope = authorities.stream().map(
//                    auth -> auth.getAuthority() ).collect(Collectors.joining( " " ) );
//
//        }
//
//        return generateToken( subject, scope, withRefreshToken );
//
//    }
//
//    private Map<String, String> generateToken( String subject, String scope,
//            boolean withRefreshToken
//    ){
//        Map<String, String> idToken = new HashMap<>();
//        Instant instant = Instant.now();
//
//        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
//                .subject(subject)
//                .issuedAt( instant )
//                .expiresAt( instant.plus(withRefreshToken? tokenConfig.tokenValidity() : tokenConfig.tokenValidity() * 2, ChronoUnit.MINUTES ) )
//                .issuer( "stee-security-service" ) //name of the app
//                .claim( "scope", scope )
//                .build();
//
//        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from( jwtClaimsSet ) ).getTokenValue();
//        idToken.put( "accessToken", jwtAccessToken );
//        idToken.put( "tokenValidity", tokenConfig.tokenValidity() );
//        if( withRefreshToken ){
//            generateRefreshToken( subject, idToken );
//        }
//        return idToken;
//    }
//
//    private Map<String, String> generateRefreshToken( String subject, Map<String, String> idToken ){
//        Instant instant = Instant.now();
//
//        JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
//                .subject(subject)
//                .issuedAt( instant )
//                .expiresAt( instant.plus(2, ChronoUnit.MINUTES ) )
//                .issuer( "stee-security-service" ) //name of the app
//                .build();
//
//        String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from( jwtClaimsSetRefresh ) ).getTokenValue();
//        idToken.put( "refreshToken", jwtRefreshToken );
//        return idToken;
//    }

}
