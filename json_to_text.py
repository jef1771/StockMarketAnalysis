import json
import os

def main():

	with open("A.json", 'r') as f:
		data = json.load(f)
		values = data["Time Series (Daily)"]
		filestring = ""
		for key in sorted(values.iterkeys()):
			filestring += key + ", " + values[key]["1. open"] + ", " + values[key]["2. high"] + ", " + values[key]["3. low"] + ", " + values[key]["4. close"] + ", " + values[key]["5. volume"]
			filestring += "\n"
		with open('A.txt', 'w') as outfile:
			outfile.write(filestring)

def directory():
	dire = "./data"
	output = "./cleaned_data/"
	counter = 1
	files = "filenames.txt"
	listfiles = ""
	for filename in os.listdir(dire):
		print(counter)
		if(filename.endswith(".json")):
			listfiles += (filename[:-4] + "txt" + "\n")

			with open(os.path.join(dire, filename), 'r') as f:
				data = json.load(f)
				if("Time Series (Daily)" in data):
					listfiles += (filename[:-4] + "txt" + "\n")
					
					values = data["Time Series (Daily)"]
					filestring = ""
					for key in sorted(values.iterkeys()):
						filestring += key + ", " + values[key]["1. open"] + ", " + values[key]["2. high"] + ", " + values[key]["3. low"] + ", " + values[key]["4. close"] + ", " + values[key]["5. volume"]
						filestring += "\n"
					with open(output + filename[:-4] + "txt", 'w') as outfile:
						outfile.write(filestring)
				f.close()
				
		counter += 1

def createAllFileNames():
	files = ""
	for filename in os.listdir("./cleaned_data/"):
		files += (filename + "\n")
	with open('filenames.txt', 'w') as outfile:
		outfile.write(files)




#directory()
createAllFileNames()
#main()

