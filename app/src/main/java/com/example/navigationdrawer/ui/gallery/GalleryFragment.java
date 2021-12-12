package com.example.navigationdrawer.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.databinding.FragmentGalleryBinding;
import androidx.appcompat.app.AppCompatActivity;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    EditText input;
    TextView output, resultHex, resultDec, resultOct, resultBin;
    Spinner baseInput, baseOutput;
    Button button;

    String[] items = {"HEX", "DEC", "OCT", "BIN"};
    String radixInput, radixOutput;

    public static final String BASE = "0123456789ABCDEF";

    public static String result(int n, int b) {
        StringBuilder stringBuilder = new StringBuilder();
        int x;
        while (n > 0) {
            x = n % b;
            n /= b;
            stringBuilder.append(BASE.charAt(x));
        }
        return stringBuilder.reverse().toString();
    }

    public boolean checkIndex(String data, String radix) {
        //data = data.toUpperCase();
        switch (radix) {
            case "HEX":
                for (int i = 0; i < data.length(); i++) {
                    if (BASE.indexOf(data.charAt(i)) < 0) return false;
                }
                break;
            case "DEC":
                for (int i = 0; i < data.length(); i++) {
                    if (BASE.indexOf(data.charAt(i)) < 0 || BASE.indexOf(data.charAt(i)) > 9) return false;
                }
                break;
            case "OCT":
                for (int i = 0; i < data.length(); i++) {
                    if (BASE.indexOf(data.charAt(i)) < 0 || BASE.indexOf(data.charAt(i)) > 7) return false;
                }
                break;
            case "BIN":
                for (int i = 0; i < data.length(); i++) {
                    if (BASE.indexOf(data.charAt(i)) < 0 || BASE.indexOf(data.charAt(i)) > 1) return false;
                }
                break;
            default:
                // Do nothing
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        input = findViewById(R.id.editTextTextPersonName);
        output = findViewById(R.id.editTextTextPersonName2);
        resultHex = findViewById(R.id.resultHex);
        resultDec = findViewById(R.id.resultDec);
        resultOct = findViewById(R.id.resultOct);
        resultBin = findViewById(R.id.resultBin);
        button = findViewById(R.id.button);

        baseInput = findViewById(R.id.spinner_input);
        baseOutput = findViewById(R.id.spinner_output);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_items_layout, R.id.text_view_content, items);
        baseInput.setAdapter(adapter);
        baseOutput.setAdapter(adapter);

        baseInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                radixInput = items[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        baseOutput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                radixOutput = items[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempInput = input.getText().toString();
                int buffer;
                String tempString;

                tempInput = tempInput.toUpperCase();
                if (tempInput.isEmpty()) displayToast(getString(R.string.input_null_message));
                else {
                    if (checkIndex(tempInput, radixInput)) {
                        switch (radixInput) {
                            case "HEX":
                                resultHex.setText(tempInput);
                                buffer = Integer.parseInt(tempInput, 16);
                                tempString = "" + buffer;
                                resultDec.setText(tempString);
                                resultOct.setText(result(buffer, 8));
                                resultBin.setText(result(buffer, 2));
                                break;
                            case "DEC":
                                resultDec.setText(tempInput);
                                buffer = Integer.parseInt(tempInput);
                                resultHex.setText(result(buffer, 16));
                                resultOct.setText(result(buffer, 8));
                                resultBin.setText(result(buffer, 2));
                                break;
                            case "OCT":
                                resultOct.setText(tempInput);
                                buffer = Integer.parseInt(tempInput, 8);
                                resultHex.setText(result(buffer, 16));
                                tempString = "" + buffer;
                                resultDec.setText(tempString);
                                resultBin.setText(result(buffer, 2));
                                break;
                            case "BIN":
                                resultBin.setText(tempInput);
                                buffer = Integer.parseInt(tempInput, 2);
                                tempString = "" + buffer;
                                resultDec.setText(tempString);
                                resultOct.setText(result(buffer, 8));
                                resultHex.setText(result(buffer, 16));
                                break;
                            default:
                                // Do nothing
                        }

                        switch (radixOutput) {
                            case "HEX":
                                output.setText(resultHex.getText().toString());
                                break;
                            case "DEC":
                                output.setText(resultDec.getText().toString());
                                break;
                            case "OCT":
                                output.setText(resultOct.getText().toString());
                                break;
                            case "BIN":
                                output.setText(resultBin.getText().toString());
                                break;
                            default:
                                //Do nothing
                        }
                    } else {
                        displayToast(getString(R.string.input_wrong_base_message) + " " +radixInput);
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}