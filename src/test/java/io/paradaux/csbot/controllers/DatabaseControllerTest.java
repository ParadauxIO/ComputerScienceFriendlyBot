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

import io.paradaux.csbot.ConfigurationCache;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DatabaseControllerTest {

    static LogController logController = new LogController();

    static ConfigurationCache configurationCache;
    static ConfigurationController configurationController;

    static DatabaseController databaseController = new DatabaseController();

    @BeforeAll
    static void setup() {
        configurationCache.setMongoConnectionUri("MONGO-URI-HERE");
        configurationController = new ConfigurationController(configurationCache);
        logController.initialise();
        databaseController.initialise();
    }

    @Test
    public void createPendingVerificationEntryTest() {
        databaseController.addPendingVerificationUser("150993042558418944", "", "69420");
    }

    @Test
    public void setVerifiedTest() {
        databaseController.setVerifiedUser("150993042558418944", "757903425311604786");
    }

}