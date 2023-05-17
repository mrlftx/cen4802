package Fib;
/**
 * 
 * @author Luiz Felipe Tafner
 *
 */
public class Fibonacci {
	/**
	 * This method recursively finds the nth number in the Fibonacci sequence, where n = x.
	 * @param x The position in the Fibonacci seqeucne we want the number from.
	 * @return The actual proper Fibonacci number that is in the x position in the sequence.
	 */
	public static int findFib(int x) {
		if (x <= 1)
			return x;
		return findFib(x-1) + findFib(x-2);
	}
	/**
	 * This main method runs the findFib method once (with a default inout of 10) and prints out the number that is returned.
	 * @param args The command line arguments (Unused).
	 */
	public static void main(String[] args) {
		int holder = findFib(10);
		System.out.println("The 10th fibonacci number is : " + holder);
	}
	
}
