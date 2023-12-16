package org.web4.configes;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.*;
import org.web4.musicProject.Music;
import org.web4.objects.PrototypeBean;

@Configuration
@PropertySource("classpath:names.properties")
public class SpringConfig {

    @Bean
    @Scope("prototype")
    public PrototypeBean prototypeBean(){
        return new PrototypeBean(new Music("ker2", 55));
    }

}
