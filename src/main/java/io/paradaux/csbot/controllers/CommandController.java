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

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import io.paradaux.csbot.commands.HelpCommand;
import io.paradaux.csbot.commands.InviteCommand;
import io.paradaux.csbot.commands.PingCommand;
import io.paradaux.csbot.commands.staff.moderation.*;
import io.paradaux.csbot.commands.staff.technician.PermissionsCommand;
import io.paradaux.csbot.commands.staff.technician.SendEmailCommand;
import io.paradaux.csbot.commands.staff.technician.SendEmbedCommand;
import io.paradaux.csbot.interfaces.IController;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;

public class CommandController implements IController {

    // Singleton Instance
    public  static CommandController INSTANCE;

    // Dependencies
    private static final ConfigurationEntry configurationEntry = ConfigurationController.getConfigurationEntry();
    private static final Logger logger = LogController.getLogger();

    // Singleton Fields
    public static CommandClient commandClient;
    public static CommandClient getCommandClient() { return commandClient; }

    @Override
    public void initialise() {
        commandClient = createCommandClient();
        INSTANCE = this;
    }

    /**
     * Creates a CommanddClient instance which is provided by JDAUtilities. It handles a lot of the command listening, and acts as a clean command wrapper.
     * @return An instance of CommandClient.
     * */
    public static CommandClient createCommandClient() {
        CommandClientBuilder builder = new CommandClientBuilder()
                .setPrefix(configurationEntry.getCommandPrefix())
                .setOwnerId("150993042558418944")
                .setActivity(Activity.playing("with your emotions"))
                .addCommands(
                        // Moderator Commands.
                        new BanCommand(),
                        new CiteCommand(),
                        new KickCommand(),
                        new TimeOutCommand(),
                        new WarnCommand(),
                        new LookupCommand(),
                        new RespondCommand(),

                        // Technician Commands.
                        new PermissionsCommand(),
                        new SendEmailCommand(),
                        new SendEmbedCommand(),

                        // Regular User Commands.
                        new HelpCommand(),
                        new InviteCommand(),
                        new PingCommand()
                );

        return builder.build();
    }

}
