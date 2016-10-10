package net.estebanrodriguez.apps.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.estebanrodriguez.apps.popularmovies.data_access.RetrieveMovieDataTask;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        RetrieveMovieDataTask task = new RetrieveMovieDataTask();
        task.execute();
        try {
            TextView textView = (TextView) this.findViewById(R.id.rando);
            textView.setText(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
