package org.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

// we implement the onClickListener - so this means there
//will be an onClick method defined for ALL the views later
//in the onClick method
public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View table = findViewById(R.id.table);
        table.setOnClickListener(this);

        String fieldID;
        View field;
        for (int i = 1; i <= 9; i++) {
            fieldID = "felt" + i;
            field = findViewById(getResources().getIdentifier(fieldID, "id", getPackageName()));
            field.setOnClickListener(this);
        }
    }

    int[] fields = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    boolean turn = true;
    int numberOfTurns = 0;

    @Override
    public void onClick(View view) {
        ImageView image = (ImageView) view;
        String idName = getResources().getResourceEntryName(view.getId());
        int fieldNumber = Character.getNumericValue(idName.charAt(idName.length() - 1));

        makeMove(image, fieldNumber);
        checkGameState();

        if (numberOfTurns < 8) {
            //AI moves
            image = AIMove();
            idName = getResources().getResourceEntryName(image.getId());
            System.out.println("idName: " + idName);
            fieldNumber = Character.getNumericValue(idName.charAt(idName.length() - 1));
            System.out.println("fieldNumber: " + fieldNumber);
            makeMove(image, fieldNumber);
            checkGameState();
        }

    }

    public ImageView AIMove() {
        ArrayList<Integer> availablePosition = new ArrayList<>();

        //get available position only
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == 0) {
                availablePosition.add(i);
            }
        }

        //pick a random available position
        Random rand = new Random();
        int n = rand.nextInt(availablePosition.size());
        System.out.println("AIMOVE: " + n);
        String fieldID = "felt" + (availablePosition.get(n) + 1);
        ImageView view = (ImageView) findViewById(getResources().getIdentifier(fieldID, "id", getPackageName()));

        System.out.println(availablePosition);
        return view;
    }

    public void newGame(View view) {
        for (int i = 0; i < fields.length; i++) {
            fields[i] = 0;
        }
        turn = true;
        numberOfTurns = 0;

        String fieldID;
        ImageView field;
        for (int i = 1; i <= 9; i++) {
            fieldID = "felt" + i;
            field = (ImageView) findViewById(getResources().getIdentifier(fieldID, "id", getPackageName()));
            field.setImageResource(R.drawable.blank);
        }
    }

    private void makeMove(ImageView image, int fieldNumber) {
        if (fields[fieldNumber - 1] == 0) {
            if (turn) {
                image.setImageResource(R.drawable.kryds);
                fields[fieldNumber - 1] = 1;
                numberOfTurns++;
            } else {
                image.setImageResource(R.drawable.bolle);
                fields[fieldNumber - 1] = 2;
                numberOfTurns++;
            }
            turn = !turn;
        } else {
            Toast.makeText(this, "Click on an empty field", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkGameState() {
        if (checkWinningCombination()) {
            System.out.println("There is a winner");
            Toast.makeText(this, "Winner", Toast.LENGTH_SHORT).show();
        } else if (numberOfTurns == 9) {
            System.out.println("Game over");
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkWinningCombination() {
        return (fields[0] != 0 && fields[0] == fields[1] && fields[0] == fields[2]) ||
                (fields[0] != 0 && fields[0] == fields[3] && fields[0] == fields[6]) ||
                (fields[0] != 0 && fields[0] == fields[4] && fields[0] == fields[8]) ||
                (fields[1] != 0 && fields[1] == fields[4] && fields[1] == fields[7]) ||
                (fields[2] != 0 && fields[2] == fields[5] && fields[2] == fields[8]) ||
                (fields[2] != 0 && fields[2] == fields[4] && fields[2] == fields[6]) ||
                (fields[3] != 0 && fields[3] == fields[4] && fields[3] == fields[5]) ||
                (fields[6] != 0 && fields[6] == fields[7] && fields[6] == fields[8]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}