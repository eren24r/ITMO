
########2
#5
l = ['а', 'у', 'о', 'ы', 'э', 'я', 'ю', 'ё', 'и', 'е']
ins = input()

ls = ins.split()

for i in range(len(ls) - 1):
	tm = ls[i]
	s = 0
	k = 0
	sa = 1
	tm = tm + "()"
	for j in range(1, len(tm) - 2):
		if tm[j-1] not in l and tm[j] in l and tm[j + 1] in l and tm[j + 2] not in l:
			s = 1

	tm1 = ls[i + 1]
	if s == 1:
		for j in range(len(tm1)):
			if tm1[j] in l:
				k = k + 1
		if k <= 3:
			ss = ""
			for ii in tm:
				if ii.isalpha():
					ss = ss + ii
			print(ss)
