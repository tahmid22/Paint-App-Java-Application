package ca.utoronto.utm.floatingpoint;



public class q2 {
	public static void main(String[] args) {
		q2 p = new q2();
		System.out.println(p.solve711());
	}
	/**
	 * The algorithm is supposed to return a string containing a,b,c,d float values such that
	 * their addition and product gives a same specific result.
	 * 
	 * @return a string containing the desired a,b,c,d float combinations
	 */
	public String solve711() {
		
		//we convert float representation of dollar currency into cents(pennies) by multiplying the dollar value by 100.
		//since 1$ = 100 cents -> $7.11 = 7.11 * 100 = 711
		
		//The restrictions that the 'for loops' run till are not 711 because the algorithm would start by checking the 
		//products and sums of more obscure values. The explanation is as follows:
		
		//What we do is have the values of a,b,c,d start off together
		//and increase as a group, compared to before where one value would go all the way to 711 and then another value 
		//would iterate by one and then the other would go all the way to 711 again.
		
		//The values of b,c,d are relative to a, so that they will be restricted from increasing past a. This prevents having to
		//iterate one variable till 711, where instead they will increase together.
		
		//This algorithm would also work for checking other values by simply replacing variable n with another
		//integer. The reason that this works for all values is because the restrictions in the loops
		//are all variables that are relevant to 'n' in some way. Because of this we know that the algorithm
		//will check all the possibilities efficiently.
		
		
		//multiply dollar value by 100
		//So we get the integer value n in cents
		int n_cents = 711;
	
		int a, b, c, d;
		for(a=1; a<n_cents; a++){
			for(b=1; b<a; b++){
				for(c=1; c<a; c++){
					for(d=1; d<a; d++){
						
						//100a$ + 100b$ + 100c$ + 100d$ = 100(a+b+c+d)$ = 100* 7.11$ = 711
						//100a$ * 100b$ * 100c$ * 100d$ = 100000000 * (a*b*c*d)$ = 100000000 * 7.11$ = 711000000
						if(a+b+c+d == n_cents && a*b*c*d == n_cents*1000000){ 
							//converting cents into dollar values. We are using float to represent dollar value since we need 
							//to have decimals. We could also use double alternatively. 
							float a_cents = a; float b_cents = b; float c_cents = c; float d_cents = d;
							float a$ = a_cents/100; float b$ = b_cents/100; float c$ = c_cents/100; float d$ = d_cents/100;
							
							return (a$ + " " + b$ + " " + c$ + " " + d$);
						}
					}
				}
			}
		}
		return "";
	}
}
