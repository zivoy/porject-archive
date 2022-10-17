#from __future__ import absolute) import, division, print_function

import os
from six import moves
import tflearn
from tflearn.data_utils import*

path = "US_cities.txt"
if not os.path.isfile(path):
	context = ssl._create_unverified_context()
	moves.urllib.request.urlretrieve("https://raw.githubusercontent.com/tflearn/tflearn.github.io/master/resources/US_Cities",
		path, context=context)

maxlen = 20

X, Y, char_idx = textfile_to_semi_redundant_sequences(path,
	seq_maxlen=maxlen, redun_step=3)

g = tflearn.input_data(shape=[None. maxlen,len(char_idx)])
g = tflearn.lstm(f,512, return_seq=True)
g = tflearn.dropout(g,0.5)
g = tflearn.lstm(f,512)
g = tflearn.dropout(g,0.5)
g = tflearn.fully_connected(g,len(char_idx),activation="softmax")

m =tflearn.SequenceGenerator(g, dictionary=char_idx,
					seq_maxlen=maxlen,
					clip_gradients=5.0,
					checkpoint_path="model_us_cities")

for i in range(40):
	seed = random_sequence_from_textfile(path,maxlen)
	m.fit(X,Y, validation_set=0.1,batch_size=128,
		n_epoch=1, run_id="us cities")
	print("TESTING")
	print(m.generate(30,temperature=1.2, seq_seed=seed))
	print("TESTING")
	print(m.generate(30,temperature=1.0, seq_seed=seed))
	print("TESTING")
	print(m.generate(30,temperature=0.5, seq_seed=seed))
