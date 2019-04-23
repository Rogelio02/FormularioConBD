package com.example.formulatioalumnos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Edt_Matricula, Edt_Nombre, Edt_ApP, Edt_ApM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Edt_Matricula = (EditText) findViewById(R.id.Matricula);
        Edt_Nombre = (EditText) findViewById(R.id.Nombre);
        Edt_ApP = (EditText) findViewById(R.id.APaterno);
        Edt_ApM = (EditText) findViewById(R.id.AMaterno);
    }

    public void RegistrarAlumno(View view)
    {
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        String matricula = Edt_Matricula.getText().toString();
        String nombre = Edt_Nombre.getText().toString();
        String materno = Edt_ApM.getText().toString();
        String paterno = Edt_ApP.getText().toString();

        if (!matricula.isEmpty() && !nombre.isEmpty() && !materno.isEmpty() && !paterno.isEmpty())
        {
            ContentValues registro = new ContentValues();
            registro.put("Matricula", matricula);
            registro.put("Nombre", nombre);
            registro.put("APaterno", paterno);
            registro.put("AMaterno", materno);

            cn.insert("Alumnos", null, registro);

            cn.close();

            Edt_Matricula.setText("");
            Edt_Nombre.setText("");
            Edt_ApP.setText("");
            Edt_ApM.setText("");

            Toast.makeText(this, "Registro guardado con exito!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "El llenado de todos los campos es obligatorio", Toast.LENGTH_SHORT).show();
        }
    }

    public void BuscarAlumno(View view)
    {
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        String matricula = Edt_Matricula.getText().toString();

        if (!matricula.isEmpty())
        {
            Cursor fila = cn.rawQuery
                    ("Select Nombre, APaterno, AMaterno from Alumnos WHERE Matricula =" + matricula, null);

            if (fila.moveToFirst())
            {
                Edt_Nombre.setText(fila.getString(0));
                Edt_ApP.setText(fila.getString(1));
                Edt_ApM.setText(fila.getString(2));
                cn.close();
            }
            else
            {
                Toast.makeText(this, "Alumno no registrado, lo siento", Toast.LENGTH_SHORT).show();
                cn.close();
            }
        }
        else
        {
            Toast.makeText(this, "Ingresa una matricula", Toast.LENGTH_SHORT).show();
        }
    }

    public void BuscarRegistros(View view)
    {
        String registros = "";
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        if (cn != null) {

            Cursor cursor = cn.rawQuery("SELECT * FROM Alumnos", null);

            if (cursor.moveToFirst())
            {
                do
                {
                    registros += cursor.getInt(cursor.getColumnIndex("Matricula"));
                    registros += " ";
                    registros += cursor.getString(cursor.getColumnIndex("Nombre"));
                    registros += " ";
                    registros += cursor.getString(cursor.getColumnIndex("APaterno"));
                    registros += " ";
                    registros += cursor.getString(cursor.getColumnIndex("AMaterno"));
                    registros += "\n";
                }
                while (cursor.moveToNext());
            }
            if (registros.equals(""))
            {
                Toast.makeText(MainActivity.this, "No hay registros", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(MainActivity.this, registros, Toast.LENGTH_LONG).show();
                cn.close();
            }
            cn.close();
        }
    }

    public void ActualizarAlumno(View view)
    {
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        String matricula = Edt_Matricula.getText().toString();
        String nombre = Edt_Nombre.getText().toString();
        String materno = Edt_ApM.getText().toString();
        String paterno = Edt_ApP.getText().toString();

        if (!matricula.isEmpty() && !nombre.isEmpty() && !materno.isEmpty() && !paterno.isEmpty())
        {
            ContentValues registro = new ContentValues();
            registro.put("Matricula", matricula);
            registro.put("Nombre", nombre);
            registro.put("APaterno", paterno);
            registro.put("AMaterno", materno);

            int Actualizados = cn.update
                    ("Alumnos", registro, "Matricula =" + matricula, null);
            cn.close();

            if(Actualizados == 1)
            {
                Toast.makeText(this, "Los datos se han actualizado con exito", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Alumno no registrado, lo siento", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void EliminarAlumno(View view)
    {
        Conexion CN = new Conexion(this, "Escuela", null, 1);
        SQLiteDatabase cn = CN.getWritableDatabase();

        String matricula = Edt_Matricula.getText().toString();

        if (!matricula.isEmpty())
        {
            int Eliminados = cn.delete
                    ("Alumnos", "Matricula =" + matricula, null);
            cn.close();

            Edt_Nombre.setText("");
            Edt_Matricula.setText("");
            Edt_ApM.setText("");
            Edt_ApP.setText("");

            if(Eliminados == 1)
            {
                Toast.makeText(this, "Alumno dado de baja", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "El alumno no está registrado", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Introduce una matricula", Toast.LENGTH_SHORT).show();
        }
    }
}