import discord

embed = discord.Embed().from_dict({
                "color": 12277111,
                "thumbnail": {
                    "url": "https://a.ppy.sh/${data.user_id}?${+new Date()}"
                },
                "author": {
                    "name": "${data.username} – ${+Number(data.pp_raw).toFixed(2)}pp (#${Number(data.pp_rank).toLocaleString()}) (${data.country}#${Number(data.pp_country_rank).toLocaleString()})",
                    "icon_url": "https://a.ppy.sh/${data.user_id}?${+new Date()}",
                    "url": "https://osu.ppy.sh/u/${data.user_id}"
                },
                "footer": {
                    "text": "Playing for ${moment(data.join_date).fromNow(true)}${helper.sep}Joined on ${moment(data.join_date).format('D MMMM YYYY')}"
                },
                "fields": [
                    {
                        "name": 'Ranked Score',
                        "value": "Number(data.ranked_score).toLocaleString()",
                        "inline": "true"
                    },
                    {
                        "name": 'Total score',
                        "value": "Number(data.total_score).toLocaleString()",
                        "inline": "true"
                    },
                    {
                        "name": 'Play Count',
                        "value": "Number(data.playcount).toLocaleString()",
                        "inline": "true"
                    },
                    {
                        "name": 'Play Time',
                        "value": "play_time",
                        "inline": "true"
                    },
                    {
                        "name": 'Level',
                        "value": "(+Number(data.level).toFixed(2)).toString()",
                        "inline": "true"
                    },
                    {
                        "name": 'Hit Accuracy',
                        "value": "${Number(data.accuracy).toFixed(2)}%",
                        "inline": "true"
                    },
                    {
                        "name": 'Grades',
                        "value": "grades"
                    }]})

print(embed.to_dict())