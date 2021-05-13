#실습 7번: wc

import sys

f1=open(sys.argv[1],'r')

#file1은 실습6번에서 만들어진 file1을 이용했습니다.

line_check=0    #라인 수를 셀 변수
words_check=0   #단어 수를 셀 변수
check=[]    #라인별 공백으로 단어를 나누고 넣을 리스트

for line in f1:
    line_check+=1
    check=line.split()
    words_check+=len(check)

f1.close()

print("라인 수: {0:d} / 단어 수: {1:d}".format(line_check,words_check))
