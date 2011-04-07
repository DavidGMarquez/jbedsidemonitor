package completeTestsTimeSeries;

import java.util.Timer;
import java.util.TimerTask;

public class Reminder {

    Timer timer;
    int limitOfItIterations;
    int iterations;

    public Reminder(int delayFirstTime, int periodOfTime,int limitOfIterations) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), delayFirstTime,periodOfTime);
        this.iterations = 0;
        this.limitOfItIterations = limitOfIterations;
    }

    class RemindTask extends TimerTask {

        public void run() {
            System.out.println("Time's up!"+iterations);
            iterations++;
            if (iterations > limitOfItIterations) {
                System.out.println("Time's end!");
                timer.cancel(); //Terminate the timer thread
            }
        }
    }
}
