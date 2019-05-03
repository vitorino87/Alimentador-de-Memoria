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
			retornarTodosResultados(tabela,"n","0");
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
//	public Cursor irParaPrimeiroCursor() {
//		Cursor d = null;
//		if (!cursor.moveToFirst()) {
//
//		}
//		return d;
//	}

	// fechando banco
	public void fecharConexao() {
		try{
			banco.close();
		}catch(Exception ex){			
		}		
	}

	// conectar no banco
	public void abrirConexao() {		
		db = banco.getWritableDatabase();
	}
	
	/**
	 * Método para retornar Todos resultados da tabela com as devidas caracteristicas solicitadas
	 * Um por vez: ou só dead files, ou só tag, ou tudo.
	 * @param tabela - nome da tabela que será afetada
	 * @param dead - escolha "s" para sim, "n" para não, "t" para tudo e "" para ignorar
	 * @param tag - informe a tag, escolha "0" para padrão, "" para ignorar ou algum número de tag salva
	 */
	public void retornarTodosResultados(String tabela, String dead, String tag){
		abrirConexao();
		cursor = banco.retornarTodosResultados(db, tabela, dead, tag);
		//cursor = banco.retornarTodosResultados(db, tabela, 1, "n");
	}
	
	public String initialResult(){
		String b="";
		if(cursor.moveToFirst())
			b = cursor.getString(1);
		return b;
		
	}	
	
	public int getTagAtual(){
		int b=-1;
		try{
			b = cursor.getInt(3);
		}catch(Exception e){
			b=-1;
		}						
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
		}
		return b;
	}
	
	public Long inserirRow(Object ideia, Object morto, String tabela, int tag){
		if(!banco.buscar(db, (String)ideia,tabela)){
			ContentValues cv = new ContentValues();
			cv.put("morto", (String)morto);
			cv.put("ideia", (String)ideia);
			cv.put("tag", tag);
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
	
	public int atualizarDB(ContentValues cv, String tabela, String ideia){
		abrirConexao();		
		int a = banco.atualizarIdeia(db, cv, tabela, ideia);
		if(a!=-2){
			cursor = null;
		}
		return a;
	}
	
	public int addOrChangeTag(String tabela, String ideia, int tag){
		ContentValues cv = new ContentValues();
		cv.put("tag", tag);
		int a = atualizarDB(cv, tabela, ideia);
		return a;
	}
	
	public int addOrDelDeadFile(String tabela, String ideia, String dead){
		ContentValues cv = new ContentValues();
		cv.put("morto", dead);
		int a = atualizarDB(cv, tabela, ideia);
		return a;
	}		
	
	public boolean resultDeadFiles(String tabela){
		abrirConexao();		
		cursor = banco.retornarTodosResultados(db, tabela, "s","0");
		if(cursor.moveToFirst()){
			return true;
		}else{
			return false;
		}
	}
}
