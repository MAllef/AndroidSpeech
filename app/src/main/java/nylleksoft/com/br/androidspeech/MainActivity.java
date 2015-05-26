package nylleksoft.com.br.androidspeech;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    @ViewById
    EditText texto;

    @ViewById
    Button btnSpeech;

    private TextToSpeech textToSpeech;

    @AfterViews
    void afterViews() {
        textToSpeech = new TextToSpeech(this, this);
    }

    @Click(R.id.btnSpeech)
    void speech() {
        String text = texto.getText().toString();
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = textToSpeech.setLanguage(new Locale("pt-BR"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeech.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
