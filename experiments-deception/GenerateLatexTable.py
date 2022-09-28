#!/usr/bin/python
import sys
import os
import math

def main() :
	domain_name_path_name = sys.argv[1]
	
	goal_recognition_approaches = ['MIRRORING_LANDMARKS']

	deception_approaches = [
	'vanilla',

	'Goal-to-Real-Goal-Approach',
	'Shared-Landmark-Approach',
	'Combined-Landmarks-Approach',
	'Most-Common-Landmarks',

	'Centroid-Approach',
	'Closest-Centroid-Approach',
	'Farthest-Centroid-Approach',
	'All-but-Real-Centroid-Approach',
	'R-Centroid-Approach',
	'R-Closest-Centroid-Approach',
	'R-Farthest-Centroid-Approach',
	'R-All-but-Real-Centroid-Approach',
	
	'Minimum-Covering-State-Approach',
	'Closest-Minimum-Covering-State-Approach',
	'Farthest-Minimum-Covering-State-Approach',	
	'All-but-Real-Minimum-Coverinst-State-Approach',
	'R-Minimum-Covering-State-Approach',
	'R-Closest-Minimum-Covering-State-Approach',
	'R-Farthest-Minimum-Covering-State-Approach',	
	'R-All-but-Real-Minimum-Coverinst-State-Approach',	
	]
	
	deception_approaches_results = dict()

	for file in os.listdir(domain_name_path_name):
		if os.path.isfile(os.path.join(domain_name_path_name, file)):
			if '.DS_Store' in file:
				continue

			ranked_first = 0
			convergence = 0
			time = 0
			with open(os.path.join(domain_name_path_name, file)) as f:
				lines = f.readlines()
				for l in lines:
					if 'Average Ranked First Percent' in l:
						ranked_first = l.split(' (%): ')[1]
					if 'Average Convergence Percent' in l:
						convergence = l.split(' (%): ')[1]
					if 'Average Run-Time' in l:
						time = l.split(' (sec): ')[1]

			d_approach_name = file.replace('MIRRORING_LANDMARKS-', '')
			d_approach_name = d_approach_name.replace('.txt', '')
			deception_approaches_results[d_approach_name] = (ranked_first, convergence, time)
	
	for approach in deception_approaches:
		ranked_first = str(round(float(deception_approaches_results[approach][0].replace('\n', '')), 2))
		convergence = str(round(float(deception_approaches_results[approach][1].replace('\n', '')), 2))
		time = str(round(float(deception_approaches_results[approach][2].replace('\n', '')), 2))
		print '&\t' + approach + '\t&\t' + time + '\t&\t' + ranked_first + '\t&\t' + convergence + '\t\\\\\n\\multicolumn{1}{l|}{}'
		print ''


if __name__ == '__main__' :
	main()