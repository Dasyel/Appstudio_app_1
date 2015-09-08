#Wordfinder
By Dasyel Willems
dasyelwillems@hotmail.com

###Summary
This app finds all possible words that can be combined of any subset of a set of letters and/or wildcards, making this app ideal for scrabble-like purposes. Complexity is high so give the app a few seconds to think ;)


###Technical Info
Wildcards can substitute any letter making this a problem with high complexity. Therefore I have created a DictTree which orders the letters of each word from a dictionary file in alphabetical order and places the characters in nodes. Each node contains words that can be created by this branch of letters. This makes my DictTree a very efficient way of finding these words.
