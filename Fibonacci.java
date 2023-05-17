package Fib;

public class Fibonacci {
	
	public static int findFib(int x) {
		if (x <= 1)
			return x;
		return findFib(x-1) + findFib(x-2);
	}
	
	public static void main(String[] args) {
		int holder = findFib(10);
		System.out.println("The 10th fibonacci number is : " + holder);
	}
	
}
