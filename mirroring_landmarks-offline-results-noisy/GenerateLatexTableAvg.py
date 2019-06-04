#!/usr/bin/python
import sys
import os
import math
import decimal

def main() :
	observabilities = ['25', '50', '75', '100']
	approaches = ['mirroring_landmarks']

	avgTime = 0
	avgAcc = 0
	avgSpread = 0
	avgGoals = 0

	resultObs = dict()

	domains = ['campus-noisy', 'easy-ipc-grid-noisy', 'intrusion-detection-noisy', 'kitchen-noisy']
	for domain_name in domains:
		path = domain_name + '-' + approaches[0] + '.txt'
		with open(path) as f:
			printed = False
			for l in f:
				line = l.strip().split('\t')
				if(line[0] == '10' and printed == False):
					avgGoals += float(line[7])
					printed = True

	for obs in observabilities:
		results = [[], [], []]
		for domain_name in domains:	
			path = domain_name + '-' + approaches[0] + '.txt'
			with open(path) as f:
				for l in f:
					line = l.strip().split('\t')
					if(line[0] == obs):
						time = float(line[10])
						accuracy = (float(line[1]) * 100)
						spreadG = float(line[9])
						
						resultTime = results[0]
						resultAcc = results[1]
						resultSpread = results[2]
						
						resultTime.append(time)
						resultAcc.append(accuracy)
						resultSpread.append(spreadG)
						
						results[0] = resultTime
						results[1] = resultAcc
						results[2] = resultSpread
			resultObs[obs] = results

	print '\nAvg. Goals: ' + str(float(avgGoals/len(domains)))
	for obs in resultObs.keys():
		results = resultObs[obs]
		
		resultTime = results[0]
		totalTime = 0
		for t in resultTime:
			totalTime += t

		resultAcc = results[1]
		totalAcc = 0
		for acc in resultAcc:
			totalAcc += acc
		
		resultSpread = results[2]
		totalSpread = 0
		for s in resultSpread:
			totalSpread += s

		# print '\nObs: ' + obs + '%'
		# print '\t - Time   : ' + str(float(totalTime/len(domains)))
		# print '\t - Acc %  : ' + str(float(totalAcc/len(domains)))
		# print '\t - Spread : ' + str(float(totalSpread/len(domains)))
		print '% ' + obs + '%'
		print '&\t ' + str(float(round(totalTime/len(domains), 3))) + '\t&\t ' + str(float(round(totalAcc/len(domains), 2))) + '\%\t&\t ' + str(float(round(totalSpread/len(domains), 2)))

if __name__ == '__main__' :
	main()