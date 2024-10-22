package edu.depaul.cdm.se452.rfa.authentication.security;

import edu.depaul.cdm.se452.rfa.config.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class FilterConfig {
    private final ApplicationProperties applicationProperties;
    private final String env;
    public FilterConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        env = this.applicationProperties.getEnv();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Configure CORS settings
        config.setAllowCredentials(true);
        config.setAllowedOrigins(env.equals("dev") ? Arrays.asList("http://localhost:5173") : Arrays.asList("https://blueroomies.com"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        // Register the configuration for all paths
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
