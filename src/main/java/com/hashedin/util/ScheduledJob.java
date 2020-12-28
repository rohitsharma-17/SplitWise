package com.hashedin.util;

import com.hashedin.services.IGroupsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ScheduledJob {

    @Autowired
    private IGroupsService groupsService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void task() throws Exception {
        Map<String,Double> map = new HashMap<>();
        groupsService.getAll().stream().forEach(groups -> {
            try {
                groupsService.getSimplifiedDebt(groups.getId()).forEach((k,v) ->{
                    log.info("Name: {} , amount: {} ",k,v );
                });
            } catch (Exception e) {
                log.error("Error while processing: {} ", e.getMessage());
            }
        });
    }
}


