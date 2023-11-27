package twin.developers.projectmqtt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private Mqtt mqttManager;
    private List<Animal> ListAnimal = new ArrayList<Animal>();
    ArrayAdapter<Animal> arrayAdapterAnimal;
    EditText txtNombreA;
    EditText txtNumeroA;
    Button btnEnviar;
    Button btnEliminar;
    ListView listaAnimales;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNombreA = findViewById(R.id.txtNombreA);
        txtNumeroA = findViewById(R.id.txtNumeroA);
        mqttManager = new Mqtt(getApplicationContext());
        mqttManager.connectToMqttBroker();
        btnEnviar = findViewById(R.id.btnEnviar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listaAnimales = findViewById(R.id.listaAnimales);
        mqttManager = new Mqtt(getApplicationContext());
        mqttManager.connectToMqttBroker();
        inicializarFireBase();
        listarDato();


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase();
                mqtt();
            }
            private void firebase(){

                Animal Animal = new Animal();
                Animal.setNombreAnimal(txtNombreA.getText().toString());
                Animal.setNumeroAnimal(txtNumeroA.getText().toString());
                databaseReference.child("Animal").child(Animal.getNombreAnimal()).setValue(Animal);
                databaseReference.child("Animal").child(Animal.getNumeroAnimal()).setValue(Animal);

            }
            private void mqtt(){
                mqttManager.publishMessage(txtNombreA.getText().toString());
                mqttManager.publishMessage(txtNumeroA.getText().toString());


            }
        });

    }


        private void listarDato () {
            databaseReference.child("Contador de animales").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ListAnimal.clear();
                    for (DataSnapshot objs : snapshot.getChildren()) {
                        Animal li = objs.getValue(Animal.class);
                        ListAnimal.add(li);
                        arrayAdapterAnimal = new ArrayAdapter<Animal>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, ListAnimal);
                        listaAnimales.setAdapter(arrayAdapterAnimal);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        private void inicializarFireBase () {
            FirebaseApp.initializeApp(this);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
    }

}


