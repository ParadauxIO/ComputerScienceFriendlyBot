/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
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

package io.paradaux.friendlybot.commands.staff.technician;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.models.objects.PrivilegedCommand;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.managers.VerificationManager;
import io.paradaux.friendlybot.utils.models.exceptions.VerificationException;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

public class VerificationCommand extends PrivilegedCommand {

    public VerificationCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "verification";
        this.help = "Administrator utility to manage verifications.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        if (!getPermissionManager().isTechnician(authorID)) {
            respondNoPermission(message, "[Technician]");
            return;
        }

        if (args.length == 0) {
            respondSyntaxError(message, ";verification <add/set/ispending/getpending/reset>>"
                    + " [@mention/userid] [email]");
            return;
        }

        VerificationManager verification = VerificationManager.getInstance();

        switch (args[0]) {
            case "add": {
                User target = parseTarget(message, args[1]);

                if (target == null) {
                    message.getChannel().sendMessage("Invalid target specified.").queue();
                    return;
                }

                try {
                    verification.setPendingVerification(args[1],
                            target.getId(), message.getGuild().getId());
                } catch (VerificationException exception) {
                    message.getChannel().sendMessage("An error occured: " + exception
                            .getMessage()).queue();
                }

                break;
            }

            case "set": {
                User target = parseTarget(message, args[1]);

                if (target == null) {
                    message.getChannel().sendMessage("Invalid target specified.").queue();
                    return;
                }

                try {
                    verification.setVerified(target.getId(),
                            message.getGuild().getId(), null);
                } catch (VerificationException exception) {
                    message.getChannel().sendMessage("An error occurred: " + exception
                            .getMessage()).queue();
                }

                break;
            }

            case "ispending": {
                User target = parseTarget(message, args[1]);

                if (target == null) {
                    message.getChannel().sendMessage("Invalid target specified.").queue();
                    return;
                }

                String not = (MongoManager.getInstance().isPendingVerification(target.getId())) ? "" : "not ";

                message.getChannel().sendMessage(target.getAsTag() + " is " + not + "pending verification.").queue();


                break;
            }

            case "getpendingusers": {
                message.getChannel().sendMessage("```" + verification.getPendingUsers() + "```").queue();
                break;
            }

            case "reset": {
                message.getChannel().sendMessage("Not yet implemented.").queue();
                break;
            }

            default: {
                respondSyntaxError(message, ";verification <add/ispending/getpending/reset>> [@mention/userid] [email]");
            }
        }
    }

}