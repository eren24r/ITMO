import math

def func(x):
    return math.pow(x, 2) + x + math.sin(x)

def golden_section_method(a, b, eps):
    cgs = (3 - 5 ** 0.5) / 2  # константа золотого сечения
    x1 = a + cgs * (b - a)
    x2 = b - cgs * (b - a)
    f1 = func(x1)
    f2 = func(x2)
    count = 0
    while (abs(b - a) > eps) and (count < iterations):
        count += 1
        if f1 < f2:
            b = x2
            x2 = x1
            f2 = f1
            x1 = a + cgs * (b - a)
            f1 = func(x1)
        else:
            a = x1
            x1 = x2
            f1 = f2
            x2 = b - cgs * (b - a)
            f2 = func(x2)
        x = (a + b) / 2
        print('шаг', count, ':', 'x = ', x, ' ', 'f(x) =', func(x))

a = -1
b = 0
epsilon = math.pow(10, -10)
iterations = 25

golden_section_method(a, b, epsilon)
