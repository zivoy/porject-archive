package com.zivoy.beataccer;

import org.json.simple.JSONObject;

/**
 * play object
 * stores info on a play
 */
public class Play {
    public String songName;
    public String songHash;
    public int rank;
    public int scoreId;
    public long score;
    public long unmodififiedScore;
    public String mods;
    public float pp;
    public double weight;
    public String timeSet;
    public int leaderboardId;
    public String songSubName;
    public String songAuthorName;
    public String levelAuthorName;
    public int difficulty;
    public String difficultyRaw;
    public long maxScore;
    public double accuracy;

    public Play(JSONObject arr) {
        this.songName = (String) arr.get("songName");
        this.songHash = (String) arr.get("songHash");
        this.rank = ((Long) arr.get("rank")).intValue();
        this.scoreId = ((Long) arr.get("scoreId")).intValue();
        this.mods = (String) arr.get("mods");
        this.timeSet = (String) arr.get("timeSet");
        this.songSubName = (String) arr.get("songSubName");
        this.songAuthorName = (String) arr.get("songAuthorName");
        this.levelAuthorName = (String) arr.get("levelAuthorName");
        this.difficultyRaw = (String) arr.get("difficultyRaw");
        this.score = (long) arr.get("score");
        this.unmodififiedScore = (long) arr.get("unmodififiedScore");
        this.maxScore = (long) arr.get("maxScore");

        // if the weight == 1 then it will be a long in the json but im storing it as a double
        if (arr.get("weight").getClass().equals(Long.class))
            this.weight = ((Long) arr.get("weight")).doubleValue();
        else
            this.weight = (Double) arr.get("weight");

        // if someone has exactly and integer valued number of pp then it will be stored as a long but
        // im storing it as a float
        if (arr.get("pp").getClass().equals(Long.class))
            this.pp = ((Long) arr.get("pp")).floatValue();
        else
            this.pp = ((Double) arr.get("pp")).floatValue();

        this.leaderboardId = ((Long) arr.get("leaderboardId")).intValue();
        // this seems to be a byte encoded flag containing the dif (expert, expert+, etc), and the play style(one saber, standard, etc)
        this.difficulty = ((Long) arr.get("difficulty")).intValue();

        // the accuracy is not in the api but can be calculated
        this.accuracy = (this.score / (double) this.maxScore) * 100.;
    }
}
