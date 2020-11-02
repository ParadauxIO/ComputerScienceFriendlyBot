/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.listeners;

import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.api.Logging;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;

/**
 * This event is used to show the bot has successfully logged in, and to provide usage information.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class ReadyListener extends ListenerAdapter {

    ConfigurationCache configurationCache;
    Logger logger;

    public ReadyListener(ConfigurationCache configurationCache) {
        this.configurationCache = configurationCache;
        logger = Logging.getLogger();
    }

    @Override
    public void onReady(ReadyEvent event) {

        int guildCount = event.getGuildAvailableCount();
        int userCount = 0;

        for (Guild guild : event.getJDA().getGuilds()) {
            userCount += guild.getMembers().size();
        }

        logger.info("Currently serving {} users in {} guilds", userCount, guildCount);
    }
}