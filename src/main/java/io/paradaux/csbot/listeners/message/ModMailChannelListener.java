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

import io.paradaux.csbot.controllers.BotController;
import io.paradaux.csbot.controllers.ConfigurationController;
import io.paradaux.csbot.controllers.DatabaseController;
import io.paradaux.csbot.embeds.modmail.ModMailReceivedEmbed;
import io.paradaux.csbot.embeds.modmail.ModMailSentEmbed;
import io.paradaux.csbot.interfaces.Embed;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ModMailChannelListener extends ListenerAdapter {

    // Dependencies
    private static final ConfigurationEntry configurationEntry = ConfigurationController
            .getConfigurationEntry();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getChannelType() == ChannelType.PRIVATE) {
            return;
        }

        if (!message.getChannel().getId().equals(configurationEntry.getModmailInputChannelID())) {
            return;
        }

        message.delete().queue();

        TextChannel channel = Objects.requireNonNull(BotController.getClient()
                .getGuildById(configurationEntry.getCsFriendlyGuildID()))
                .getTextChannelById(configurationEntry.getModmailOutputChannelID());


        String messageContent = message.getContentRaw();
        String ticketNumber = DatabaseController.INSTANCE.getNextTicketNumber();
        String incidentID = DatabaseController.INSTANCE.getNextIncidentID();

        Embed receivedEmbed = new ModMailReceivedEmbed(event.getMessage().getAuthor(),
                message.getContentRaw(), ticketNumber, incidentID);
        ModMailSentEmbed sentEmbed = new ModMailSentEmbed(ticketNumber, messageContent);

        receivedEmbed.sendEmbed(channel);
        event.getAuthor().openPrivateChannel().queue((privateChannel) -> privateChannel
                .sendMessage(sentEmbed.getEmbed()).queue());
    }


}