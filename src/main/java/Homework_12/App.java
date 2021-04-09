package Homework_12;

public class App {
    private static final int SIZE = 10000000;
    private static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        App app = new App();
        app.firstMethod();
        app.secondMethod();

    }

    public void firstMethod() {
        float[] arr = createArrayFromOnes();
        long a = System.currentTimeMillis();
        calculate(arr);
        long b = System.currentTimeMillis();
        System.out.println("Время первого метода: " + (b - a));
    }

    public void secondMethod() {
        float[] arr = createArrayFromOnes(); //создаем массив из единичек
        long a = System.currentTimeMillis();
        float[] firstHalfArr = new float[HALF];
        float[] secondHalfArr = new float[HALF];
        System.arraycopy(arr, 0, firstHalfArr, 0, HALF); //делим массив на две части
        System.arraycopy(arr, HALF, secondHalfArr, 0, HALF);
        Thread t1 = new Thread(new MyRunnable(firstHalfArr)); //каждую половину рассчитывает свой поток
        Thread t2 = new Thread(new MyRunnable(secondHalfArr));
        t1.start();
        t2.start();
        System.arraycopy(firstHalfArr, 0, arr, 0, HALF); //склеиваем обратно обе части массива
        System.arraycopy(secondHalfArr, 0, arr, HALF, HALF);
        long b = System.currentTimeMillis();
        System.out.println("Время второго метода: " + (b - a)); //высчитываем время
    }

    public static int getSIZE() {
        return SIZE;
    }

    public static int getHALF() {
        return HALF;
    }

    public float[] createArrayFromOnes() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
        return arr;
    }

    public static float[] calculate(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return arr;
    }
}

class MyRunnable implements Runnable {
    private float[] array;

    public MyRunnable(float[] array) {
        this.array = array;
    }

    public void run() {
        array = App.calculate(array);
    }
}
