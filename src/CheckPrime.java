
public class CheckPrime {

    private boolean checkPrime(int x) {
        if ((x & 1) == 0)  {
            return false;
        }

        for (int i = 3; i * i <= x; i += 2) {
            if (x % i == 0) {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        int maxInt = Integer.MAX_VALUE;
        int totalPrimeNumbers = 0;

    }
}
