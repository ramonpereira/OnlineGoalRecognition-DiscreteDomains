#!/usr/bin/python
import sys
import os
import math

def main() :
	domains = [
	'campus', 'campus-noisy',
	'depots',
	'driverlog',
	'easy_ipc_grid', 'easy_ipc_grid-noisy',
	'ferry',
	'intrusion-detection', 'intrusion-detection-noisy',
	'kitchen', 'kitchen-noisy',
	'logistics',
	'miconic',
	'rovers',
	'satellite',
	'zeno_travel',
	'dwr',
	'sokoban',
	'blocks-world'
	]
	
	for domain in domains:
		print '-> Running Experiments (Mirroring with Landmarks) ' + domain
		cmdJar = 'java -jar -server -d64 -Xms2G -Xmx16G -DisableExplicitGC -XX:NewSize=2G -XX:+AggressiveOpts mirroring_landmarks-offiline1.0.jar ' + domain
		print cmdJar
		os.system(cmdJar)

if __name__ == '__main__' :
	main()