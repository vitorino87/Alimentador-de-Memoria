package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper
{
	//Controle da versão
	private static final int VERSAO_BANCO = 2;
	//Cria a tabela com _id sequencial
	private static final String SCRIPT_TABLE_CREATE= "create table memoria(id integer primary key autoincrement, ideia text, morto text);";	
	
	public Banco(Context context, String nomeBanco) {//criando o banco
		super(context, nomeBanco, null, VERSAO_BANCO);
	}
	
	//Called when the database is created for the first time.	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{		
		//criando uma nova tabela
		try{
			db.execSQL(SCRIPT_TABLE_CREATE);
		}catch(Exception ex){
			System.out.println("não deu certo"+ex.toString());
		}
		
	}
	
	/**
	 * Método para inserir dados na tabela
	 * @param db - SQLiteDatabase que irá receber o método
	 * @param values - valores a serem adicionados
	 * @param tabela - nome da tabela
	 * @return um long com a posição da arrow afetada 
	 */
	public long inserir(SQLiteDatabase db, ContentValues values, String tabela)
	{		
		long id = db.insert(tabela, null, values);
		return id;
	}
	
	/**
	 * Método para buscar um dado na tabela
	 * @param id - id do dado
	 * @param db - SQLiteDatabase que irá receber o método
	 * @param colunas - Colunas a serem retornadas
	 * @return um Cursor com os dados encontrados
	 */
	public Cursor buscar(int id, SQLiteDatabase db, String [] colunas)
	{
		Cursor c = null;
		try{
			c = db.query("questao", colunas, "questao.id="+id, null, null, null, null);
			return c;
		}catch(Exception ex){
			return null;
		}
	}
	
	public boolean buscar(SQLiteDatabase db, String ideia, String tabela)
	{
		String[] args = {ideia};
		Cursor c = null;
		try{
			c = db.query(tabela, null, "ideia like ?", args, null, null, null);
			if(c.moveToFirst()){
				return true;
			}else{
				return false;
			}
		}catch(Exception ex){
			return false;
		}
	}
	
	/**
	 * Método para retornar a row com maior id 
	 * @param db - SQLiteDatabase que irá receber o método
	 * @return um Cursor com a row com maior id
	 */
	public Cursor buscarIdMax(SQLiteDatabase db)
	{
		Cursor c = null;
		try{
			String [] s = new String[]{"questao.id","questao.enunciado",
					"questao.itemA","questao.itemB","questao.itemC","questao.itemD"
					,"questao.itemE","questao.gabarito"};
			//c = db.query("questao", s, null, null, null, null, "questao.id desc");
			c = db.rawQuery("select * from questao order by questao.id desc limit 1", null);
			//int idMax = Integer.parseInt(c.moveToLast());
			c.moveToFirst();
			return c;
		}catch(Exception ex){
			return null;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Realiza o update da tabela com os dados fornecidos
	 * @param id - id da row a ser atualizada
	 * @param db - SQLiteDatabase que irá receber o método 
	 * @param values - valores que serão adicionados 
	 * @param tabela - nome da tabela
	 * @return o número da row afetada ou -1 quando falha
	 */
	public int update(int id, SQLiteDatabase db, ContentValues values, String tabela)
	{
		int rtn;
		try{
			rtn=db.update(tabela, values, "id="+id, null);
		}catch(Exception ex){
			rtn=-1;
		}
		return rtn;
	}
	
	/**
	 * Método para retornar Todos resultados da tabela fornecida
	 * @param db - SQLiteDatabase que irá receber o método
	 * @param tabela - nome da tabela que será afetada
	 * @return o Cursor com todos os resultados ou null se não encontrou nada
	 */
	public Cursor retornarTodosResultados(SQLiteDatabase db, String tabela){
		Cursor c = null;
		String[] args = {"n"};
		c = db.query(tabela, null, "morto=?", args, null, null, null);
		return c;
	}
	
	public int deletarIdeia(String ideia, SQLiteDatabase db, String tabela){
		int a=-2;
		String[] ideias ={ideia};
		if(!ideia.equals("")){
			a = db.delete(tabela, "ideia like ?",ideias);
		}		
		return a;
	}
	
	public int atualizarIdeia(SQLiteDatabase db, ContentValues novoValor, String tabela, String ideia){
		int a = -2;
		try{
			String[] args = {ideia};
			a=db.update(tabela, novoValor, "ideia like ?", args);			
		}catch(Exception e){			
		}
		return a;
	}
	/**
	 * Retorna os dead files, são os campos que possuem a coluna morto com valor 1
	 * @param db
	 * @param tabela
	 * @return
	 */
	public Cursor retornarTodosDeadFiles(SQLiteDatabase db, String tabela){
		Cursor c = null;
		String[] args = {"s"};
		c = db.query(tabela, null, "morto=?", args, null, null, null);
		int a = c.getCount();
		return c;
	}
	
	/*public void exportarToCSV(SQLiteDatabase db, String tabela){
		Cursor c = retornarTodosResultados(db, tabela);
		int a = c.getColumnCount();
	}*/
	
	/*public File getPublicAlbumStorageDir(String albumName) {
	    // Get the directory for the user's public pictures directory.
	    File file = new File(Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES), albumName);
	    if (!file.mkdirs()) {
	        Log.e("FeedMemo", "Directory not created");
	    }
	    return file;
	}*/
	
	/*public void salvarLinhasProcessadas(String file, String textoParaSalvar) {
        try {
            if (file != null) {
            	FileWriter fw = new FileWriter(file, true);
            	fw.write(textoParaSalvar);
            }
        } catch (Exception ex) {
        	Log.e("FeedMemo", "File not created");
        }
    }*/
}
	

