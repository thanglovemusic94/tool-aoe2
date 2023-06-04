package com.backend.controllers;

import com.backend.models.Event;
import com.backend.repository.EventRepository;
import com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoDataController implements CommandLineRunner, ApplicationRunner {
    //https://stackoverflow.com/questions/44749286/spring-boot-insert-sample-data-into-database-upon-startup

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        generateEvent(10);
    }

    @Override
    public void run(String... args) throws Exception {

    }


    public void generateEvent(int numberdata) {

        for (int i = 0; i < numberdata; i++) {
            Event event = new Event();
            event.setDescriptionShort("description " + i);
            event.setNote("note " + i);
            event.setTitle("su kien " + i);
            event.setTerm("term " + i);

            eventRepository.save(event);
        }

    }

}
