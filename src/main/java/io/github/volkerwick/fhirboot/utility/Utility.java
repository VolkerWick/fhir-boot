package io.github.volkerwick.fhirboot.utility;

import org.springframework.scheduling.annotation.Async;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utility {

    @Async
    public void test() {
        log.info("*** entering test");

        try {
            Thread.sleep(10_000);;
        } catch (Exception e) {
            log.error("Sleep aborted: ", e);
        }

        log.info("*** exiting test");
    }
    
}