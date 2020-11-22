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

package io.paradaux.csbot.controllers;

import io.paradaux.csbot.embeds.AuditLogEmbed;
import io.paradaux.csbot.interfaces.IController;
import io.paradaux.csbot.models.automatic.AuditLogEntry;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;

public class AuditLogController implements IController {

    // Singleton Instance
    public  static AuditLogController INSTANCE;

    // Dependencies
    private static final ConfigurationEntry configurationEntry = ConfigurationController.getConfigurationEntry();

    @Override
    public void initialise() {
        INSTANCE = this;
    }

    public void log(AuditLogEmbed.Action action, User target, User staff, String reason, String incidentID) {
        AuditLogEmbed embed = new AuditLogEmbed(action, target, staff, reason, incidentID);

        AuditLogEntry auditLogEntry = new AuditLogEntry()
                .setAction(action)
                .setIncidentID(incidentID)
                .setUserTag(target.getAsTag())
                .setUserID(target.getId())
                .setReason(reason)
                .setTimestamp(new Date());

        DatabaseController.INSTANCE.addAuditLog(auditLogEntry);

        TextChannel channel = BotController.getClient()
                .getGuildById(configurationEntry.getCsFriendlyGuildID())
                .getTextChannelById(configurationEntry.getAuditLogChannelID());

        embed.sendEmbed(channel);

    }

}
