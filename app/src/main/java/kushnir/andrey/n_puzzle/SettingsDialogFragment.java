package kushnir.andrey.n_puzzle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Андрей on 12.04.2018.
 */

public class SettingsDialogFragment extends DialogFragment {

    /**
     * The size.
     */
    private int size;

    public SettingsDialogFragment() {}


    /**
     * Instantiates a new settings dialog fragment.
     *
     * @param size the size
     */
    public SettingsDialogFragment(int size) {
        this.size = size;
    }

    /**
     * Sets the size.
     *
     * @param size the new size
     */
    void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    int getSize() {
        return this.size;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle
     * )
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Выберите размер игровой доски")
                .setSingleChoiceItems(R.array.size_options, this.size - 2,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                setSize(which + 2);

                            }

                        })
                .setPositiveButton("Изменить",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                ((MainActivity) getActivity())
                                        .changeSize(getSize());
                            }
                        })
                .setNegativeButton("Выход",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }
}
