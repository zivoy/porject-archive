package com.zivoy.beataccer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * class for creating playlists
 * playlists consists of a name, a description, an author and a base64 encoded image
 * as well as a list of songs that have a name and the map hash
 */
public class PlayListCreator {
    String name;
    String author;
    String description;
    String image;
    Play[] songs;

    public PlayListCreator(String name, String author, String description, String image, Play[] songs) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.image = image;
        this.songs = songs;
    }

    public PlayListCreator(String playerName, Play[] songs) throws IOException {
        this(playerName + "'s accuracy improvement plan", "BeatAccer",
                "This Playlist was auto generated using the BeatAccer program for " + playerName + ".\\n\\n" +
                        "if there is any problems with the program you can contact me on discord at 'The Detenator#4604' " +
                        "don't bother sending a friend request just send a dm", getImage("/packLogo.png"), songs);

    }

    private static String getImage(String path) {
        /*
        have to read the bytes in this way instead of etResourceAsStream(path).readAllBytes() because im
        doing this in java 8 instead of 11 so its compatible with more people
        */
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        InputStream is = Main.class.getResourceAsStream(path);
        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        } catch (Exception ignored) {
        }

        return "data:image/png;base64," + Base64.getEncoder()
                .encodeToString(buffer.toByteArray());
    }

    public static void main(String[] args) throws IOException {
        // testing purposes
        PlayListCreator a = new PlayListCreator("bob", new Play[0]);
        System.out.println(a.generateJsonString());
    }

    public String generateJsonString() {
        JSONObject playlist = new JSONObject();
        playlist.put("playlistTitle", this.name);
        playlist.put("playlistAuthor", this.author);
        playlist.put("playlistDescription", this.description);
        playlist.put("image", this.image);
        JSONArray songs = new JSONArray();
        for (Play i : this.songs) {
            JSONObject song = new JSONObject();
            song.put("songName", i.songName + " --" + i.difficultyRaw.replace('_', ' '));
            song.put("hash", i.songHash);
            songs.add(song);
        }
        playlist.put("songs", songs);

        return playlist.toString();
    }
}
