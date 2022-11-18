otr = r"c:\envirement-vars\;C:\Users\Games\AppData\Local\Temp\yarn--1568492796880-0.7032658860526579;" \
      r"D:\theia\node_modules\.bin;C:\Users\Games\AppData\Local\Yarn\Data\link\node_modules\.bin;" \
      r"D:\libexec\lib\node_modules\npm\bin\node-gyp-bin;D:\lib\node_modules\npm\bin\node-gyp-bin;" \
      r"D:\node.js\node_modules\npm\bin\node-gyp-bin;D:\Git\mingw64\bin;D:\Git\usr\bin;C:\Users\Games\bin;" \
      r"C:\Windows;C:\Windows;C:\Program Files\JetBrains\PyCharm 2019.2\jbr\bin;" \
      r"C:\Program Files\JetBrains\PyCharm 2019.2\jbr\bin\server;C:\Python27;C:\Python27\Scripts;" \
      r"D:\python37\Scripts;D:\python37;H:\ics stuff\python\Scripts;H:\ics stuff\python;C:\Perl64\site\bin;" \
      r"C:\Perl64\bin;C:\Program Files\Oculus\Support\oculus-runtime;" \
      r"C:\Program Files (x86)\Common Files\Oracle\Java\javapath;" \
      r"C:\ProgramData\Oracle\Java\javapath;D:\ffmpeg-3.4-win64-static\bin;" \
      r"C:\Program Files (x86)\Calibre2;D:\IsoBuster;D:\PuTTY;C:\Program Files\Intel\WiFi\bin;" \
      r"C:\Program Files\Common Files\Intel\WirelessCommon;C:\Android;C:\Windows\System32;" \
      r"C:\Program Files (x86)\Wolfram Research\W;" \
      "C:\\Users\\Games\\AppData\\Roaming\\npm;D:\\gtk+\\bin\\"
o=set(otr.split(";"))
o.remove(r"C:\Program Files (x86)\Wolfram Research\W")
o.remove(r"H:\ics stuff\python")
for i in o:
    print (i)
print(len(otr))
print(len(";".join(o)))
print(";".join(o))