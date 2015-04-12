package huji.ac.il.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddNewTodoItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);

        final EditText edtNewItem = (EditText) findViewById(R.id.edtNewItem);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        final Button btnOK = (Button) findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();

                if (datePicker == null) {
                    result.putExtra("dueDate",  String.valueOf(0));
                } else {
                    Calendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    result.putExtra("dueDate",  String.valueOf(calendar.getTimeInMillis()));
                }

                if (edtNewItem == null) {
                    result.putExtra("title", "");
                } else {
                    result.putExtra("title", edtNewItem.getText().toString());
                }

                setResult(RESULT_OK, result);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
