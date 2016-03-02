package com.knowledgepoker;

import com.knowledgepoker.domain.Game;
import com.knowledgepoker.domain.Question;
import com.knowledgepoker.domain.Role;
import com.knowledgepoker.domain.User;
import com.knowledgepoker.repository.GameRepository;
import com.knowledgepoker.repository.QuestionRepository;
import com.knowledgepoker.repository.UserRepository;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.SocketUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootApplication
@EnableJpaRepositories
@EnableAutoConfiguration
public class KnowledgePokerApplication extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(KnowledgePokerApplication.class);

    @Autowired
    DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(KnowledgePokerApplication.class, args);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(KnowledgePokerApplication.class);
    }

}
