package fun.tomberg.swedbankhm.hooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        logger.info("LOGIN SUCCESS FOR: " + event.getAuthentication().getName().toUpperCase());
    }
}
