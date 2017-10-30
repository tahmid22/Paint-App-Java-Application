package ca.utoronto.utm.floatingpoint;

public class q1 {
	public static void main(String[] args) {
		q1 p = new q1();
		System.out.println(p.solve711());
	}
	
	/**
	 * Overall this algorithm will not solve the problem.
	 * What does work is the for loop structure as it does check all possibilities but
	 * it is not efficient since the way it searches is inefficient.
	 * To make it more efficient we need to restrict the conditions on the nested for loops,
	 * so that it can reduce a good amount of unnecessary search. 
	 * 
	 * The main issue that this will not work is because it is using decimals/floating points.
	 * 
	 * Computers cannot hold the true value of many fractions because the decimal value is infinite and would overload the 
	 * computers memory. So floating point numbers are used where the number is reduced into a IEEE 745 32 bit binary string
	 * such that it will resemble the true number through a different value that is very close to it. The issue is that when 
	 * the infinite decimal value of the number is reduced to 32 bits, the value will have to be rounded/compressed in general, 
	 * therefore losing its true value.
	 * 
	 * The only time we have a true value is when the decimal can be represented as 1 divided by a factor of 2. This is because
	 * decimals are represented by base 2, 1/2^k, where k is the position to the right of the decimal. If a decimal can
	 * be represented by a sum of 1/2^k or a single 1/2^k then it will have a true representation of the numbers value.
	 * This means it's true value can be represented by base 2, up to a 32 bits since we are using a float in this case, 
	 * but we could then use a double to get 64 bits.
	 * 
	 * In this case each iteration when we add 0.1 to one of the existing integers it is adding a rounded value
	 * to another rounded value, therefore each iteration as we add 2 rounded number together, the answer is becoming
	 * less and less precise.
	 * 
	 * @return a string containing the desired a,b,c,d float combinations
	 */
	public String solve711() {
		float a, b, c, d;
		for (a = 0.00f; a < 7.11f; a = a + .01f) {
			for (b = 0.00f; b < 7.11f; b = b + .01f) {
				for (c = 0.00f; c < 7.11f; c = c + .01f) {
					for (d = 0.00f; d < 7.11f; d = d + .01f) {
						if (a * b * c * d == 7.11f && a + b + c + d == 7.11f) {
							return (a + " " + b + " " + c + " " + d);
						}
					}
				}
			}
		}
		return "";
	}
}
