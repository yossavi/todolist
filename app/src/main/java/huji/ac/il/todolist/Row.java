package huji.ac.il.todolist;

/**
 * Created by yossi on 3/19/2015.
 */
public class Row {
    public String toDo;
    public long date;

    public Row(String toDo, long date) {
        this.date = date;
        this.toDo = toDo;
    }
}
