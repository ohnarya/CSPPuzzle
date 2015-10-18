This shows the result below in an order of Job puzzle (baseline), Job puzzle (mrv), House puzzle (baseline), and House puzzle (mrv)

•	Abstract

1)	Job Puzzle is implemented in an object-oriented way. Person is extended to Woman and Man. Job is extended to 8 jobs: chef, guard, nurse, clerk, police officer, teacher, actor, and boxer. Constraints are defined in each job class and are called in ‘consistent_check()’, ‘consistent_job()’ function through ‘isEligible()’ to see if a job can be assigned to a person. ‘JobNode’ data structure is introduced to hole a group of variables: a person and 2 jobs. The algorithm loops through each jobs and check every person is eligible for the job.

2)	House Puzzle is hard to implement in an object-oriented fashion because it has too many variables and it is hard to manage cross-over constraints. Thus, it is implemented in a procedural way. Variables are each objects: races, houses, animals, foods, and drinks. And Domains are slot from 1 to 5. It has ‘constistent_house()’ function to check if a variable is located a certain slot. ‘board’ is introduced to manage overall location of variables. 

•	Choice a variable out of unassigned ones


1)	Baseline (without MRV heuristics)

Baseline doesn’t care about if domains of the chosen variable are already assigned to some variables else. Basically the algorithm run though all values in its domain. The values are statically defined and never change while running.

2)	MRV heuristics

The algorithm using MRV heuristics choose a variable out of unassigned one choose the variable which has minimum remaining possible values. To do so, every time a variable is assigned, update domains of the rest unassigned variables so that domains hold possible values at the time. Person.getCanDO() for Job Puzzle and HousePuzzle.setDomainMRV() for House Puzzle propagate unavailable values to domain of each variable.




•	Result

As below, total iterations show a huge difference. By choosing a variable with minimum remaining variables, iterations searching for the right answer are dramatically reduced because it prunes out a tree where it doesn’t need to traverse. If it chooses another variable with more values, then it goes around seeking routes not leading to the goal.


JobPuzzle: BASELINE iteration=82, pass=4, backtrack=4
-----------------------------------------------------
[Roberta   ] is [Guard   ] and [Teacher ].
[Thelma    ] is [Chef    ] and [PoliceOfficer].
[Steve     ] is [Nurse   ] and [Clerk   ].
[Pete      ] is [Actor   ] and [Boxer   ].
-----------------------------------------------------


JobPuzzle: MRV iteration=4, pass=4, backtrack=4
-----------------------------------------------------
[Roberta   ] is [Guard   ] and [Teacher ].
[Thelma    ] is [Chef    ] and [PoliceOfficer].
[Pete      ] is [Clerk   ] and [Actor   ].
[Steve     ] is [Nurse   ] and [Boxer   ].
-----------------------------------------------------


House Puzzle: BASELINE iternation=761670, pass=152344, backtrack=152332 
-----------------------------------------------------
[yellow     ][norwegian  ][fox        ][kitkat     ][water      ]
[hershey    ][tea        ][ukranian   ][horse      ][blue       ]
[snail      ][red        ][english    ][milk       ][smarty     ]
[milkyway   ][japanese   ][zebra      ][green      ][coffee     ]
[snicker    ][ivory      ][orangejuice][spaniard   ][dog        ]
-----------------------------------------------------


House Puzzle: MRV iternation=4898, pass=1542, backtrack=1530 
-----------------------------------------------------
[yellow     ][norwegian  ][fox        ][kitkat     ][water      ]
[hershey    ][tea        ][ukranian   ][horse      ][blue       ]
[snail      ][red        ][english    ][milk       ][smarty     ]
[milkyway   ][japanese   ][zebra      ][green      ][coffee     ]
[snicker    ][ivory      ][orangejuice][spaniard   ][dog        ]
-----------------------------------------------------
