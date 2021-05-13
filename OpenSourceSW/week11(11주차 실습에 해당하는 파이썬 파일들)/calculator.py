#실습 9번

class Stack:
    def __init__(self):
        self.top=[]

    def size(self):
        return len(self.top)
    
    def isEmpty(self):
        return self.size()==0
    
    def push(self,item):
        self.top.append(item)

    def pop(self):
        if not self.isEmpty():
            return self.top.pop()
        else:
            print("Stack underflow")

def calc(OP,Num):
    op=OP.pop()
    n2=Num.pop()
    n1=Num.pop()
    print("{0:d}, {1:d}".format(n1,n2))
    if op=='+':
        Num.push(n1+n2)
    elif op=='-':
        Num.push(n1-n2)
    elif op=='*':
        Num.push(n1*n2)
    else:
        Num.push(n1/n2)
    return OP, Num
        

origin=input("계산식 입력: ").split()
operator=['+','-','*','/']
Num=Stack()
OP=Stack()

for i in range(0,len(origin)):
    a=origin[i]
    if a in operator:
        OP.push(a)
        check=i
    else:
        Num.push(int(a))
        
    if Num.size()>=2 and OP.size()==Num.size()-1:
        while(Num.size()!=1):
            if check<(len(origin)-2):
                if origin[check+2] in '*/':
                    break
            OP,Num=calc(OP,Num)
                
             

print("계산식 출력: ",end="")
for i in origin:
    print(i,end="")

print("={0:d}".format(Num.pop()))
 
