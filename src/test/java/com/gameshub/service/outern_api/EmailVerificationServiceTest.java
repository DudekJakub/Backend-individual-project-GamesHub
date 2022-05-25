package com.gameshub.service.outern_api;

import com.gameshub.client.EmailVerificationClient;
import com.gameshub.domain.mail.Mail;
import com.gameshub.domain.mail.MailVerificationDto;
import com.gameshub.exception.EmailVerificationFailedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {

    @InjectMocks
    private EmailVerificationService service;

    @Mock
    private EmailVerificationClient emailVerificationClient;

    @Test
    void checkEmailExists() throws EmailVerificationFailedException {
        //Given
        String email = "test_email";
        MailVerificationDto verificationDto = new MailVerificationDto();
        verificationDto.setEmailExists(true);

        when(emailVerificationClient.checkEmail(email)).thenReturn(Optional.of(verificationDto));

        //When
        boolean emailExists = service.checkEmailExists(email);

        //Then
        assertTrue(emailExists);
    }
}