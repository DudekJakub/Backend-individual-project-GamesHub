package com.gameshub.email;

import com.gameshub.domain.mail.Mail;
import com.gameshub.domain.user.User;
import com.gameshub.email.builder.EmailBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final ApplicationContext appContext;
    private final EmailUserService emailUserService;
    private final EmailAdminService emailAdminService;

    @Scheduled(cron = "0 0 */30 * * *")
    public void sendUserSummaryEmail() {
        List<?> confirmedUsers = (List<?>) appContext.getBean("confirmedUsers");

        for (Object confirmedUser : confirmedUsers) {
            if (confirmedUser instanceof User) {
                User user = (User) confirmedUser;
                String userEmail = user.getEmail();

                Mail mail = EmailBuilder.createMail(
                        userEmail,
                        "GamesHub - You personal summary mail!",
                        "Check Your personal application statistics & stay with us for a long time!"
                );
                emailUserService.sendUserSummaryMail(mail, user);
            }
        }
    }

    @Scheduled(cron = "0 0 */30 * * *")
    public void sendAppStatisticEmail() {
        List<?> admins = (List<?>) appContext.getBean("admins");

        for (Object admin_ : admins) {
            User admin = (User) admin_;
            String adminEmail = admin.getEmail();

            Mail mail = EmailBuilder.createMail(
                    adminEmail,
                    "GamesHub - [ADMIN] Application Statistics",
                    "Every week app statistics."
            );
            emailAdminService.sendAppStatisticMail(mail, admin);
        }
    }
}
