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

package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.utils.models.configuration.PermissionEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PermissionManager {

    // Singleton Instance
    public static PermissionManager instance;

    // Singleton Fields
    private PermissionEntry permissions;
    private final Logger logger;

    public PermissionManager(Logger logger) {
        this.logger = logger;

        logger.info("Initialising: PermissionController");

        try {
            permissions = IOManager.getInstance().readPermissionFile();
        } catch (FileNotFoundException e) {
            logger.info("Permissions file was not found.");
        }

        instance = this;
    }

    public static PermissionManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public void save() {
        try {
            IOManager.getInstance().updatePermissionFile(permissions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAdmin(String discordID) {
        permissions.getAdministrators().add(discordID);
        save();
    }

    public void removeAdmin(String discordID) {
        permissions.getAdministrators().remove(discordID);
        save();
    }

    public boolean isAdmin(String discordID) {
        return permissions.getAdministrators().contains(discordID);
    }

    public void addMod(String discordID) {
        permissions.getModerators().add(discordID);
        save();
    }

    public void removeMod(String discordID) {
        permissions.getModerators().remove(discordID);
        save();
    }

    public boolean isMod(String discordID) {
        return permissions.getModerators().contains(discordID);
    }

    public void addTechnician(String discordID) {
        permissions.getTechnicians().add(discordID);
        save();
    }

    public void removeTechnician(String discordID) {
        permissions.getTechnicians().add(discordID);
        save();
    }

    public boolean isTechnician(String discordID) {
        return permissions.getTechnicians().contains(discordID);
    }

}