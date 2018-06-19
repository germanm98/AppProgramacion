package com.example.german.appejemplo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import data.Alumno;
import data.AlumnosDbHelper;

public class MainActivity extends AppCompatActivity {
    private EditText t1, t2, t3, t4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.editTextNombre);
        t2 = findViewById(R.id.editTextApellido);
        t3 = findViewById(R.id.editTextDNI);
        t4 = findViewById(R.id.editTextLegajo);
    }

    public void listar(View view){
        Intent intent = new Intent(view.getContext(),Listado.class);
        startActivityForResult(intent, 0);
    }

    public void alta(View view){

        if(t1.getText().toString().trim().isEmpty() || t2.getText().toString().trim().isEmpty() || t3.getText().toString().trim().isEmpty() || t4.getText().toString().trim().isEmpty())
            Toast.makeText(this, "Debe completar todos los campos para cargar un alumno", Toast.LENGTH_SHORT).show();
         else{
            AlumnosDbHelper admin = new AlumnosDbHelper(this, "administracion", null, 1);
            SQLiteDatabase base = admin.getWritableDatabase();

            Alumno a1 = new Alumno(t1.getText().toString(), t2.getText().toString(), Integer.parseInt(t3.getText().toString()), Integer.parseInt(t4.getText().toString()));
            ContentValues contenido = new ContentValues();
            contenido.put("nombre", a1.getNombre());
            contenido.put("apellido", a1.getApellido());
            contenido.put("dni", a1.getDni());
            contenido.put("legajo", a1.getLegajo());

            if(base.insert("alumno", null, contenido) != -1) {
                Toast.makeText(this, "Datos cargados con exito", Toast.LENGTH_SHORT).show();
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
            } else {
                Toast.makeText(this, "Ha ocurrido un error, compruebe que haya ingresado su DNI o legajo correctamente", Toast.LENGTH_SHORT).show();
            }

            base.close();

        }
    }

    public void consulta(View view) {
        if(!(t3.getText().toString().trim().isEmpty())) {
            AlumnosDbHelper admin = new AlumnosDbHelper(this, "administracion", null, 1);
            SQLiteDatabase base = admin.getWritableDatabase();

            String dni = t3.getText().toString();

            Cursor fila = base.rawQuery("select nombre, apellido, legajo from alumno where dni=" + dni, null);

            if (fila.moveToFirst()) {

                t1.setText(fila.getString(0));
                t2.setText(fila.getString(1));
                t4.setText(fila.getString(2));

            } else

                Toast.makeText(this, "No existe ningún alumno con ese DNI", Toast.LENGTH_SHORT).show();

            base.close();
        } else
            Toast.makeText(this, "Ingrese un DNI", Toast.LENGTH_SHORT).show();
    }

    public void baja(View view){
        if(!(t3.getText().toString().trim().isEmpty())) {
            AlumnosDbHelper admin = new AlumnosDbHelper(this, "administracion", null, 1);
            SQLiteDatabase base = admin.getWritableDatabase();

            String dni = t3.getText().toString();
            int cantidad = base.delete("alumno", "dni="+dni, null);

            if (cantidad == 1) {
                Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                t1.setText("");
                t2.setText("");
                t3.setText("");
                t4.setText("");
                base.close();
            }else
                Toast.makeText(this, "No se ha encontrado un alumno con el DNI descrito", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Ingrese un DNI", Toast.LENGTH_SHORT).show();
    }

    public void modificacion(View view){
        AlumnosDbHelper admin = new AlumnosDbHelper(this, "administracion", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();

        String nombre = t1.getText().toString();
        String apellido = t2.getText().toString();
        String dni = t3.getText().toString();
        String legajo = t4.getText().toString();

        ContentValues contenido = new ContentValues();
        contenido.put("nombre",nombre);
        contenido.put("apellido",apellido);
        contenido.put("legajo",legajo);

        int cantidad;

        try {
            cantidad = base.update("alumno",contenido,"dni=" + dni,null);
        } catch (Exception e){
            cantidad = -1;
            e.printStackTrace();
        }


        base.close();

        if(cantidad == 1)
            Toast.makeText(this, "Se actualizo al alumno con éxito", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Ha ocurrido un error, compruebe si se ha ingresado correctamente el DNI o legajo", Toast.LENGTH_SHORT).show();
    }
}
