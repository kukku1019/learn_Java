# coding:utf-8

#images([0.1.0.1,1・・・・・x],[0.1.0.1,1・・・・・x],[0.1.0.1,1・・・・・x]・・・)
#label([0,0,0,0,1,0,0,0,0},[1,0,0,0,0,0,0,0,0}・・・)
#mint dataはグレースケールである点に注意


import os
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets('MNIST_data', one_hot=True)

from PIL import Image
DATA_WIDTH=28
DATA_HEIGHT=28
OUTPUT_FOLDER='./output/'
image_buffer = Image.new('RGB', (DATA_WIDTH, DATA_HEIGHT), (255, 255, 255))

if not os.path.exists(OUTPUT_FOLDER):
os.makedirs(OUTPUT_FOLDER)

#文字データの個数すべて
for i in range(len(mnist.train.images)):
print 'no:' + str(i)
for d_label in range(len(mnist.train.labels[i])):
#この文字データは何の数字か確認する。9個の中で一つだけ1のはず。
if mnist.train.labels[i, d_label] == 1:
label = d_label
break
for y in range(DATA_HEIGHT):
for x in range(DATA_WIDTH):
z = 255 - int(mnist.train.images[i, x + y * DATA_WIDTH ] * 255)
#x,y番目のRBGを設定
image_buffer.putpixel((x, y), (z, z, z))
#生成したイメージを保存
image_buffer.save(OUTPUT_FOLDER + str(i) + '-' + str(d_label) + '.jpg', 'JPEG', quality=100, optimize=True)
