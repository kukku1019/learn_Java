# -*- coding: utf-8 -*-
#!/usr/bin/env python
"""
Created on Fri Jun 23 15:45:21 2017

@author: dev01
"""
import nueralnet as nue
import numpy
import matplotlib.pyplot



# number of input, hidden and output nodes
#　入力ノード数　隠れ層ノード数　出力層ノード数設定
input_nodes = 784
hidden_nodes = 200
output_nodes = 10

# learning rate
#学習率
learning_rate = 0.1
##epochs はトレーニングデータをトレーニング用に使用する回数
epochs = 5


neuralNetwork=nue.neuralNetwork


# create instance of neural network
#ニューラルネットワークのインスタンス生成
n = neuralNetwork(input_nodes,hidden_nodes,output_nodes, learning_rate)


# load the mnist training data CSV file into a list
#csvデータを読み込む
training_data_file = open("C:\\Users\\dev01\\mnist_dataset\\mnist_train_100.csv", 'r')
#行ごとにメモリに取り込む
training_data_list = training_data_file.readlines()
#ファイルを閉じる
training_data_file.close()


# train the neural network
#　ニューラルネットワークをトレーニングする
# epochs is the number of times the training data set is used for training
#epochs はトレーニングデータをトレーニング用に使用する回数
#epochs = 5

for e in range(epochs):
    # go through all records in the training data set
    #訓練データの全データに対して実行
    for record in training_data_list:
        # split the record by the ',' commas
        #データをコンマ','でsplit(区切る）
        all_values = record.split(',')
        # scale and shift the inputs
        # 入力データを変形させる(上限下限調整）　（シフト、スケーリング）
        inputs = (numpy.asfarray(all_values[1:]) / 255.0 * 0.99) + 0.01
        # create the target output values (all 0.01, except the desired label which is 0.99)
        #目標出力の生成（ラベルの位置が0.99、残りは0.01）
        targets = numpy.zeros(output_nodes) + 0.01

        # all_values[0] is the target label for this record
        # all_values[0]はこのデータのラベル
        targets[int(all_values[0])] = 0.99
        n.train(inputs, targets)
        pass
    pass

# load the mnist test data CSV file into a list
test_data_file = open("C:\\Users\\dev01\\mnist_dataset\\mnist_test_10.csv", 'r')
test_data_list = test_data_file.readlines()
test_data_file.close()

# test the neural network
#ニューラルネットワークテスト
# scorecard for how well the network performs, initially empty
#scorecard は判定のリスト、最初は空
scorecard = []

# go through all the records in the test data set
#テストデータのすべてのデータに対して実行
for record in test_data_list:
    # split the record by the ',' commas
#データを”、”で区切る
    all_values = record.split(',')
    # correct answer is first value
    #正解は配列の1番目
    correct_label = int(all_values[0])
    #if(correct_label==7):
    print(correct_label)
    # scale and shift the inputs
    image_array = numpy.asfarray(all_values[1:]).reshape((28,28))
    matplotlib.pyplot.imshow(image_array, cmap='Greys',interpolation='None')
    inputs = (numpy.asfarray(all_values[1:]) / 255.0 * 0.99) + 0.01
    


    # query the network
    outputs = n.query(inputs)
    # the index of the highest value corresponds to the label
    label = numpy.argmax(outputs)
    # append correct or incorrect to list
    if (label == correct_label):
        # network's answer matches correct answer, add 1 to scorecard
        scorecard.append(1)
    else:
        # network's answer doesn't match correct answer, add 0 to scorecard
        scorecard.append(0)
        pass

    pass


# calculate the performance score, the fraction of correct answers
scorecard_array = numpy.asarray(scorecard)
s=scorecard_array.sum() / scorecard_array.size
print ("performance(評価値) = ", s)


#image_array = numpy.asfarray(all_values[1:]).reshape((28,28))
#matplotlib.pyplot.imshow(image_array, cmap='Greys',interpolation='None')
