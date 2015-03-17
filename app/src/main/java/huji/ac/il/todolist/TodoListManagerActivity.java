package huji.ac.il.todolist;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class TodoListManagerActivity extends ActionBarActivity {

    private ArrayList<String> list;
    private ListView listview;
    private Context context;
    private ToDoListArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        listview = (ListView) findViewById(R.id.lstTodoItems);
        list = new ArrayList<String>();
        context = TodoListManagerActivity.this;
        adapter = new ToDoListArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                final Dialog dialog = new Dialog(context);
                LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View bodyView = inflater.inflate(R.layout.delete_dialog, null);

                dialog.setTitle(list.get((int) id));

                Button deleteButton = (Button) bodyView.findViewById(R.id.menuItemDelete);
                deleteButton.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                        dialog.dismiss();
                        list.remove((int)id);
                        listview.setAdapter(adapter);
                    }
                });

                dialog.setContentView(bodyView);
                dialog.show();
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuItemAdd) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToList(MenuItem item) {
        final EditText edtNewItem = (EditText) findViewById(R.id.edtNewItem);
        list.add(edtNewItem.getText().toString());
        listview.setAdapter(adapter);
    }
}
