import json

def main():

	with open("A.json", 'r') as f:
		data = json.load(f)
		values = data["Time Series (Daily)"]
		filestring = ""
		for key in values:
			filestring += key + ", " + values[key]["1. open"] + ", " + values[key]["2. high"] + ", " + values[key]["3. low"] + ", " + values[key]["4. close"] + values[key]["5. volume"]
			filestring += "\n"
		with open('A.txt', 'w') as outfile:
			outfile.write(filestring)

main()

