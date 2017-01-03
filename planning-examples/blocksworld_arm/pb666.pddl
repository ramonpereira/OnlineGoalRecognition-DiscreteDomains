(define (problem blocks_words)
	(:domain blocks)
(:objects 

A B C D E R - block
)
(:init
(HANDEMPTY)
(CLEAR R) (ONTABLE R) (CLEAR B) (ON B C) (ONTABLE C) (CLEAR D) (ONTABLE D) (CLEAR E) (ON E A) (ONTABLE A)
)
(:goal (and
(ONTABLE B) (ON E B) (ON D E) (CLEAR D)
))
)