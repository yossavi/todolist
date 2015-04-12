package huji.ac.il.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.TimeZone;

/**
 * Created by yossi on 3/15/2015.
 */
public class ToDoListArrayAdapter extends ArrayAdapter<Row> {

    public ToDoListArrayAdapter(Context context, int textViewResourceId,
                                List<Row> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                LayoutInflater.from(getContext());

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }

        TextView txtTodoTitle = (TextView)convertView.findViewById(R.id.txtTodoTitle);
        TextView txtTodoDueDate = (TextView)convertView.findViewById(R.id.txtTodoDueDate);
        txtTodoTitle.setText(getItem(position).toDo);
        if (getItem(position).date==0) {
            txtTodoDueDate.setText("No due date");
        } else {
            txtTodoDueDate.setText(Clock.getHumanTime(getItem(position).date));
        }


        if(Clock.getCurrentTimeMili() > getItem(position).date) {
            txtTodoTitle.setTextColor(Color.RED);
            txtTodoDueDate.setTextColor(Color.RED);
        } else {
            txtTodoTitle.setTextColor(Color.BLACK);
            txtTodoDueDate.setTextColor(Color.BLACK);
        }

        return convertView;
    }

}
