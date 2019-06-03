#!/usr/bin/python
import sys
import os
import math

def main() :
	domain = 'sokoban'
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
						TPR = (float(line[1]) * 100)
						FPR = (float(line[5]) * 100)
						complete_print += str(round(FPR, 1)) + '\t' + str(round(TPR, 3)) + '\n'
						
		print complete_print

if __name__ == '__main__' :
	main()