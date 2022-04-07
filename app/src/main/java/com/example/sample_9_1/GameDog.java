package com.example.sample_9_1;

/**
 * @author: liuming
 * @date: 2022/4/7
 */
public class GameDog {
    MainActivity mActivity = null;
    private State currentState;

    public GameDog(MainActivity activity){
        mActivity = activity;
        currentState = new CommonState(activity);
    }

    public boolean updateState(MainActivity.MasterAction ma){
        State beforeState = currentState;
        currentState = currentState.toNextState(ma);
        return !(beforeState == currentState);
    }
}
