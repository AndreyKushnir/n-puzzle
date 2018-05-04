package kushnir.andrey.n_puzzle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The Class MainActivity.
 *
 * @author Andrey Kushnir
 * @version 1.0 $
 * @see BoardView
 * @see Board
 */
public class MainActivity extends AppCompatActivity {

    private ViewGroup mainView;

    private Board board;

    private BoardView boardView;

    private TextView movesTextView;

    private TextView minMovesTextView;

    private int boardSize = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = findViewById(R.id.mainLayout);
        movesTextView = findViewById(R.id.moves);
        minMovesTextView = findViewById(R.id.min_moves);
        movesTextView.setTextColor(Color.WHITE);
        movesTextView.setTextSize(20);
        minMovesTextView.setTextColor(Color.WHITE);
        minMovesTextView.setTextSize(20);
        this.newGame();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void newGame() {
        this.board = new Board(this.boardSize);
        this.board.addBoardChangeListener(boardChangeListener);
        this.board.rearrange();
        this.mainView.removeView(boardView);
        this.boardView = new BoardView(this, board);
        this.mainView.addView(boardView);
        this.movesTextView.setText("Количество ходов: 0");
        this.minMovesTextView.setText("Мин: " + Integer.toString(this.board.getMinMoves()));
    }

    public void changeSize(int newSize) {
        if (newSize != this.boardSize) {
            this.boardSize = newSize;
            this.newGame();
            boardView.invalidate();
        }
    }

    void updateMinMoves() {
        this.minMovesTextView.setText("Мин: " + Integer.toString(this.board.getMinMoves()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_training:


            case R.id.action_settings:
                FragmentManager fm = getSupportFragmentManager();
                SettingsDialogFragment settings = new SettingsDialogFragment(
                        this.boardSize);
                settings.show(fm, "fragment_settings");
                break;
            case R.id.action_new_game:
                new AlertDialog.Builder(this)
                        .setTitle("Новая игра")
                        .setMessage("Вы уверены, что хотите начать новую игру?")
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        board.rearrange();
                                        movesTextView.setText("Количество ходов: 0");
                                        boardView.invalidate();
                                        updateMinMoves();
                                    }
                                })
                        .setNegativeButton(android.R.string.no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // do nothing
                                    }
                                }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
            case R.id.action_help:
                new AlertDialog.Builder(this)
                        .setTitle("Инструкция для чайников:")
                        .setMessage(
                                "Цель игры — перемещая костяшки по коробке, " +
                                        "добиться упорядочивания их по номерам, " +
                                        "желательно сделав как можно меньше перемещений.")
                        .setPositiveButton("Ясно, поехали!",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Слушатель, реагирующий на изменения игровой доски.
     */
    private Board.BoardChangeListener boardChangeListener;

    {
        boardChangeListener = new Board.BoardChangeListener() {
            public void tileSlid(Place from, Place to, int numOfMoves) {
                movesTextView.setText("Количество ходов: "
                        + Integer.toString(numOfMoves));
            }

            public void solved(int numOfMoves) {
                movesTextView.setText("Собрано за " + Integer.toString(numOfMoves)
                        + " ходов!");
                Toast.makeText(getApplicationContext(), "Вы выиграли!",
                        Toast.LENGTH_LONG).show();
            }
        };
    }

    /**
     * The Class SettingsDialogFragment. Shows the settings alert dialog in
     * order to change the size of the board.
     */

}
