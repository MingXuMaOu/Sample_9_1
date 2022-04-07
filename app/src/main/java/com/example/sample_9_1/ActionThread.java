package com.example.sample_9_1;

/**
 * @author: liuming
 * @date: 2022/4/7
 */
public class ActionThread extends Thread{
    private int sleepSpan = 5000;
    boolean flag = true;
    MainActivity mActivity;

    public ActionThread(MainActivity activity){
        mActivity = activity;
    }
    public void run(){
        while (flag){
            try{
                Thread.sleep(sleepSpan);
            }catch (Exception e){
                e.printStackTrace();
            }
            mActivity.mHandler.sendEmptyMessage(1);
        }
    }
}
