package com.sls.listService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ResourceExceptionHandlerTest {

    private ResourceExceptionHandler resourceExceptionHandler;

    @Before
    public void setUp() {
        resourceExceptionHandler = new ResourceExceptionHandler();
    }

    @Test
    public void testReturnsResponseEntityWithBadRequestAndInternalServerError() {

        ListException listException = new ListException("Thrown ListException");

        ResponseEntity responseEntity = resourceExceptionHandler.handleResourceException(listException);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isEqualTo(listException.getMessage());

    }

}