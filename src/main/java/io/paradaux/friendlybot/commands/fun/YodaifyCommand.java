/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.fun.YodaifyCommand :  06/02/2021, 10:49
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.paradaux.friendlybot.commands.fun;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.HttpUtils;
import io.paradaux.friendlybot.utils.NumberUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;

public class YodaifyCommand extends BaseCommand {

    private static final String YODA_IMAGE_ID = "14371066";

    public YodaifyCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "yodaify";
        this.aliases = new String[]{"yoda", "yodify"};
        this.help = "Convert english into Yoga's Language!";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        if (event.getArgs().isEmpty()) {
            respondSyntaxError(message, ";yoda <sentence>");
            return;
        }

        String[] tokenisedSentence = event.getArgs().split("\\. *");

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < tokenisedSentence.length; i++) {
            String[] temp = tokenisedSentence[i].split(" ");
            builder.append(temp[temp.length - 1]);
            for (int x = 0; x < temp.length - 1; x++){
                builder.append(" ").append(temp[x]);
            }
            builder.append(". ");
        }

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("template_id", YODA_IMAGE_ID)
                .addFormDataPart("username", getConfig().getImgflipUsername())
                .addFormDataPart("password", getConfig().getImgflipPassword());

        bodyBuilder.addFormDataPart("text0", builder.toString());

        RequestBody body = bodyBuilder.build();

        Request request = new Request.Builder()
                .url(MemeCommand.CAPTION_MEMES_API)
                .method("POST", body)
                .build();

        HttpUtils.sendAsync(client, request).thenAccept((response -> {
            if (response.body() == null) {
                throw new IllegalStateException("No response received.");
            }

            try (Reader reader = response.body().charStream()) {
                int charInt;
                StringBuilder strBuilder = new StringBuilder();
                while ((charInt = reader.read()) != -1) {
                    strBuilder.append((char) charInt);
                }

                JSONObject responseJson = new JSONObject(strBuilder.toString());

                MessageEmbed embed = new EmbedBuilder()
                        .setImage(responseJson.getJSONObject("data").getString("url"))
                        .setColor(NumberUtils.randomColor())
                        .setFooter("For " + event.getAuthor().getName())
                        .build();

                event.getChannel().sendMessage(embed).queue();

            } catch (IOException ok) {
                getLogger().error("Error occurred whilst interacting with Imgflip");
            }
        })).join();

    }
}
