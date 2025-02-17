import requests

def getFunc(url, data):
	response = requests.get(url, params=data)
	print(response.url)

	if response.status_code == 200:
	    data = response.text
	    return data
	else:
		print(f"Request failed with status code {response.status_code}")

def postFunc(url, data):
	response = requests.post(url, data=data)

	if response.status_code == 200:
	    data = response.json()
	    return data
	else:
		print(f"Request failed with status code {response.status_code}")