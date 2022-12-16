package br.com.algaworks.algafoodapi.core.security.authorizationserver;

import br.com.algaworks.algafoodapi.domain.model.Usuario;
import br.com.algaworks.algafoodapi.domain.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        authorizationServerConfigurer.authorizationEndpoint(customizer -> customizer.consentPage("/oauth2/consent"));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        http.securityMatcher(endpointsMatcher)
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .formLogin(Customizer.withDefaults())
                .exceptionHandling(exceptions -> {
                    exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
                })
                .apply(authorizationServerConfigurer);

        return http.formLogin(customizer -> customizer.loginPage("/login")).build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(AlgaFoodSecurityProperties properties) {
        return AuthorizationServerSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder, JdbcOperations jdbcOperations) {

//        RegisteredClient algafoodbackend = RegisteredClient
//                .withId("1")
//                .clientId("algafood-backend")
//                .clientSecret(passwordEncoder.encode("backend123"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .scope("READ")
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//                        .refreshTokenTimeToLive(Duration.ofMinutes(30))
//                        .build())
//                .build();
//
//        RegisteredClient algafoodWeb = RegisteredClient
//                .withId("2")
//                .clientId("algafood-web")
//                .clientSecret(passwordEncoder.encode("web123"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .scope("READ")
//                .scope("WRITE")
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//                        .accessTokenTimeToLive(Duration.ofMinutes(15))
//                        .reuseRefreshTokens(false)
//                        .refreshTokenTimeToLive(Duration.ofDays(1))
//                        .build())
//                .redirectUri("http://127.0.0.1:8080/authorized")
//                .redirectUri("http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html")
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(true)
//                        .build())
//                .build();
//
//        RegisteredClient foodanalytics = RegisteredClient
//                .withId("3")
//                .clientId("foodanalytics")
//                .clientSecret(passwordEncoder.encode("web123"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope("READ")
//                .scope("WRITE")
//                .tokenSettings(TokenSettings.builder()
//                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//                        .accessTokenTimeToLive(Duration.ofMinutes(30))
//                        .build())
//                .redirectUri("http://www.foodanalytics.local:8082")
//                .clientSettings(ClientSettings.builder()
//                        .requireAuthorizationConsent(false)
//                        .build())
//                .build();

        JdbcRegisteredClientRepository repository = new JdbcRegisteredClientRepository(jdbcOperations);
//        repository.save(algafoodbackend);
//        repository.save(algafoodWeb);
//        repository.save(foodanalytics);
        return repository;
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception {
        char[] keyStorePass = properties.getPassword().toCharArray();
        String keypairAlias = properties.getKeypairAlias();

        Resource jksLocation = properties.getJksLocation();
        InputStream inputStream = jksLocation.getInputStream();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keyStorePass);

        RSAKey rsaKey = RSAKey.load(keyStore, keypairAlias, keyStorePass);

        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UsuarioRepository usuarioRepository) {
        return context -> {
            Authentication authentication = context.getPrincipal();
            User user = (User) authentication.getPrincipal();
            if (authentication.getPrincipal() instanceof User) {
                Usuario usuario = usuarioRepository.findByEmail(user.getUsername()).orElseThrow();

                Set<String> authorities = new HashSet<>();

                for (GrantedAuthority authority : user.getAuthorities()) {
                    authorities.add(authority.getAuthority());
                }
                context.getClaims().claim("usuario_id", usuario.getId().toString());
                context.getClaims().claim("authorities", authorities);
            }
        };
    }

    @Bean
    public OAuth2AuthorizationConsentService consentService(JdbcOperations jdbcOperations, RegisteredClientRepository clientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, clientRepository);
    }

    @Bean
    public OAuth2AuthorizationQueryService auth2AuthorizationQueryService(JdbcOperations jdbcOperations, RegisteredClientRepository clientRepository) {
        return new JdbcOAuth2AuthorizationQueryService(jdbcOperations, clientRepository);
    }
}
