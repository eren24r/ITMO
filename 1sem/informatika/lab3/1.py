#print(373751%6)
#print(373751%4)
#print(373751%7)
#print(373751%5)

#  5 3 0
#  [<{(


########1
my_sm = "[<{("
#ss = ""
#with open('input.txt') as f:
#    lines = f.readlines()
#    for s in lines:
#    	ss = ss + "\n" + s

ss = input()
ans = 0

for i in range(0, len(ss)):
	if ss[i:i+4] == my_sm:
		ans = ans + 1
print(ans)
#print(ss.count(my_sm))
