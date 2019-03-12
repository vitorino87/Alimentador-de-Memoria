package controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import model.Banco;

public class ControladorDoDB {

	protected SQLiteDatabase db;
	Banco banco = null;
	Cursor cursor;
	Boolean previous=false;
	int position=-1;	
	
	public int armazenarPositionDoCursor(){
		position = cursor.getPosition();
		return position;
	}

	public void goToPositionCursor() {
		cursor.moveToPosition(position);		
	}
	
	public void goToPositionCursor(int posicao){
		cursor.moveToPosition(posicao);
	}
	
	public void atualizarCursorAposInserirIdeia(String tabela){
		if(position!=-1){
			retornarTodosResultados(tabela);
			goToPositionCursor();
			position=-1;
		}
	}
	
	public boolean checarPosition(){
		if(position!=-1){
			return true;
		}{
			return false;
		}
	}

	public ControladorDoDB(Context context){
		banco = new Banco(context, "Ideias");
	}

	// Recupera o Cursor máximo
	public int buscarIdMax() {
		cursor = banco.buscarIdMax(db);
		int a = cursor.getInt(0);
		return a;
	}

	//Retorna o primeiro cursor
	public Cursor irParaPrimeiroCursor() {
		Cursor d = null;
		if (!cursor.moveToFirst()) {

		}
		return d;
	}

	// fechando banco
	public void fecharConexao() {
		try{
			//c.close();
			banco.close();
		}catch(Exception ex){
			
		}
		
	}

	// conectar no banco
	public void abrirConexao() {		
		db = banco.getWritableDatabase();
	}
	
	public void retornarTodosResultados(String tabela){
		abrirConexao();
		cursor = banco.retornarTodosResultados(db, tabela);
		//c.moveToFirst();
	}
	
	public String initialResult(){
		String b="";
		cursor.moveToFirst();
		b = cursor.getString(1);
		return b;
		
	}	
	
	public String nextResult(){
		String b="";			
		if(!cursor.isLast())
			cursor.moveToNext();
		else
			cursor.moveToPosition(0);
		b = cursor.getString(1);			
		return b;
	}
	
	public String currentResult(int position){		
		String b="";	
		cursor.moveToPosition(position);			
		b = cursor.getString(1);								
		return b;
	}
	
	public String previousResult(){
		String b="";
		if(!cursor.isFirst()){
			cursor.moveToPrevious();		
			b = cursor.getString(1);
		}else{
			cursor.moveToLast();
			b = cursor.getString(1);
			/*previous = true;*/			
		}
		return b;
	}
	
	public Long inserirRow(Object ideia, Object morto, String tabela){
		if(!banco.buscar(db, (String)ideia,tabela)){
			ContentValues cv = new ContentValues();
			cv.put("morto", (String)morto);
			cv.put("ideia", (String)ideia);
			Long a = banco.inserir(db, cv, tabela);
			return a;
		}else{
			return -1L;
		}
		
	}
	
	public boolean deletarRow(String ideia, String tabela){
		if(banco.deletarIdeia(ideia, db, tabela)>0){
			return true;
		}{
			return false;
		}
	}

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	
	public int addOrDelDeadFile(String tabela, String ideia, String dead){
		abrirConexao();
		ContentValues cv = new ContentValues();
		cv.put("morto", dead);
		int a = banco.atualizarIdeia(db, cv, tabela, ideia);
		if(a!=-2){
			cursor = null;
		}
		return a;
	}		
	
	public boolean resultDeadFiles(String tabela){
		abrirConexao();		
		cursor = banco.retornarTodosDeadFiles(db, tabela);
		int a = cursor.getCount();
		if(cursor.moveToFirst()){
			return true;
		}else{
			return false;
		}
	}
}
