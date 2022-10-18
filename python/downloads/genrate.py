import re
from tqdm import tqdm
import html
import os
import sys
postTypere = re.compile("PostTypeId=\"(\\d)\"")
parentIdre = re.compile("ParentId=\"(\\d+)\"")

def mkdir(name):
    try:
        os.mkdir(name)
    except:
        pass

try:
    start = int(sys.argv[1])
except:
    start = None
treehead = 'Responses'
mkdir(treehead)
open("questions.txt","w").close()
num_lines = None#sum(1 for line in open('Posts.xml', encoding="utf-8"))
with open("Posts.xml", "r", encoding="utf-8") as o:
    for i, line in tqdm(enumerate(o), total=num_lines):
        jobId = re.search(postTypere, line)
        if start and i >= start:
            if jobId:
                if int(jobId.group(1)) == 1:
                    # its a question
                    with open("questions.txt", "a+", encoding="utf-8") as q:
                        title = re.search("Title=\"([^\"]+)\"", line).group(1)
                        q.write("{} {}\n".format(i,html.unescape(title)))
                elif int(jobId.group(1)) == 2:
                    # its a response
                    parent = re.search(parentIdre, line).group(1)
                    parentdir = os.path.join(treehead, str(parent))
                    if not os.path.exists(parentdir):
                        mkdir(parentdir)
                    mkdir(os.path.join(parentdir, str(i)))
                else:
                    print(jobId.group(1))

