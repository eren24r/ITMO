import math

def func(x):
    return math.pow(x, 2) + x + math.sin(x)

def derivative(x):
    return ((x ** 3) / 3) + ((x ** 2) / 2) - math.cos(x)

delta = 1
epsilon = math.pow(10, -10)
x0 = 1
iterations = 0

while delta > epsilon:
    iterations += 1
    x = x0 - func(x0) / derivative(x0)
    delta = abs(x - x0)
    print('шаг', iterations, ':', f"x = {x:.2e}",f"f'(x) = {derivative(x):.2e}")
    x0 = x
    if iterations > 24:
        break
