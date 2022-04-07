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
class HappyState extends State{ //表示高兴状态的类
    public HappyState(MainActivity activity) {//构造器
        super(activity);
    }
    public State toNextState(MainActivity.MasterAction ma){//去下一状态
        State result=this;
        switch(ma){
            case ALONE_ACTION://超过指定时间无人搭理
                result= new CommonState(activity);
                activity.myImageView.setImageResource(R.drawable.common);//换图
                activity.myTextView.setText("状态：普通");
                break;
        }
        return result;
    }
}
class CommonState extends State{ //表示普通状态的类
    public CommonState(MainActivity activity) {//构造器
        super(activity);
    }
    public State toNextState(MainActivity.MasterAction ma){//去下一状态
        State result=this;
        switch(ma){
            case ALONE_ACTION://超过指定时间无人搭理
                result= new AwayState(activity);
                activity.myImageView.setImageResource(R.drawable.away);//换图
                activity.myTextView.setText("状态：出走");
                break;
            case BATH_ACTION:  //洗澡
            case ENGAGE_ACTION://逗弄
                result= new HappyState(activity);
                activity.myImageView.setImageResource(R.drawable.happy);//换图
                activity.myTextView.setText("状态：高兴");
                break;
        }
        return result;
    }
}
class AwayState extends State {//表示出走状态的类
    public AwayState(MainActivity activity) {//构造器
        super(activity);
    }
    public State toNextState(MainActivity.MasterAction ma){//去下一状态
        State result=this;
        switch(ma){
            case FIND_ACTION://寻找
                result=new CommonState(activity);
                activity.myImageView.setImageResource(R.drawable.common);//换图
                activity.myTextView.setText("状态：普通");
                break;
        }
        return result;
    }
}