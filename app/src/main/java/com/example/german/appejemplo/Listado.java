package com.example.german.appejemplo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import data.Alumno;
import data.AlumnosDbHelper;

//clase del listado de alummnos registrados

public class Listado extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Alumno> alumnos;
    private AlumnosDbHelper admin;
    private SQLiteDatabase base;
    private int posicion2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lv = findViewById(R.id.lvAlumnos);

        ArrayList<String> nombres = new ArrayList<>();


        admin = new  AlumnosDbHelper(this,"administracion",null,1);
        base = admin.getWritableDatabase();

        Cursor fila = base.rawQuery("select * from alumno",null);
        alumnos = new ArrayList<>();
        int num = 1;
        if(fila.moveToFirst()){
            do {
                Alumno alum = new Alumno();
                alum.setId(num);
                alum.setDni(Integer.parseInt(fila.getString(2)));
                alumnos.add(alum);
                nombres.add(fila.getString(0) +" "+fila.getString(1)+" \n DNI: "+fila.getString(2)+" \n Legajo:"+fila.getString(3));
                num++;
            } while(fila.moveToNext());
        }

        /*se hace conexion con la base de datos y los datos se meten a un arraylist,
         se meten a un adapter para poder visualizarlos en un list view */


        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nombres);
        lv.setAdapter(adaptador);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int posicion, long l) {                 //
                posicion2=posicion;
                AlertDialog.Builder dialogo = new AlertDialog.Builder(Listado.this);
                dialogo.setTitle("Eliminar");
                dialogo.setMessage("¿Desea eliminar el registro?");
                dialogo.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogo, int i) {
                        base.delete("alumno", "dni=" + alumnos.get(posicion2).getDni(), null);
                        Intent intent = new Intent(Listado.this, Listado.class);
                        startActivity(intent);
                        base.close();
                        finish();
                    }

                });
                dialogo.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialogo.show();
               return false;
            }
        });

    }

}
