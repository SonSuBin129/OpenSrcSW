#실습 6번:copy

userinput=input("입력: ")
li_input=userinput.split()

f1=open(li_input[1],'w')

for i in range(1,11):
    f1.write("copy test {0:d}\n".format(i))
f1.close()

#강의자료에서의 file1으로 할만한 파일을 정하지 못해서 실행될때마다 file1에 해당하는 파일을 만들게끔 했습니다.

f1=open(li_input[1],'r')
f2=open(li_input[2],'w')

for line in f1:
    f2.write(line)
f1.close()
f2.close()
