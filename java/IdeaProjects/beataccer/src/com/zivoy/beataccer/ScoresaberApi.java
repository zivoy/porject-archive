package com.zivoy.beataccer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// different api links
// https://beatsaver.com/api/maps/detail/ff9
// https://new.scoresaber.com/api/player/2429129807113296/scores/top/1
// https://new.scoresaber.com/api/player/76561198158987204/full
// https://new.scoresaber.com/api/players/by-name/Vero
// todo implement the player search using username
// todo map detail

public class ScoresaberApi extends api {
    // regular expression to get the uid from links
    private static final Pattern uid = Pattern.compile("scoresaber\\.com/u/(\\d+)");
    public long id;
    public boolean valid;

    public String name;
    public int rank;
    public int countryRank;
    public float pp;
    public String country;

    public int totalPlayCount;
    public int rankedPlayCount;

    public double averageAcc;

    public int rankedPages;

    public Play[] rankedPlays;
    public Double[] accList;

    public DownloadRanked downloadRanked;

    public ScoresaberApi(long id) {
        fakeConstructor(id);
    }

    public ScoresaberApi(String idLink) {
        try {
            fakeConstructor(Long.parseLong(idLink));  // if just the id was given in string format
        } catch (java.lang.NumberFormatException e) {
            Matcher m = uid.matcher(idLink);
            if (m.find())
                fakeConstructor(Long.parseLong(m.group(1))); // if a link was given
        }
    }

    public static void main(String[] args) {
        // testing purposes
        System.out.println(new ScoresaberApi("https://new.scoresaber.com/u/76561198158987204").isValid());
        System.out.println(new ScoresaberApi(7656119815898704L).isValid());
    }

    public DownloadRanked makePageDownloader(JButton button, JProgressBar progressBar, JFrame frame, Supplier<Void> end) {
        this.downloadRanked = new DownloadRanked(button, progressBar, frame, end);
        return this.downloadRanked;
    }

    public boolean isValid() {
        return valid;
    }

    public void fakeConstructor(long id) {
        this.id = id;
        getUser();
    }

    private void getUser() {
        try {
            // fetch user
            JSONObject userData = getJson("https://new.scoresaber.com/api/player/" + this.id + "/full");
            JSONObject playerInfo = (JSONObject) userData.get("playerInfo");
            this.name = (String) playerInfo.get("playerName");
            this.rank = ((Long) playerInfo.get("rank")).intValue();
            this.countryRank = ((Long) playerInfo.get("countryRank")).intValue();
            this.pp = ((Double) playerInfo.get("pp")).floatValue();
            this.country = (String) playerInfo.get("country");

            JSONObject scoreStats = (JSONObject) userData.get("scoreStats");
            this.totalPlayCount = ((Long) scoreStats.get("totalPlayCount")).intValue();
            this.rankedPlayCount = ((Long) scoreStats.get("rankedPlayCount")).intValue();
            this.averageAcc = (double) scoreStats.get("averageRankedAccuracy");

            // there is 8 scores per page if there is a decimal it means that there is part of the scores on the next page so we round up
            this.rankedPages = (int) Math.ceil(this.rankedPlayCount / 8.);

            this.rankedPlays = new Play[this.rankedPlayCount];
            this.accList = new Double[this.rankedPlayCount];

            this.valid = true;
        } catch (Exception e) {
            valid = false; // if getting the user failed it means its invalid
        }
    }

    JSONArray getPlayPage(int pageNumber) {
        try {
            // fetch specified page
            return (JSONArray) getJson("https://new.scoresaber.com/api/player/" + this.id + "/scores/top/" + pageNumber)
                    .get("scores");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    class DownloadRanked extends SwingWorker<Void, Void> {
        JButton button;
        JProgressBar progressBar;
        JFrame frame;
        Supplier<Void> end;

        public DownloadRanked(JButton button, JProgressBar progressBar, JFrame frame, Supplier<Void> end) {
            this.button = button;
            this.progressBar = progressBar;
            this.frame = frame;
            this.end = end;
        }

        @Override
        public Void doInBackground() {
            // setup progress bar
            setProgress(0);
            progressBar.setStringPainted(true);

            // dont do anything if user is invalid
            if (!isValid()) return null;

            for (int i = 1; i <= rankedPages; i++) {
                try {
                    Thread.sleep(100);  // add a small delay as to not spam scoresaber with requests
                } catch (InterruptedException ignore) { }

                progressBar.setString(i + "/" + rankedPages);

                JSONArray page = getPlayPage(i);
                for (int j = 0; j < 8; j++) {
                    int loc = (i - 1) * 8 + j;
                    // add plat to list of plays and add the accuracy to a dedicated list
                    rankedPlays[loc] = new Play((JSONObject) page.get(j));
                    accList[loc] = rankedPlays[loc].accuracy;

                    setProgress((int) Math.round(100. * loc / rankedPlayCount));
                }
            }
            return null;
        }

        @Override
        public void done() {
            // sort the accuracy list
            Arrays.sort(accList);

            button.setEnabled(true);
            frame.setCursor(null); // turn off the wait cursor
            progressBar.setVisible(false);

            // execute the end function
            this.end.get();
        }
    }
}
