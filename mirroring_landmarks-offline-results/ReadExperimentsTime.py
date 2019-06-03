#!/usr/bin/python
import sys
import os
import math

def main() :
	domain = 'campus'
	observabilities = ['10', '30', '50', '70', '100']
	
	domains = ['campus', 'easy_ipc_grid', 'ferry', 'intrusion_detection', 'kitchen', 'logistics', 'miconic', 'rovers', 'satellite', 'zeno_travel']
	
	for domain in domains:
		path = domain + '-goalrecognition_results-mirroring_landmarks.txt'

		totalProblems = 0
		avgGoals = 0

		complete_print = ''
		for obs in observabilities:
			printed = False
			with open(path) as f:
				for l in f:
					line = l.strip().split('\t')
					if(line[0] == obs):
						avgObs = float(line[8])
						time = float(line[10])
						complete_print += str(avgObs) + ',' + str(time) + '\n'
						
		print complete_print

if __name__ == '__main__' :
	main()