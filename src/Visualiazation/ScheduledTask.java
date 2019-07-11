package Visualiazation;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

class ScheduledTask extends TimerTask {
    private Timer time;
    private int stepsNum;
    private int stepsCount;
    private AdapterMainWindow thisAdapter;
    ScheduledTask(Timer time, int stepsNum, int stepsCount, AdapterMainWindow thisAdapter){
        this.time = time;
        this.stepsNum = stepsNum;
        this.stepsCount = stepsCount;
        this.thisAdapter = thisAdapter;
    }

    @Override
    public void run() {
        if(stepsCount >= stepsNum ) {
            time.cancel();
            time.purge();
            thisAdapter.setUpCloseMainThreadAlgorithm();
            thisAdapter.updateTimer();
            thisAdapter.setIsAlgorithmBlock(false);
            JOptionPane.showMessageDialog(null, "algorithm end work!");
            return;
        }
        stepsCount++;
        thisAdapter.nextStep();
    }

    public void setStepCount(int stepsCount){
        this.stepsCount = stepsCount;
    }
}