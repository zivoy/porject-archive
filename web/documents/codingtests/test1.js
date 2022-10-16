url = "https://raw.githubusercontent.com/rithik-b/PlaylistManager/master/img/easteregg.bplist"
// url = "https://scoresaber.balibalo.xyz/top200.bplist"

$.get(
    url,
    (d) => {
        s = JSON.parse(d.replace("}, ],", "} ],"))
        $("img").attr("src", "data:image/png;"+s.image);

        $("#title").text(s.playlistTitle)
        $("#author").text(s.playlistAuthor)
        $("#description").text(s.playlistDescription)
        $("#songs").text("s.songs")

        // console.log(s)
    }
)

console.log("test1")