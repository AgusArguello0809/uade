package algorithms;

public class HanoiTower {
    public void transfer(String a, String c, String b, int n) {
        if(n == 1) {
            move(a, c);
        } else {
            transfer(a, b, c, n - 1);
            move(a,c);
            transfer(b, c, a, n - 1);
        }
    }

    private void move(String x, String y) {
        System.out.printf("Move %s to %s%n", x, y);
    }
}
