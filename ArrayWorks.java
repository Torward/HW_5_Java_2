import java.util.Arrays;

public class ArrayWorks {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    static float[] arr = new float[SIZE];

    public static void first() {
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long finishT = System.currentTimeMillis();
        System.out.println("Время на цикл расчёта метода first- " + (finishT - a) + " ms");
    }

    public static void second() {
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (arr[i] * Math.sin(0.2f + i + HALF / 5) * Math.cos(0.2f + +HALF / 5) * Math.cos(0.4f + +HALF / 2));
            }
        });
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        long splice = System.currentTimeMillis();
        System.out.println("------------------------------------------------");
        System.out.println("Время на цикл расчёта метода second - " + (splice - a) + " ms");

    }

    public static void main(String[] args) {
        first();
        second();
    }
}

