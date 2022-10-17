from bs4 import BeautifulSoup
from bs4.dammit import EncodingDetector
import requests
from urllib.parse import urljoin
import os

session = requests.session()
javascript = True

blacklistDownload = [
    "youtube.com"
]


def downloader(link: str, depth: int = 1, redownload=False):
    if depth < 0 or any([i in link for i in blacklistDownload]):
        return get_path(link)

    parser = 'html.parser'  # or 'lxml' (preferred) or 'html5lib', if installed
    resp = session.get(link)
    content = str(resp.content)
    http_encoding = resp.encoding if 'charset' in resp.headers.get('content-type', '').lower() else None
    html_encoding = EncodingDetector.find_declared_encoding(resp.content, is_html=True)
    encoding = html_encoding or http_encoding
    soup = BeautifulSoup(resp.content, parser, from_encoding=encoding)

    # download other pages
    for link in soup.find_all('a', href=True):
        loc = web_path(link, link["href"])
        name = downloader(loc, depth=depth-1, redownload=redownload)
        content = content.replace(link["href"], get_path(link["href"]))
        print(link['href'])
    # for link in soup.find_all('button', href=True):
    #     print(link['href'])

    # download css elements

    # ? download js elements
    if javascript:
        for link in soup.find_all('a', href=True):
            print(link['href'])

    if not is_downloaded(link) or redownload:
        with open(get_path(link), "w") as f:
            f.write(content)

    return


def is_downloaded(link: str) -> bool:
    return os.path.exists(get_path(link))


def get_path(link: str) -> str:
    link = link.replace("https://", "").replace("http://", "")
    link = link.replace("/", "~")
    if "." not in link:
        link += ".html"
    # os.path
    return link


def web_path(base, url):
    return urljoin(base, url)


def main():
    pass


if __name__ == "__main__":
    main()
