/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.listeners.message;

import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.api.EmailUtils;
import io.paradaux.csbot.api.SMTPConnection;
import io.paradaux.csbot.api.VerificationSystem;
import io.paradaux.csbot.controllers.ConfigurationController;
import io.paradaux.csbot.controllers.EmailController;
import io.paradaux.csbot.controllers.LogController;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.mail.MessagingException;

public class VerificationEmailReceivedListener extends ListenerAdapter {

    ConfigurationCache configurationCache;
    SMTPConnection smtpConnection;
    Logger logger;

    public VerificationEmailReceivedListener() {
        this.configurationCache = ConfigurationController.getConfigurationCache();
        this.smtpConnection = EmailController.getSmtpConnection();
        logger = LogController.getLogger();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        EmailUtils emailUtils = new EmailUtils();

        if (!event.getChannel().getId().equals(configurationCache.getListeningChannel())) return;

        Message message = event.getMessage();
        message.delete().queue();
        String email = message.getContentRaw();

        if (!emailUtils.isValidEmail(email)) {
            event.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("Your message did not contain an email.").queue();
            });

            return;
        }

        if (!emailUtils.getEmailDomain(email).equalsIgnoreCase("tcd.ie")) {
            event.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("The provided email must be of the tcd.ie domain. If you are not a trinity student, please contact a moderator for manual verification").queue();
            });

            return;
        };

        event.getAuthor().openPrivateChannel().queue((channel) -> {
            channel.sendMessage("Please check your email for a verification token. Once you have it, please paste it into this private message channel.").queue();
        });

        VerificationSystem.addPendingUser(event.getAuthor().getId(), emailUtils.generateVerificationCode());
        try {
            smtpConnection.sendVerificationEmail(email, VerificationSystem.getVerificationCode(event.getAuthor().getId()));
        } catch (MessagingException exception) {
            logger.error("There was an error sending the verification email for {}", event.getAuthor().getName(), exception);
            return;
        }

    }
}
