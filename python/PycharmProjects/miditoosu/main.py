import argparse
import os
import subprocess
import sys
import zipfile

import mido


# "Rachmaninoff_Piano_Concerto_No_3_1st_Movement_for_2_PianosPS2.mid" -N "Rachmaninoff Piano Concerto No 3 1st Movement for 2 Pianos" -C marshall -A "Paul Schuddeboom" -V "Piano time"
#"Ouverture_Opus_39_No._11_in_B_Minor.mid"  -N "Alkan Ouverture Opus 39 No. 11 in B Minor" -C zivoy -A "ClassicMan" -V "Big hand"

class invalidFile(Exception): ...


# fluidsynth fluid-soundfont-gm twolame

parser = argparse.ArgumentParser()
parser.add_argument("midi", help="midi file fo music", action="store")
parser.add_argument("-N", "--map-name", action="store", default="noname", dest="map_name", nargs=1,
                    help="Name of the mapset")
parser.add_argument("-C", "--map-creator", action="store", default="creator name", dest="map_creator", nargs=1,
                    help="creator of beatmapset")
parser.add_argument("-A", "--artist-name", action="store", default="artist name", dest="artist_name", nargs=1,
                    help="name of song creator")
parser.add_argument("-V", "--difficulty-name", action="store", default="version name", dest="diff_name", nargs=1,
                    help="name of the beatmap difficulty")
args = None


def main():
    global args
    args = parser.parse_args()
    if not args.midi:
        raise invalidFile("No file provided")
    file = args.midi
    if not os.path.exists(file):
        raise invalidFile(f"{file} is an invalid path")

    if args.map_name == "noname":
        args.map_name = file

    lengthen(file)
    make_audio("audio.mid")
    mapname = midi_to_map("audio.mid")

    with zipfile.ZipFile(mapname + ".osz", "w") as zp:
        zp.write("audio.mp3")
        zp.write(mapname + ".osu")

    os.remove(mapname + ".osu")
    os.remove("audio.mid")
    os.remove("audio.mp3")


def lengthen(file):
    mid = mido.MidiFile(file)
    for i in range(len(mid.tracks)):
        mid.tracks[i][0].time += 2000
        mid.tracks[i][-1].time += 2000 - 1
    mid.save("audio.mid")


def midi_to_map(file):
    mid = mido.MidiFile(file)

    osumap = osuFile()

    osumap.Metadata.Title = args.map_name[0]
    osumap.Metadata.Artist = args.artist_name[0]
    osumap.Metadata.Creator = args.map_creator[0]
    osumap.Metadata.Version = args.diff_name[0]

    lastTimeSig = 4
    lastTempo = 0
    first = True
    for i in get_timing_points(mid):
        if i.type == 'time_signature':
            lastTimeSig = i.numerator
            osumap.Editor.BeatDivisor = i.denominator
            if first:
                first = False
            else:
                if len(osumap.TimingPoints.Points) >= 1 and \
                        osumap.TimingPoints.Points[-1].time == int(round(i.time * 1000)):
                    osumap.TimingPoints.Points[-1].meter = i.numerator
                else:
                    timeP = osumap.TIMINGPOINT(int(round(i.time * 1000)), int(round(lastTempo / 1000)), i.numerator)
                    osumap.TimingPoints.Points.append(timeP)
        elif i.type == "set_tempo":
            lastTempo = i.tempo
            if len(osumap.TimingPoints.Points) >= 1 and \
                    osumap.TimingPoints.Points[-1].time == int(round(i.time * 1000)):
                osumap.TimingPoints.Points[-1].beatLength = int(round(i.tempo / 1000))
            else:
                timeP = osumap.TIMINGPOINT(int(round(i.time * 1000)), int(round(i.tempo / 1000)), lastTimeSig)
                osumap.TimingPoints.Points.append(timeP)

    name = f"{osumap.Metadata.Artist} - {osumap.Metadata.Title} ({osumap.Metadata.Creator}) [{osumap.Metadata.Version}]"

    with open(name + ".osu", "w+") as mp:
        mp.write(str(osumap))

    return name


