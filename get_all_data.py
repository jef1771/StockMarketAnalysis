import json
import requests
import time

url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
url_second = "&outputsize=full&apikey=RWQMHL8X1830Q08S"

def main():
	symbols = 1
	with open('symbols.json', 'r') as f:
		syms = json.load(f)
		for key in syms:
			if(key != "" and key != "Symbol" and symbols > 70):
				failcount = 0;
				while(True):
					try:
						r = requests.get(url = (url + key + url_second), params = {})
						with open('./data/' + key + '.json', 'w') as outfile:
							outfile.write(json.dumps(r.json(), indent=4))
						print('finished: ' + key + " \"" + str(r.json)[0:100] + "\"")
						time.sleep(10)
						break
					except:
						print('Failed on ' + key + ', retrying in 15 seconds')
						if(failcount < 3):
							failcount += 1
							time.sleep(15)
						else:
							print('Failed on ' + key + ', 3 times. Moving on')
			symbols += 1


main()
