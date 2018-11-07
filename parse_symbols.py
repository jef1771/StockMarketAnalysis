import json

def main():
	j_dict = "{"
	with open('symbols.txt', 'r') as fp:
		line = fp.readline()
   		while line:
			l_line = line.split("|")
			j_dict += "\n \"" + l_line[1] + "\" : \"" + l_line[2] + "\""
			line = fp.readline()
			if(line):
				j_dict += ","
	j_dict += "\n}"
	with open('symbols.json', 'w') as outfile:
    		outfile.write(j_dict)
main()
	  
