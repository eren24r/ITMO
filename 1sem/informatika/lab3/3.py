

########3
#1
k = input()
kk = k.split()
l = ['а', 'у', 'о', 'ы', 'э', 'я', 'ю', 'ё', 'и', 'е']
ans = []

for i in range(len(kk)):
	tm = kk[i]
	s = ""
	er = 0
	for ii in tm:
		if ii in l:
			s = ii 
			break
	for ii in tm:
		if ii in l and ii == s:
			er = 1
		elif ii in l and ii != s:
			er = 0
			break
	if er == 1:
		ss = ""
		for ii in tm:
			if ii.isalpha():
				ss = ss + ii
		ans.append(ss)
ans.sort(key = lambda s: len(s))
for i in ans:
	print(i)
