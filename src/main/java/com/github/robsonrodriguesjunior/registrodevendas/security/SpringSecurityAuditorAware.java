package com.github.robsonrodriguesjunior.registrodevendas.security;

import com.github.robsonrodriguesjunior.registrodevendas.config.Constants;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    // The return type is incompatible with '@NonNull Optional<String>' returned from AuditorAware<String>.getCurrentAuditor() (mismatching null constraints) Java(67109778)
    @SuppressWarnings("null") // Java language server Bug - For: "Java(67109778)"
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM));
    }
}
