package kz.talipovsn.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager; // Менеджер датчиков
    private Sensor proximity; // Датчик приближения
    private float distance; // Значение датчика приближения
    private TextView textView;
    private MediaPlayer mp;


    // Задаем метод, вызываемый при создании активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView); // Получаем ссылку на элемент TextView

        // Создаем менеджер датчиков
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.laugh);
    }

    // Обработчик событий датчиков
    private SensorEventListener sensorlistener = new SensorEventListener() {
        // Метод, вызываемый при изменении значений датчика
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                distance = event.values[0];
                if (distance == 0) {
                    mp.start();
                    Toast.makeText(getApplicationContext(), proximity.getVendor() + ", " + proximity.getMaximumRange() + getString(R.string.centimeters), Toast.LENGTH_SHORT).show();
                }
            }
            textView.setText(String.format("%s %s %s",
                    getString(R.string.proximity), distance, getString(R.string.centimeters)));
        }

        // Метод, вызываемый при изменении точности датчика
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


//    private SensorEventListener proximityListener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
//                if (event.values[0] > 5) {
//                    mp.start();
//                    Toast.makeText(getApplicationContext(), proximity.getVendor() + ", " + proximity.getMaximumRange() + " cm", Toast.LENGTH_SHORT).show();
//                }
//            }
////            try {
////                distance = event.values[0];
////                if (distance == 0) {
////                    mp.start();
////                    Toast.makeText(getApplicationContext(), proximity.getVendor() + ", " + proximity.getMaximumRange() + " cm", Toast.LENGTH_SHORT).show();
////                }
////                System.out.println(distance);
////                textView.setText(String.format("%s %s %s",
////                        getString(R.string.proximity), distance, getString(R.string.centimeters)));
////            } catch (NullPointerException e) {
////                distance = 0;
////                System.out.println(distance);
////            }
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    };

    // Задаем метод, вызываемый при возвращении программы в активный режим
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorlistener, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Задаем метод, вызываемый при приостановке работы программы
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorlistener);
    }

}
