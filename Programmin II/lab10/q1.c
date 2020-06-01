#include <stdio.h>
#include <math.h>

//Declaration of the function
int CACHE(double* prod, double* sum, double d1, double d2);

//Definiton of the function
/*
This function:
- Takes as arguments two doubles, d1 and d2 and the pointers prod, sum.
- Computes the nearest integer not greater than d1 and the nearest integer not greater than d2 
and stores the product of these integers in prod and their sum in sum.
- Returns 1 if d1 and d2 have the same sign 
and returns -1 if their sign is different. 

- NOTE:  0 has no sign, so, this function treats 0 as a number with a sign 
different from both a positive and a negative number.
*/
int CACHE(double* prod, double* sum, double d1, double d2) {

	//Computes the nearest integer that is not greater than the given number
	int roundedD1 = floor(d1);
	int roundedD2 = floor(d2);

	//Computers and stores the product of the two integers
	int product = roundedD1 * roundedD2;
	prod = &product;

	//Computers and stores the sum of the two integers
	int summation = roundedD1 + roundedD2;
	sum = &summation;

	//Returns "1" if the two initial numbers have the same sign
	if ((d1 > 0 && d2 > 0) || (d1< 0 && d2 < 0) || (d1 == 0 && d2 == 0))
		return 1;

	//Returns "-1" if the two initial numbers have different signs
	else return -1;
}