/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.

 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).

 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.csbot.FriendlyBot;
import net.dv8tion.jda.api.entities.Message;

/**
 * This is a command which provides the user with an invite link to the current discord server.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see FriendlyBot
 * */

public class InviteCommand extends Command {

    public InviteCommand() {
        this.name = "invite";
        this.aliases = new String[]{"inv", "i"};
        this.help = "Provides the user with an invite link to invite the bot.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        message.getChannel().sendMessage("You can invite this discord bot by "
                + "messaging the maintainer, or by running your own instance The "
                + "verification system requires an SMTP Login, you can use the likes "
                + "of GMX for this, as well as a mongodb database.\nIf you wish to use "
                + "the current edition of the bot, please message Rían#6500 or open "
                + "an issue at https://github.com/ParadauxIO/ComputerScienceFriendlyBot")
                .queue();
    }
}
