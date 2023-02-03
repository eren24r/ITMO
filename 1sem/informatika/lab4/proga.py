import time
inputt = open('jsonfile.json', 'r', encoding="utf-8")
output = open('yamlefile_programm.yaml', 'w', encoding="utf-8")

start_time = time.perf_counter()
x = inputt.read()
ansyaml = "---\n"
l = x.split('\n')
print(l)
for i in range(1, len(l) - 1):
	ll = 0
	r = 0
	ans = 0
	count = (l[i]).count('\t')
	for j in range(len(l[i])):
			if ll == 0 and l[i][j] == '"':
				ll = j + 1
			if ll != 0 and l[i][j] == '"':
				r = j 
	ans = l[i][ll:r]
	if len(ans) != 0:
		if ',' in l[i - 1]:
			ans = l[i][ll:r]
			ans = ans.replace('"', '')
			ansyaml = ansyaml + str((count - 1) * '  ') + ans + "\n"
		elif '},' in l[i - 2]:
			if '{' in l[i - 1]:
				ans = l[i][ll:r]
				ans = ans.replace('"', '')
				ansyaml = ansyaml + str((count - 2) * '  ') + '- ' + ans + "\n"  
		elif '[' in l[i - 2]:
			ans = l[i][ll:r]
			ans = ans.replace('"', '')
			ansyaml = ansyaml + str((count - 2) * '  ') + '- ' + ans + "\n" 
		elif '{' in l[i - 1]: 
			ans = l[i][ll:r] + ':'
			ansyaml = ansyaml + str((count - 1) * '  ') + ans + "\n"


print(ansyaml)

output.write(ansyaml)
output.close()

print(time.perf_counter() - start_time)
#0.00499739998485893
