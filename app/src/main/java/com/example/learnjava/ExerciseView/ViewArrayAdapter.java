package com.example.learnjava.ExerciseView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnjava.R;
import com.example.learnjava.models.ModelExercise;
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
        switch (holder.getItemViewType()){
            case VIEW_ANSWER:
                initLayoutAnswer((ViewHolderAnswer)holder);
                break;
            case VIEW_CHOICE:
                initLayoutChoice((ViewHolderChoice)holder);
                break;
                //TODO do the other layouts
            default:
                break;
        }
    }

    public void initLayoutAnswer(ViewHolderAnswer holder){
        holder.exerciseText.setText(currentTask.getTaskText());
    }

    public void initLayoutChoice(ViewHolderChoice holder){
        holder.exerciseText.setText(currentTask.getTaskText());
        holder.editText.setHint("Write a answer");

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
        return 0;
    }

    static class ViewHolderAnswer extends RecyclerView.ViewHolder{

        public TextView exerciseText;
        public ViewHolderAnswer(View view){
            super(view);
            exerciseText = view.findViewById(R.id.exerciseTextAnswer);

        }
    }

    static class ViewHolderChoice extends RecyclerView.ViewHolder{

        public TextView exerciseText;
        public EditText editText;
        public ViewHolderChoice(View view){
            super(view);
            exerciseText = view.findViewById(R.id.exerciseTextChoice);
            editText = view.findViewById(R.id.editTextAnswer);
        }
    }

    //TODO make method to dynamiclly add rows and edittexts
}
