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

package io.paradaux.csbot.embeds;

import io.paradaux.csbot.IEmbedMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class PickYourPronounsEmbed implements IEmbedMessage {

    EmbedBuilder builder;

    public PickYourPronounsEmbed() {
        builder = new EmbedBuilder();
        create();
    }

    @Override
    public void create() {
        // Zero width space
        builder.setDescription("**What pronouns would you like people to use for you?**\n\nIf you selected other, please feel free to use your nickname or your status to provide additional information.");
        builder.addField("He/Him", "\uD83C\uDDE6", true);
        builder.addField("She/Her", "\uD83C\uDDE7", true);
        builder.addField("They/Them", "\uD83C\uDDE8", true);
        builder.addField("Other", "\uD83C\uDDE9", true);
    }

    @Override
    public MessageEmbed build() {
        return builder.build();
    }
}
