import math

def func(x):
    return math.pow(x, 2) + x + math.sin(x)

def bisection_method(a, b):
    count = 0
    f_a = func(a)
    f_b = func(b)
    while (abs(b - a) > epsilon) and (count < iterations):
        x0 = (a + b) / 2
        f_xm = func(x0)
        if f_a * f_xm <= 0:
            b = x0
        else:
            a = x0
        count += 1
        x = (a + b) / 2
        print('n = ', count, 'a = ', a, 'b = ', b)

a = -1
b = 0
epsilon = 10 ** -10
iterations = 25

bisection_method(a, b)
