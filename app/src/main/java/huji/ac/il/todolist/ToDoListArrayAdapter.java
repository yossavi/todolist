package huji.ac.il.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yossi on 3/15/2015.
 */
public class ToDoListArrayAdapter extends ArrayAdapter<String> {

    public ToDoListArrayAdapter(Context context, int textViewResourceId,
                                List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.row, parent, false);
        TextView row = (TextView)view.findViewById(R.id.rowText);
        row.setText(getItem(position));
        if(position%2==0) {
            row.setTextColor(Color.RED);
        } else {
            row.setTextColor(Color.BLUE);
        }

        return view;
    }

}
