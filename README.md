# OnlineGoalRecognition-DiscreteDomains

## Usage

- Option: Single tar.bz2 file containing domain, problem (initial state), set of goals, observations, correct goal, threshold value. For instance: experiments/logistics/100/logistics_p01_hyp-1_full.tar.bz2.

> Parameters needed: < -online | -offline > < tar.bz2 file > < threshold_value >

```bash
java -jar goalRecognizerMirroringLandmarks1.1.jar -online experiments/logistics/100/logistics_p01_hyp-1_full.tar.bz2 0.0
```

## Dependencies

This goal recognizer uses the following libs (which are included in [lib](lib)):

- jgrapht-jdk1.6.jar (A free Java Graph Library);
- planning-landmarks3.0.jar (A Landmark Extraction Algorithm based on [Ordered Landmarks in Planning](https://www.aaai.org/Papers/JAIR/Vol22/JAIR-2208.pdf));
- planning-utils3.2.jar (PDDL Parser and Planning data structure from JavaFF, including the FF Planner developed in Java);

This project contains the sourcecode of two papers:

- [Vered et al., Towards Online Goal Recognition Combining Goal Mirroring and Landmarks, AAMAS 2018](http://www.meneguzzi.eu/felipe/pubs/aamas-online-landmark-2018.pdf);
- [Vered et al., Online Goal Recognition as Reasoning over Landmarks, AAAI/PAIR 2018](https://www.aaai.org/ocs/index.php/WS/AAAIW18/paper/viewPaper/17188).
