package JAssign1;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Assignment1v2Ex1 {

    private class SleepingBeauty extends Thread {
        public void run()  {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                System.err.println(e.getStackTrace());
            }
        }
    }

    private Stack myThreads = new Stack<Thread>();
    private static int numOfThreads = 13000;

    public void ex1() {
        for(int i = 0; i < numOfThreads; i++) {
            SleepingBeauty thread = new SleepingBeauty();
            thread.start();
            myThreads.push(thread);
        }

        while(!(myThreads.empty())) {
            try {
                Thread t = (Thread) (myThreads.pop());
                t.join();
            } catch (Exception e) {
                System.err.println(e.getStackTrace());
            }
        }
    }

    public static void main(String[] args) {
        Assignment1v2Ex1 ass1 = new Assignment1v2Ex1();
        long startTime = System.nanoTime();
        ass1.ex1();
        long stopTime = System.nanoTime();
        long duration = (stopTime - startTime) / (long)(Math.pow(10, 6));
        System.out.println(numOfThreads + " threads each slept for 1 second in " + duration + " milliseconds!");
    }
}
