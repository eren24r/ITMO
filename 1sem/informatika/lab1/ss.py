def translater(x, a, b):
    x = int(x, a)
    s = ''
    l = ['A', 'B', 'C', 'D', 'E', 'F']
    while x > 0:
        d = x % b
        if d >= 10: d = l[d % 10]
        s = str(d) + s
        x = x // b
    return s



#перевод в факториальную сс
def fact(x):
    x = int(x, a)
    s = ''
    f = 2
    while x > 0:
        d = x % f
        s = str(d) + s
        x = x // f
        f += 1
    return s


x = str(input('Введите число ')) # число
a = int(input('Введите сс, из которой хотите перевести (2 - 16) ')) # перевод из сс a
b = int(input('Введите сс, в которую хотите перевести (2 - 16 или 0 для факториальной сс) ')) # перевод в сс b

if b == 0: print(fact(x))
else: print(translater(x, a, b))