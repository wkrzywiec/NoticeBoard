package com.wkrzywiec.medium.noticeboard.controller;

import com.wkrzywiec.medium.noticeboard.controller.dto.NoticeDTO;
import com.wkrzywiec.medium.noticeboard.entity.Notice;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableTransactionManagement
public class NoticeControllerITCase {

    @LocalServerPort
    private int port;

    //TODO: replace with RestAssured
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @Ignore("NoticeRepository don't see saved entity")
    public void givenSingleNotice_whenGETAllNotices_thenGetSingleNoticeList() {
        //given
        entityManager.persist(singleNotice(1L));
        entityManager.flush();

        //when
        ResponseEntity<?> response =
                restTemplate.exchange(
                "http://localhost:" + port + "/notices/",
                HttpMethod.GET,
                null, new ParameterizedTypeReference<List<NoticeDTO>>() {});

        List<NoticeDTO> noticeDTOList = (List<NoticeDTO>) response.getBody();

        assertEquals(1, noticeDTOList.size());

    }

    private Notice singleNotice(Long number){
        return Notice.builder()
                .title("Notice " + number)
                .description("Notice description " + number)
                .build();
    }
}
