public class CheckPrime {

    public int maxInt = 1000000;
    public int totalPrimeNumbers = 0;

    private boolean checkPrime(int x) {
        if ((x & 1) == 0) {
            return false;
        }

        for (int i = 3; i <= (int) Math.pow(x, 0.5); i += 2) {
            if (x % i == 0) {
                return false;
            }
        }
        totalPrimeNumbers++;
        return true;
    }

    public static void main(String[] args) {

        CheckPrime checkPrime = new CheckPrime();

        final long startTime = System.currentTimeMillis();

        for (int i = 3; i <= checkPrime.maxInt; i++) {
            checkPrime.checkPrime(i);
        }

        final long elapsedTimeMillis = System.currentTimeMillis() - startTime;

        System.out.println("Total prime numbers: " + checkPrime.totalPrimeNumbers + 1 + " in " + elapsedTimeMillis + "ms");

    }
}
