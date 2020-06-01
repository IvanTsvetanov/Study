#include <stdio.h>
#include <stdlib.h>

//Defining any new type used in the functions
typedef int* NEWTYPE;

//Declaration of every function used
NEWTYPE ARRAY(int N);
int STORE(NEWTYPE as, int N, int row, int col, int val);
int FETCH(NEWTYPE as, int N, int row, int col);

//Definition of every function used
/*
The following function:
- Allocates memory for a NxN array
- Creates a checkered array of size NxN
*/
NEWTYPE ARRAY(int N) {
	//Allocates memory for array with N^2 integers and creates the array for (board)
	int* checkersBoard = malloc(N * N * sizeof(int));

	//Checks if mallock() can allocate the requested memory
	if (checkersBoard == NULL) {
		printf("Malloc failed!\n");
		return -1;
	}

	return *checkersBoard;
}

/*
The following function:
- Stores an integer value (val) at position (row, col) of a checkered array size NxN
- Returns "-1" if there are errors
- Returns "1" if there aren't any errors
*/
int STORE(NEWTYPE as, int N, int row, int col, int val) {
	//Checks if the selected position is valid
	if (row > N || col > N || (row + col) % 2 != 0)
		return -1;

	//Stores the value into the array
	as[row * N + col] = val;

	return 1;
}

/*
The following function:
- Fetches and returns the value stored at position (row, col) in a checkered array of size NxN
- Returns -1 if there are errors
*/
int FETCH(NEWTYPE as, int N, int row, int col) {
	//Checks if the selected position is valid
	if (row > N || col > N || (row + col) % 2 != 0)
		return -1;

	//Returns the requested value
	int element = as[row * N + col];
	return element;
}