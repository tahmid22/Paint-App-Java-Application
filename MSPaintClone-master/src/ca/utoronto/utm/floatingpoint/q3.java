
package ca.utoronto.utm.floatingpoint;


public class q3 {
	/**
	 * YOUR ANSWER GOES HERE!!!
	 * 
	 * a) -6.8 as an IEEE754 single is:
	 * 
	 * 1)Sign:
	 * 
	 * We start off with the negative:
	 * 
	 * We know that the first bit is a 1 since that implies negative.
	 * 
	 * 
	 * 2) Convert non-decimal value to binary:
	 * 
	 * Then we convert the number 6 into binary which can be represented as:
	 * 
	 * 2^2	2^1	 0
	 * | 	| 	 |
	 * 1	1 	 0
	 * |	|	 |
	 * 4	2	 0 => 4+2+0 = 6
	 * 
	 * Therefore 6 in binary: 110
	 * 
	 * 
	 * 3)Convert decimal value to binary
	 * 
	 * After that, we convert the 0.8 into binary by using the multiply by 2 method
	 * 
	 * Such that we get:
	 * 
	 * 0.8 * 2 = 1.6
	 * 0.6 * 2 = 1.2
	 * 0.2 * 2 = 0.4
	 * 0.4 * 2 = 0.8
	 * 
	 * It will repeat after this so we take the 1100
	 * 
	 * We can write 0.8 as:
	 * 
	 * .11001100...
	 * 
	 * 
	 * 4) Put it together & solve for exponent:
	 * 
	 * Now, when we put them together we get
	 * 
	 * 110.11001100...
	 * 
	 * Move decimal left 2 times
	 * 
	 * 1.1011001100... * 2^2
	 * 
	 * Solve for the exponent:
	 * 
	 * 127 + 2 = 129
	 * 
	 * Likewise we got binary representation of 6 above, 129 = 128+1 in binary is:
	 * 
	 * Exponent 129 in binary: 10000001
	 * 
	 * Put sign, exponent and mantissa all together in IEEE754 format, we get:
	 * 
	 * 1 10000001 10110011001100110011001..., where 1100 repeats for mantissa
	 * 
	 * 
	 * 5) Rounding 23 bit mantissa and finalize IEEE representation:
	 * 
	 * We notice, we have 1 after the 23th bit of mantissa. So if we find one more 1, we can round mantissa to a larger mantissa
	 * We notice, we have 1001 after 23th bit of mantissa from mantissa. So, we can round the mantissa to a larger value.
	 * 
	 * That means, we can write the mantissa 10110011001100110011001|1001... -> 10110011001100110011010
	 * 
	 * So finally, -6.8 in IEEE754 format:
	 * 
	 * -6.8 in IEEE754: 1 10000001 10110011001100110011010
	 * 
	 * 
	 * __________________________________________________________________________________________________________________________
	 * 
	 * 
	 * b) 23.1 as an IEEE754 single is:
	 * 
	 * 1)Sign:
	 * 
	 * We start off with positive value:
	 * 
	 * We know that the first bit is a 0 since that implies positive value.
	 * 
	 * 2) Convert non-decimal value to binary:
	 * 
	 * Then we convert the number 23 into binary which can be represented as:
	 * 
	 * 2^4	0	 2^2   2^1	 2^0
	 * | 	| 	 |	   |	 |
	 * 1	0 	 1	   1     1
	 * |	|	 |	   |     |
	 * 16	0	 4     2     1 => 16+4+2+1 = 23
	 * 
	 * Therefore, 23 in binary is: 10111
	 * 
	 * 
	 * 3)Convert decimal value to binary
	 * 
	 * After that, we convert the 0.1 into binary by using the multiply by 2 method
	 * 
	 * 0.1 * 2 = 0.2
	 * 0.2 * 2 = 0.4
	 * 0.4 * 2 = 0.8
	 * 0.8 * 2 = 1.6
	 * 0.6 * 2 = 1.2
	 * 0.2 * 2 = 0.4
	 * 0.4 * 2 = 0.8
	 * 0.8 * 2 = 1.6
	 * 
	 * It starts off with 0 then repeats 0011
	 * 
	 * We get: 0001100110011...
	 * 
	 * 
	 * 4) Put it together & solve for exponent:
	 * 
	 * Now, when we put them together we get
	 * 10111.0001100110011
	 * 
	 * 
	 * Move the decimal over 4 times
	 * 
	 * 1.011100011001100 * 2^4
	 * 
	 * 
	 * Solve for the exponent:
	 * 
	 * 127 + 4 = 131
	 * 
	 * Likewise we got binary representation of 23 above, 131 = 128+2+1 in binary is:
	 * 
	 * Exponent 131 in binary: 10000011
	 * 
	 * Put sign, exponent and mantissa all together in IEEE754 format, we get:
	 * 
	 * 0 10000011 01110001100110011001100..., where 0011 repeats for mantissa
	 * 
	 * 
	 * 5) Rounding 23 bit mantissa and finalize IEEE representation:
	 * 
	 * We notice, we have 1 after the 23th bit of mantissa. So if we find one more 1, we can round mantissa to a larger mantissa
	 * We notice, we have 11 after 23th bit of mantissa from mantissa. So, we can round the mantissa to a larger value.
	 * 
	 * That means, we can write the mantissa 01110001100110011001100|1100... -> 01110001100110011001101
	 * 
	 * So finally, 23.1 in IEEE754 format:
	 * 
	 * 23.1 in IEEE754: 0 10000011 01110001100110011001101
	 */
}

