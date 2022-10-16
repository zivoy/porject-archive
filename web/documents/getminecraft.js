infoUrl = "https://api.mcsrvstat.us/2/minecraft.shalit.name";

var loading = document.getElementById("loading");
var offline = document.getElementById("noConnection");
var online = document.getElementById("stats");


var image = document.getElementById("server-icon");
var version = document.getElementById("version-text");
var playerBar = document.getElementById("player-amount-bar");
var playerText = document.getElementById("player-amount-text");
var motd = document.getElementById("motd");

fetch(infoUrl)
    .then(res => res.json())
    .then((out) => {
        loading.classList.add("is-hidden");
        // console.log(out);
        if (out.online) {
            online.classList.remove("is-hidden");

            image.src = out.icon;
            version.innerHTML = `Server is running ${out.software} on Minecraft version ${out.version}`;
            playerText.innerHTML = `${out.players.online} / ${out.players.max}`;
            playerBar.value = out.players.online;
            playerBar.max = out.players.max;
            motd.innerHTML = ""

            out.motd.html.forEach((item, i) => {
                motd.innerHTML = `${motd.innerHTML}${item}\n`
            });


        } else {
            offline.classList.remove("is-hidden");
        }
    })
    .catch(err => {
        document.getElementById("server-stats").classList.add("is-hidden");
        throw err
    });
