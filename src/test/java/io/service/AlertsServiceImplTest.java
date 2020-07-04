package io.service;

import io.entity.alerts;
import io.repository.AlertsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
class AlertsServiceImplTest {

    @TestConfiguration
    static class AlertServiceConfig{

        @Bean
        public AlertsService getService(){
            return new AlertsServiceImpl();
        }
    }
    @Autowired
    AlertsService alertsService;

    @MockBean
    private AlertsRepository alertsRepository;

    List<alerts> list = new ArrayList();
    alerts alert1 = new alerts();
    alerts alert2 = new alerts();
    @BeforeEach
    void setUp() {
        alert1.setVin("1");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.kkk'Z'");
        df.setTimeZone(tz);
        String time1 = df.format(new java.util.Date(System.currentTimeMillis() - (1 * 60 * 60 * 1000)));
        alert1.setTimestamp(time1);
        alert1.setId("1");
        alert1.setPriority(priority.HIGH);
        alert1.setRule(rule.EngineRpmHigh);
        list.add(alert1);
        alert2.setVin("1");
        String time2 = df.format(new java.util.Date(System.currentTimeMillis() - (1 * 30 * 60 * 1000)));
        alert2.setTimestamp(time2);
        alert2.setId("2");
        alert2.setPriority(priority.HIGH);
        alert2.setRule(rule.EngineRpmHigh);
        list.add(alert2);
        Mockito.when(alertsRepository.getHighAlerts(priority.HIGH,"1")).thenReturn(list);
        Mockito.when(alertsRepository.findByVin("1")).thenReturn(list);
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void getHighAlerts() {
//        List<alerts> alertsList = alertsService.getHighAlerts();
//        assertEquals(list,alertsList,"Lists must match");
//        assertEquals(list.size(),alertsList.size(),"size of lists must match");
//    }

    @Test
    void findByVin() {
        List<alerts> alertsList = alertsService.findByVin("1");
        assertEquals(list,alertsList,"Lists must match");
        assertEquals(list.size(),alertsList.size(),"size of lists must match");
    }
}