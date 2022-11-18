import sys, eyed3, glob
from mp3_tagger import MP3File, VERSION_1
from utils import *
from gmusicapi import Mobileclient
import urllib.request as request

class Music:
    def __init__(self):
        self.api = Mobileclient()
        self.logged_in = self.api.login('email', 'password', 'key')

    def play(self, song : str):

        def s():
            results = self.api.search(song, max_results=1)
            with open("output.txt", "wb") as f:
                f.write(str(results["song_hits"]).encode(sys.stdout.encoding, errors='replace'))
            track = results['song_hits'][0]['track']
        #    print(track)
            song_id = track['storeId']
            artist = track['artist']
            album = track['album']
            title = track['title']
            track_nr = track['trackNumber']
            year = track['year']
            genre = track['genre']
            album_artist = track['albumArtist']
            album_art = track['albumArtRef'][0]['url']
            #tot_track = track['totalTrackCount']
           # tot_disk = track['totalDiscCount']
            disk = track['discNumber']
            url = self.api.get_stream_url(song_id)
            #print(track, song_id, artist, album, title, track_nr, year, genre, album_artist)
            return url, title, artist, album_art, album, track_nr, year, genre, album_artist, disk#, tot_track, tot_disk

        track_url, title, artist, album_art, album, track, year, genre, album_artist, disk = s()

        #print('track_url','\n', title,'\n', artist, '\n',album_art)

        track_raw = request.urlopen(track_url)
        #print(title)
        title = title.replace("/","-")
        #print(title)
        filepath = r'./music/{}_{}.mp3'.format(artist, title)
        imgpath = r'./music/{}_{}.jpg'.format(artist, title)

        #print(">>>>>: ", filepath)
        if filepath not in glob.glob("./music/*.mp3"):
            print("Downloading {}'s {}".format(artist, title))
            with open(filepath, "wb") as track_file:
                track_file.write(track_raw.read())
                track_file.close()
            request.urlretrieve(album_art, imgpath)
            #print(album_art, filepath)

            mp3 = MP3File(filepath)
            mp3.set_version(VERSION_1)
            mp3.song = title
            mp3.album = album
            #mp3.album_artist = album_artist
            mp3.artist = artist
            mp3.disc_num = disk
            mp3.track = str(track)
            mp3.year = str(year)
            mp3.genre = 255 #genre
            mp3.save()
#https://media.readthedocs.org/pdf/eyed3/latest/eyed3.pdf
            audiofile = eyed3.load(filepath, 0)
            if (audiofile.tag == None):
                audiofile.initTag()

            tag = audiofile.tag
            tag.images.set(2, open(imgpath,'rb').read(), 'image/jpeg')
            tag.images.set(3, open(imgpath,'rb').read(), 'image/jpeg')
            tag.save()

          #  tag.images.set(3, request.urlretrieve(album_art), 'image/jpeg')



print('format Artist name - Song name')
inSong = input('Type \"exit()\" to exit\n>>> ')

while inSong.lower() != "exit()":
   # print(inSong)
    Music().play(inSong)
    inSong = input('>>> ')
