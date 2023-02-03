x = input()

if '@' not in x:
	print("Fail!")
else:
	s = 0
	ss = ""
	for i in range(len(x)):
		if s == 1:
			ss = ss + x[i]
		if x[i] == '@':
			s = 1
	if '.' in ss:
		print(ss)
	else:
		print("Fail!")