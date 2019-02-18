package controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.Banco;

public class MainControl {

	protected SQLiteDatabase db;
	Banco banco = null;
	Cursor c;
	Boolean previous=false;
	int position=-1;	
	
	public void armazenarPositionDoCursor(){
		position = c.getPosition();
	}

	public void setPositionOfCursor() {
		c.moveToPosition(position);		
	}
	
	public void atualizarCursorAposInserirIdeia(String tabela){
		if(position!=-1){
			retornarTodosResultados(tabela);
			setPositionOfCursor();
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

	public MainControl(Context context){
		banco = new Banco(context, "Ideias");
	}

	// Recupera o Cursor máximo
	public int buscarIdMax() {
		c = banco.buscarIdMax(db);
		int a = c.getInt(0);
		return a;
	}

	//Retorna o primeiro cursor
	public Cursor irParaPrimeiroCursor() {
		Cursor d = null;
		if (!c.moveToFirst()) {

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
		c = banco.retornarTodosResultados(db, tabela);
		//c.moveToFirst();
	}
	
	public String initialResult(){
		String b="";
		c.moveToFirst();
		b = c.getString(1);
		return b;
		
	}	
	
	public String nextResult(){
		String b="";			
		if(!c.isLast())
			c.moveToNext();			
		b = c.getString(1);			
		return b;
	}
	
	//public String currentResult(){		
	//	String b="";	
	//	if(c.isLast()){
	//		c.moveToPrevious();			
	//	}
	//	b = c.getString(1);								
	//	return b;
	//}
	
	public String previousResult(){
		String b="";
		if(!c.isFirst()){
			c.moveToPrevious();		
			b = c.getString(1);
		}else{
			c.moveToFirst();
			previous = true;			
		}
		return b;
	}
	
	public Long inserirRow(String valor, String tabela){
		ContentValues cv = new ContentValues();
		cv.put("ideia", valor);
		Long a = banco.inserir(db, cv, tabela);
		return a;
	}
	
	public boolean deletarRow(String ideia, String tabela){
		if(banco.deletarIdeia(ideia, db, tabela)>0){
			return true;
		}{
			return false;
		}
	}
}
