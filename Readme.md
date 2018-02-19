Node List Exercise

Summary: This java program takes a node list string and returns the expanded node list along with tests to prove that it works. 

A node list string can contain bracketed sections which indicate a numeric range or multiple options. Ranges are denoted by “-”, options are denoted by “,”.  For example, “node[1-3]” indicates nodes named “node1”, “node2”, and “node3”, while “node[1,5]” indicates nodes named “node1” and “node5”. Ranges and options can be combined in the same bracket, so, for example, “c[1,3-5]” indicates nodes named “c1”, “c3”, “c4”, and “c5”.  There can be any number of bracketed sections in a node list string, but they won’t be nested. That means you can get a string like "node[1-2]node[4-5]" which should expand to "node1node4", "node1node5", "node2node4", "node2node5".
