end = "1ea557b88731756eb3b84bf0f7b8f8500e26a1f096aa36e4e19e3516"
from hashlib import sha224
now = 0.028392123105
while sha224(str(now)).hexdigest()!=end:
    #print sha224(str(now)).hexdigest()+ " " + '%.12f' % now
    now+=0.000000000001
print '%.12f' % now
