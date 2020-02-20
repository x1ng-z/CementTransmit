package hs.view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

public class CurrentTimeLabel extends JLabel {

    private String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private int ONE_SECOND = 1000;
    private int ONE_MIN = ONE_SECOND*60;
    private int ONE_HOUR = ONE_MIN*60;
    private int ONE_DAY = ONE_HOUR*24;

    public CurrentTimeLabel(){
        configTimeArea();
        setFont(new Font("宋体", Font.BOLD, 22));
        setForeground(Color.red);
    }

    private void configTimeArea() {
        java.util.Timer tmr = new java.util.Timer();
        tmr.scheduleAtFixedRate(new JLabelTimerTask(),new Date(), ONE_SECOND);
    }

    protected class JLabelTimerTask extends TimerTask {
        SimpleDateFormat real_time = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        @Override
        public void run() {
            String time_text = real_time.format(Calendar.getInstance().getTime());
            setText(time_text);
        }
    }

}
