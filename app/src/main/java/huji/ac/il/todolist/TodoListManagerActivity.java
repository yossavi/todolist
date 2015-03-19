package huji.ac.il.todolist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TodoListManagerActivity extends ActionBarActivity implements DbListeners{

    private Db db;
    private ListView listview;
    private Context context;
    private ArrayList<Row> list;
    private ToDoListArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        listview = (ListView) findViewById(R.id.lstTodoItems);
        context = TodoListManagerActivity.this;
        db = new Db(context, this);
        db.getWritableDatabase();
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
        Intent intent = new Intent(this, AddNewTodoItemActivity.class);
        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && data.hasExtra("edtNewItem") && data.hasExtra("datePicker")) {
            Row newRow = new Row(data.getStringExtra("edtNewItem"), Long.parseLong(data.getStringExtra("datePicker")), 0);
            db.addRow(newRow);
            onDbOpened();
        }
    }

    @Override
    public void onDbOpened() {
        list = db.getAllRows();
        adapter = new ToDoListArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                final Dialog dialog = new Dialog(context);
                LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View bodyView = inflater.inflate(R.layout.delete_dialog, null);

                dialog.setTitle(list.get((int) id).toDo);

                Button deleteButton = (Button) bodyView.findViewById(R.id.menuItemDelete);
                deleteButton.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                        dialog.dismiss();
                        db.removeRow(list.get((int) id).id);
                        list.remove((int)id);
                        listview.setAdapter(adapter);
                    }
                });

                Button menuItemCall = (Button) bodyView.findViewById(R.id.menuItemCall);
                String mydata = list.get((int) id).toDo;
                Pattern pattern = Pattern.compile("call (.*)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(mydata);

                if (matcher.find()) {
                    final String res = matcher.group(1);
                    menuItemCall.setText("Call to: " + res);
                    menuItemCall.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent dial = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse("tel:"+res));
                            startActivity(dial);
                        }
                    });
                } else {
                    menuItemCall.setVisibility(View.GONE);
                }


                dialog.setContentView(bodyView);
                dialog.show();
                return true;
            }
        });
        listview.setAdapter(adapter);
    }
}
