package il.ac.tau.cs.sw1.hw6;

public class Polynomial {
	
	double[] coefficients;
	int degree;
	
	
	
	public static int check_degree(double[] coeffi) {
		for(int i=coeffi.length-1; i>=0; i--) {
			if(coeffi[i]!=0.0)
				return i;
		}
		return 0;
	}
	
	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial()
	{
		coefficients = new double[0];
		degree = check_degree(coefficients);
	} 
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		if(coefficients.length == 0) {
			this.coefficients = new double[0];
			degree = 0;
		}
		else {
			this.coefficients = coefficients;
			degree = check_degree(this.coefficients);
		}
	}
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		double[] new_coe_arr;
		int len_orig = this.coefficients.length;
		int len_add = polynomial.coefficients.length;
		if(len_orig == 0)
			return polynomial;
		if(len_add == 0)
			return this;
		if(len_orig == len_add) {
			for(int i=0; i<len_add; i++) 
				polynomial.coefficients[i] = this.coefficients[i] + polynomial.coefficients[i];
			return polynomial;
		}
		if(len_orig > len_add) {
			new_coe_arr = new double[len_orig];
			for(int i=0; i<len_add; i++) 
				new_coe_arr[i] = this.coefficients[i] + polynomial.coefficients[i];
			for(int i=len_add; i<len_orig; i++)
				new_coe_arr[i] = this.coefficients[i];
			polynomial.coefficients = new_coe_arr;
			polynomial.degree = new_coe_arr.length-1;
			return polynomial;
		
		}
		
		if(len_add > len_orig) {
			new_coe_arr = new double[len_add];
			for(int i=0; i<len_orig; i++) 
				new_coe_arr[i] = this.coefficients[i] + polynomial.coefficients[i];
			for(int i=len_orig; i<len_add; i++)
				new_coe_arr[i] = polynomial.coefficients[i];
			polynomial.coefficients = new_coe_arr;
			polynomial.degree = new_coe_arr.length-1;
			return polynomial;
		
		}
	
		return polynomial;
	}
		
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		
		Polynomial polynomial;
		int len = this.coefficients.length;
		if(len == 0) {
			polynomial = new Polynomial();
			return polynomial;
		}
		double[] arr = new double[this.coefficients.length];
		
		for(int i=0; i<arr.length; i++)
			arr[i] = this.coefficients[i];
		
		polynomial = new Polynomial(arr);
		
		for(int i=0; i<len; i++) 
			polynomial.coefficients[i] = polynomial.coefficients[i] *a;

		return polynomial;
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		return this.degree;
	}
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		return this.coefficients[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		
		if(n <= this.degree) 
			this.coefficients[n] = c;
		else {
			double[] arr = new double[n+1];
			for(int i =0; i<this.coefficients.length; i++) 
				arr[i] = this.coefficients[i];
			for(int i =this.coefficients.length; i<=n; i++) {
				if(i==n)
					arr[i]=c;
				else
					arr[i]=0.0;
			}
		this.coefficients = arr;
		this.degree = coefficients.length-1; 
		}
		
		
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		Polynomial polynomial;
		int len = this.coefficients.length;
		if(len == 0) {
			polynomial = new Polynomial();
			return polynomial;
		}
		else {
			double[] arr = new double[len-1];
			for(int i=0; i<len-1; i++) {
				arr[i] = this.coefficients[i+1] * (i+1);
			}
		polynomial = new Polynomial(arr);	
		}
		
		return polynomial;
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		double compute = 0.0;
		for(int i=0; i<this.coefficients.length; i++) {
			compute = compute + this.coefficients[i]*(Math.pow(x,i));
		}
		return compute;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		if(this.getFirstDerivation().computePolynomial(x) == 0 && this.getFirstDerivation().getFirstDerivation().computePolynomial(x) != 0)
			return true;
		return false;
	}
	
	
	
	

    
    

}
