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

package io.paradaux.csbot.managers;

import io.paradaux.csbot.models.exceptions.ManagerNotReadyException;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import org.slf4j.Logger;

import java.io.FileNotFoundException;

public class ConfigManager {

    private static ConfigManager instance = null;
    private ConfigurationEntry config;

    public ConfigManager(ConfigurationEntry config, Logger logger) {
        this.config = config;

        logger.info("Initialising: ConfigurationController");
        instance = this;
    }

    public ConfigManager(Logger logger) {

        IOManager.deployFiles(logger);
        try {
            config = IOManager.readConfigurationFile();
        } catch (FileNotFoundException exception) {
            logger.error("Could not find the configuration file!", exception);
        }

        instance = this;
    }

    public ConfigurationEntry getConfig() {
        return config;
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

}