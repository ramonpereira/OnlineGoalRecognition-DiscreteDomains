#!/usr/bin/python
import sys
import os
import math

def main() :
	domain_name = sys.argv[1]
	observabilities = ['10', '30', '50', '70', '100']
	approaches = ['mirroring_landmarks']

	totalProblems = 0
	avgGoals = 0

	path = domain_name + '-goalrecognition_results-' + approaches[0] + '.txt'
	with open(path) as f:
		printed = False
		for l in f:
			line = l.strip().split('\t')
			if(line[0] == '10' and printed == False):
				avgGoals = float(line[7])
				printed = True
	
	print '\n\\multirow{5}{*}{\\rotatebox[origin=c]{90}{\\textsc{' + domain_name + '}} \\rotatebox[origin=c]{90}{(' + str((totalProblems * 4)) + ')}} & \\multirow{5}{*}{' + str(round(avgGoals, 1)) + '} '	

	complete_print = ''
	for obs in observabilities:
		printedObs = False
		for approach in approaches:
			path = domain_name + '-goalrecognition_results-' + approach + '.txt'
			with open(path) as f:
				for l in f:
					line = l.strip().split('\t')
					if(line[0] == obs):
						if not printedObs:
							avgObs = float(line[8])
							complete_print += '\n\t' + ('\\\\ & & ' if obs != '10' else ' & ') + obs + '\t & ' + str(avgObs) + '\n'
							printedObs = True
						
						time = float(line[10])
						accuracy = (float(line[1]) * 100)
						spreadG = float(line[9])
						
						complete_print += '\n\t\t% ' + approach + ' - ' + obs + '% '
						complete_print += '\n\t\t& ' + str(round(time, 3)) + ' & ' + str(round(accuracy, 1)) + '\% & ' + str(round(spreadG, 2))
						complete_print += ' \t \n'
					
	complete_print += ' \\\\ \hline'
	print complete_print

if __name__ == '__main__' :
	main()