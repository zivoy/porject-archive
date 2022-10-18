import regex
import os
from urllib.parse import urljoin
import requests
import mimetypes
import json
import base64
from PIL import Image
from io import BytesIO

expression = regex.compile(r"\[([^]]+)\]\(([^\)]+)\)")

image_formats = ("image/png", "image/jpeg", "image/jpg")

def is_url_image(url):
    mimetype,encoding = mimetypes.guess_type(url)
    return (mimetype and mimetype.startswith('image'))

def string_escape(s):
    return s.replace("\\\\","\\").replace("\\/","/")

for file in ["replies.json", "threads.json", "comments.json"]:
    base64Images =dict()
    webImages = dict()
    f = json.load(open(file,"r",encoding="utf-8"))
    for line in f:
        results = expression.findall(line["content"])
        for i in results:
            name = f"{file}/{line['id']} - {i[0]}"
            uri = string_escape(i[1])#.decode('string_escape')
            if uri.lower().startswith("data:image"):
                base64Images[name] = uri
            else:
                url = urljoin("https://redstoner.com",uri)
                #if url.startswith("https://redstoner.com") or url.startswith("http://redstoner.com"):
                #    continue
                if is_url_image(url):
                    webImages[name] = url
                else:
                    pass
                    #print("not image:",url)

    dire = "".join("replies.json".split(".")[:-1])
    if not os.path.exists(dire):
        os.mkdir(dire)
    for i in base64Images:
        im = Image.open(BytesIO(base64.b64decode(i[1].strip("data:image/jpeg;base64,"))))
        im.save(os.path.join(dire,f'{i[0]}.jpg'), 'jpg')

    print("locs")
    [print(j,i) for j,i in webImages.items()]

    #print("encoded")
    #[print(j,i) for j,i in base64Images.items()]