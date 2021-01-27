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

package io.paradaux.friendlybot.utils.models.database;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Date;

public class BanEntry {

    @BsonProperty(value = "incident_id")
    String incidentID;

    @BsonProperty(value = "target_tag")
    String userTag;

    @BsonProperty(value = "target_id")
    String userID;

    @BsonProperty(value = "staff_tag")
    String staffTag;

    @BsonProperty(value = "staff_id")
    String staffID;

    @BsonProperty(value = "reason")
    String reason;

    @BsonProperty(value = "timestamp")
    Date timestamp;

    public BanEntry() {

    }

    public BanEntry(String incidentID, String userTag, String userID, String staffTag, String staffID, String reason, Date timestamp) {
        this.incidentID = incidentID;
        this.userTag = userTag;
        this.userID = userID;
        this.staffTag = staffTag;
        this.staffID = staffID;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public String getUserTag() {
        return userTag;
    }

    public String getUserID() {
        return userID;
    }

    public String getStaffTag() {
        return staffTag;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getReason() {
        return reason;
    }

    public String getIncidentID() {
        return incidentID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public BanEntry setIncidentID(String incidentID) {
        this.incidentID = incidentID;
        return this;
    }

    public BanEntry setUserTag(String userTag) {
        this.userTag = userTag;
        return this;
    }

    public BanEntry setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public BanEntry setStaffTag(String staffTag) {
        this.staffTag = staffTag;
        return this;
    }

    public BanEntry setStaffID(String staffID) {
        this.staffID = staffID;
        return this;
    }

    public BanEntry setReason(String reason) {
        this.reason = reason;
        return this;
    }

}
