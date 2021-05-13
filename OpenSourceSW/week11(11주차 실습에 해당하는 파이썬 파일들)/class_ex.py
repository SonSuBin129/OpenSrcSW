#실습 8번: 사칙 연산 클래스

class Calc:
    def sum(self,a,b):
        result=a+b
        print("{0:d} + {1:d} = {2:d} 입니다.".format(a,b,result))
    def sub(self,a,b):
        result=a-b
        print("{0:d} - {1:d} = {2:d} 입니다.".format(a,b,result))
    def multi(self,a,b):
        result=a*b
        print("{0:d} * {1:d} = {2:d} 입니다.".format(a,b,result))
    def divi(self,a,b):
        result=a/b
        print("{0:d} / {1:d} = {2:d} 입니다.".format(int(a),int(b),int(result)))
    
