import re
import requests
from bs4 import BeautifulSoup
import os
langs={"CN": 495, "JP": 496, "KR": 497}
pages=10
def getLink(language ,page):
    link = "https://www.novelupdates.com/series-finder/?sf=1&org={}&sort=sdate&order=desc&pg={}".format(langs[language], page)
    return link
for lang in langs:
    os.mkdir(lang)
    print(f"starting {lang}...")
    for pg in range(1, 1 + pages):
        site = getLink(lang, pg)
        response = requests.get(site)
        soup = BeautifulSoup(response.text, 'html.parser')
        img_tags = soup.find_all('img')
        urls = [img['src'] for img in img_tags]
        for url in urls:
            #print ('starting url {}'.format(url))
            filename = re.search(r'/series_([1-9].+jpg)$', url)
            try:
                #print('in try' , filename)
                if filename.group(1) != "noimagemid.jpg":
                    with open("{}/{}".format(lang,filename.group(1)), 'wb') as f:
                        if 'http' not in url:
                            url = '{}{}'.format(site, url)
                        response = requests.get(url)
                        f.write(response.content)
            except:
                #print(url) # not a image from novelupdates
                pass
    print("done!")
