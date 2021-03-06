package com.example.sample_9_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    enum DogState{
        HAPPY_STATE,
        COMMON_STATE,
        AWAY_STATE;
    }

    enum MasterAction{
        BATH_ACTION,
        ENGAGE_ACTION,
        FIND_ACTION,
        ALONE_ACTION;
    }



    public ImageView myImageView;
    public TextView myTextView;
    private Button bath;
    private Button engage;
    private Button find;
    private GameDog mGameDog;

    public Handler mHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    mGameDog.updateState(MasterAction.ALONE_ACTION);
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImageView = findViewById(R.id.myImageView);
        myTextView = findViewById(R.id.myTextView);
        bath = findViewById(R.id.bath);
        engage = findViewById(R.id.enage);
        find = findViewById(R.id.find);

        bath.setOnClickListener(this);
        engage.setOnClickListener(this);
        find.setOnClickListener(this);

        mGameDog = new GameDog(this);

        ActionThread actionThread = new ActionThread(this);
        actionThread.start();

    }

    @Override
    public void onClick(View v) {
        if(v == bath){
            mGameDog.updateState(MasterAction.BATH_ACTION);
        }
        if(v == engage){
            mGameDog.updateState(MasterAction.ENGAGE_ACTION);
        }
        if(v == find){
            mGameDog.updateState(MasterAction.FIND_ACTION);
        }
    }
}
abstract class State{
    public MainActivity activity;
    public State(MainActivity activity){
        this.activity = activity;
    }
    public abstract State toNextState(MainActivity.MasterAction ma);
}
class HappyState extends State{ //????????????????????????
    public HappyState(MainActivity activity) {//?????????
        super(activity);
    }
    public State toNextState(MainActivity.MasterAction ma){//???????????????
        State result=this;
        switch(ma){
            case ALONE_ACTION://??????????????????????????????
                result= new CommonState(activity);
                activity.myImageView.setImageResource(R.drawable.common);//??????
                activity.myTextView.setText("???????????????");
                break;
        }
        return result;
    }
}
class CommonState extends State{ //????????????????????????
    public CommonState(MainActivity activity) {//?????????
        super(activity);
    }
    public State toNextState(MainActivity.MasterAction ma){//???????????????
        State result=this;
        switch(ma){
            case ALONE_ACTION://??????????????????????????????
                result= new AwayState(activity);
                activity.myImageView.setImageResource(R.drawable.away);//??????
                activity.myTextView.setText("???????????????");
                break;
            case BATH_ACTION:  //??????
            case ENGAGE_ACTION://??????
                result= new HappyState(activity);
                activity.myImageView.setImageResource(R.drawable.happy);//??????
                activity.myTextView.setText("???????????????");
                break;
        }
        return result;
    }
}
class AwayState extends State {//????????????????????????
    public AwayState(MainActivity activity) {//?????????
        super(activity);
    }
    public State toNextState(MainActivity.MasterAction ma){//???????????????
        State result=this;
        switch(ma){
            case FIND_ACTION://??????
                result=new CommonState(activity);
                activity.myImageView.setImageResource(R.drawable.common);//??????
                activity.myTextView.setText("???????????????");
                break;
        }
        return result;
    }
}