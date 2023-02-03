import json
import yaml
import time

start_time = time.perf_counter()

with open('jsonfile.json', encoding="utf-8") as js:
	data = json.load(js)

with open('yamlefile_programm_lib.yaml', 'w', encoding="utf-8") as yml:
	yaml.dump(data, yml, allow_unicode=True, sort_keys=False)   

print(time.perf_counter() - start_time)
#0.002262099995277822