def get_timing_points(midi):
    rolup = 0
    first = True
    for i in midi:
        rolup += i.time
        if first:
            rolup -= i.time - 2
            first = False
        if i.type in {"set_tempo", 'time_signature'}:
            j = i.copy()
            j.time = rolup
            yield j


def make_audio(file):
    if sys.platform == "win32":
        wsl = "wsl "
    else:
        wsl = ""

    soundfonts = ["/usr/share/sounds/sf2/FluidR3_GM.sf2",
                  "GeneralUser.sf2",
                  "Sonatina_Symphonic_Orchestra.sf2",
                  "Timbres_Of_Heaven.sf2"]

    subprocess.call(f"{wsl}fluidsynth -l -T raw -F - {soundfonts[2]} {file} |"
                    f" twolame -b 64 -r - audio.mp3")  # bitrate: 128
    # timidity '{file}' -Ow -o - | ffmpeg -i - -acodec libmp3lame -ab 64k audio.mp3")


class osuFile:
    def __init__(self):
        self.version = "osu file format v14"
        self.General = self.GENERAL()
        self.Editor = self.EDITOR()
        self.Metadata = self.METADATA()
        self.Difficulty = self.DIFFICULTY()
        self.Events = self.EVENTS()
        self.TimingPoints = self.TIMIMGPOINTS()
        self.HitObjects = self.HITOBJECTS()

    def __str__(self):
        string = self.version
        string += "\n\n"

        string += str(self.General)
        string += "\n\n"

        string += str(self.Editor)
        string += "\n\n"

        string += str(self.Metadata)
        string += "\n\n"

        string += str(self.Difficulty)
        string += "\n\n"

        string += str(self.Events)
        string += "\n\n"

        string += str(self.TimingPoints)
        string += "\n\n"

        string += str(self.HitObjects)

        string += "\n"

        return string

    class GENERAL:
        def __init__(self, AudioFilename="audio.mp3", udioLeadIn=0, PreviewTime=-1, Countdown=0,
                     SampleSet="Soft", StackLeniency=5, Mode=0, LetterboxInBreaks=0, WidescreenStoryboard=1):
            self.AudioFilename = AudioFilename
            self.udioLeadIn = udioLeadIn
            self.PreviewTime = PreviewTime
            self.Countdown = Countdown
            self.SampleSet = SampleSet
            self.StackLeniency = StackLeniency
            self.Mode = Mode
            self.LetterboxInBreaks = LetterboxInBreaks
            self.WidescreenStoryboard = WidescreenStoryboard

        def __str__(self):
            return "[General]\n" + osuFile.stringattr(self)

    class EDITOR:
        def __init__(self, DistanceSpacing=1, BeatDivisor=1, GridSize=32, TimelineZoom=1):
            self.DistanceSpacing = DistanceSpacing
            self.BeatDivisor = BeatDivisor
            self.GridSize = GridSize
            self.TimelineZoom = TimelineZoom

        def __str__(self):
            return "[Editor]\n" + osuFile.stringattr(self)

    class METADATA:
        def __init__(self, Title="", TitleUnicode="", Artist="", ArtistUnicode="", Creator="",
                     Version="", Source="", Tags="", BeatmapID=0, BeatmapSetID=-1):
            self.Title = Title
            self.Artist = Artist
            self.Creator = Creator
            self.TitleUnicode = TitleUnicode
            self.ArtistUnicode = ArtistUnicode
            self.Version = Version
            self.Source = Source
            self.Tags = Tags
            self.BeatmapID = BeatmapID
            self.BeatmapSetID = BeatmapSetID

        def __str__(self):
            return "[Metadata]\n" + osuFile.stringattr(self, False)

    class DIFFICULTY:
        def __init__(self, HPDrainRate=5, CircleSize=5, OverallDifficulty=5,
                     ApproachRate=5, SliderMultiplier=1, SliderTickRate=1):
            self.HPDrainRate = HPDrainRate
            self.CircleSize = CircleSize
            self.OverallDifficulty = OverallDifficulty
            self.ApproachRate = ApproachRate
            self.SliderMultiplier = SliderMultiplier
            self.SliderTickRate = SliderTickRate

        def __str__(self):
            return "[Difficulty]\n" + osuFile.stringattr(self)

    class EVENTS:
        def __init__(self, Background=None, Breaks=None, SbLayer0=None, SbLayer1=None, SbLayer2=None, SbLayer3=None,
                     SbLayer4=None, SbSound=None):
            if SbSound is None:
                SbSound = list()
            if SbLayer3 is None:
                SbLayer3 = list()
            if SbLayer2 is None:
                SbLayer2 = list()
            if SbLayer4 is None:
                SbLayer4 = list()
            if SbLayer1 is None:
                SbLayer1 = list()
            if SbLayer0 is None:
                SbLayer0 = list()
            if Breaks is None:
                Breaks = list()
            if Background is None:
                Background = list()
            self.SbLayer0 = SbLayer0
            self.SbLayer1 = SbLayer1
            self.SbLayer2 = SbLayer2
            self.SbLayer3 = SbLayer3
            self.SbLayer4 = SbLayer4
            self.SbSound = SbSound
            self.Breaks = Breaks
            self.Background = Background

        def __str__(self):
            string = "[Events]\n"
            string += "//Background and Video events\n"
            string += "\n".join(self.Background)
            string += "\n" if self.Background else ""
            string += "//Break Periods\n"
            string += "\n".join(self.Breaks)
            string += "\n" if self.Breaks else ""
            string += "//Storyboard Layer 0 (Background)\n"
            string += "\n".join(self.SbLayer0)
            string += "\n" if self.SbLayer0 else ""
            string += "//Storyboard Layer 1 (Fail)\n"
            string += "\n".join(self.SbLayer1)
            string += "\n" if self.SbLayer1 else ""
            string += "//Storyboard Layer 2 (Pass)\n"
            string += "\n".join(self.SbLayer2)
            string += "\n" if self.SbLayer2 else ""
            string += "//Storyboard Layer 3 (Foreground)\n"
            string += "\n".join(self.SbLayer3)
            string += "\n" if self.SbLayer3 else ""
            string += "//Storyboard Layer 4 (Overlay)\n"
            string += "\n".join(self.SbLayer4)
            string += "\n" if self.SbLayer4 else ""
            string += "//Storyboard Sound Samples"
            string += "\n" if self.SbSound else ""
            string += "\n".join(self.SbSound)

            return string

    class TIMIMGPOINTS:
        def __init__(self, Points=None):
            if Points is None:
                Points = list()
            self.Points = Points

        def __str__(self):
            string = "[TimingPoints]\n"
            string += "\n".join([str(i) for i in self.Points])

            return string

    class HITOBJECTS:
        def __init__(self, Objects=None):
            if Objects is None:
                Objects = list()
            self.Objects = Objects

        def __str__(self):
            string = "[HitObjects]"
            string += "\n".join(self.Objects)

            return string

    class TIMINGPOINT:
        def __init__(self, time, beatLength, meter=4, sampleSet=0, sampleIndex=0, volume=100, uninherited=1, effects=0):
            self.effects = effects
            self.uninherited = uninherited
            self.volume = volume
            self.sampleIndex = sampleIndex
            self.sampleSet = sampleSet
            self.meter = meter
            self.beatLength = beatLength
            self.time = time

        def __str__(self):
            return f"{self.time},{self.beatLength},{self.meter},{self.sampleSet}," \
                   f"{self.sampleIndex},{self.volume},{self.uninherited},{self.effects}"

    @staticmethod
    def stringattr(cls, space=True):
        string = ""
        for i in dir(cls):
            if not i.startswith('__'):
                string += f"{i}:{' ' if space else ''}{getattr(cls, i)}\n"
        return string[:-1]


if __name__ == "__main__":
    try:
        main()
    except invalidFile as e:
        print(e)
