package com.example.learnjava.ExerciseView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

public class ViewArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_ANSWER = 1;
    private static final int VIEW_CHOICE = 2;
    private static final int VIEW_FILLBLANKS = 3;
    private static final int VIEW_DRAG = 4;
    private static final int VIEW_ORDER = 5;
    private static final int VIEw_CODE = 6;

    private ModelTask currentTask;

    public ViewArrayAdapter(ModelTask currentTask){
        this.currentTask = currentTask;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_ANSWER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_view_answer,parent,false);
            return  new ViewHolderAnswer(view);
        }
        else if(viewType == VIEW_CHOICE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_view_choice,parent,false);
            return  new ViewHolderChoice(view);
        }
        else{
            throw new RuntimeException("The type has to be 1- 6");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("In onBINDVIWHOLDER", "init layouts");
        switch (holder.getItemViewType()){
            case VIEW_ANSWER:
                initLayoutAnswer((ViewHolderAnswer)holder, position);
                break;
            case VIEW_CHOICE:
                initLayoutChoice((ViewHolderChoice)holder, position);
                break;
                //TODO do the other layouts
            default:
                break;
        }
    }

    private void initLayoutAnswer(ViewHolderAnswer holder, int pos){
        holder.exerciseText.setText(currentTask.getTaskText());
        holder.editText.setHint("Write a answer");
    }

    private void initLayoutChoice(ViewHolderChoice holder, int pos){
        holder.exerciseText.setText(currentTask.getTaskText());


    }

    @Override
    public int getItemViewType(int position){
       int currentViewType = currentTask.getExerciseViewType();
       if(currentViewType == 1)
           return VIEW_ANSWER;
       else if (currentViewType == 2)
           return VIEW_CHOICE;
       else if(currentViewType == 3)
           return VIEW_FILLBLANKS;
       else if(currentViewType ==4)
           return VIEW_DRAG;
       else if(currentViewType == 5)
           return VIEW_ORDER;
       else if(currentViewType == 6)
           return VIEw_CODE;
       else
           return -1;
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    //Static inner classes to initialze the view
    static class ViewHolderAnswer extends RecyclerView.ViewHolder{

        public TextView exerciseText;
        public EditText editText;
        public ViewHolderAnswer(View view){
            super(view);
            exerciseText = view.findViewById(R.id.exerciseTextAnswer);
            editText = view.findViewById(R.id.editTextAnswer);

        }
    }

    static class ViewHolderChoice extends RecyclerView.ViewHolder{

        public TextView exerciseText;
        public RadioGroup radioGroup;
        public RadioButton buttonAnswer;
        public ViewHolderChoice(View view){
            super(view);
            exerciseText = view.findViewById(R.id.exerciseTextChoice);
            radioGroup = view.findViewById(R.id.answerGroup);
        }

    }

    public void buttonClicked(){

    }

    //TODO make method to dynamiclly add rows and edittexts
}
