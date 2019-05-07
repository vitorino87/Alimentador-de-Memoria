package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper
{
	//Controle da versão
	private static final int VERSAO_BANCO = 3;
	//Cria a tabela com _id sequencial
	private static final String SCRIPT_TABLE_CREATE= "create table memoria(id integer primary key autoincrement, ideia text, morto text, tag integer);";
	
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
	 * Método para retornar Todos resultados da tabela com as devidas caracteristicas solicitadas
	 * Um por vez: ou só dead files, ou só tag, ou tudo.
	 * @param db - SQLiteDatabase que irá receber o método
	 * @param tabela - nome da tabela que será afetada
	 * @param morto - escolha "s" para sim, "n" para não, "t" para tudo e "" para ignorar
	 * @param tag - informe a tag
	 * @return o Cursor com todos os resultados ou null se não encontrou nada
	 */
	public Cursor retornarTodosResultados(SQLiteDatabase db, String tabela, String morto, String tag){
		Cursor c = null;
		if(morto.equals("t"))
			c = db.query(tabela, null, null, null, null, null, null);
		else if(morto=="n" || morto=="s"){	
			String[] args = {morto};
			c = db.query(tabela, null, "morto=?", args, null, null, null);			
		}else{
			String[] args = {tag};
			c = db.query(tabela, null, "tag=?", args, null, null, null);					
		}
		return c;
	}
	/**
	 * Método para retornar todos resultados que estão de acordo com as caracteristicas adicionadas
	 * @param db - SQLiteDatabase
	 * @param tabela - tabela que será consultada
	 * @param operacao - informe a operação desejada<ul><li>1 - todos</li><li>2 - tag</li><li>3 - morto</li></ul>
	 * @param info - opcional, informe a informação a ser buscada. Funciona somente para operação 2 e 3;
	 * @return um cursor com o resultado obtido
	 */
//	public Cursor retornarTodosResultados(SQLiteDatabase db, String tabela, int operacao, String info){
//		Cursor c = null;
//		String[] args = {info};
//		switch(operacao){
//		case 1:
//			c = db.query(tabela, null, null, null, null, null, null);
//			break;
//		case 2:			
//			c = db.query(tabela, null, "tag=?", args, null, null, null);
//			break;
//		case 3:			
//			c = db.query(tabela, null, "morto=?", args, null, null, null);
//			break;
//		}				
//		return c;
//	}
	
	public Cursor retornarTodosResultados(SQLiteDatabase db, String tabela, int operacao, String[] info){
		Cursor c = null;
		String[] args = info;
		try{
			switch(operacao){
			case 1:
				c = db.query(tabela, null, null, null, null, null, null);//utilizado pelo export
				break;
			case 2:			//utilizado pela funcionalidade Tag
				c = db.query(tabela, null, "tag=? AND id BETWEEN ? AND ? LIMIT 5", args, null, null, null);
				break;
			case 3:			//utilizado pela funcionalidade DeadFiles e default
				c = db.query(tabela, null, "morto=? AND id BETWEEN ? AND ? LIMIT 5", args, null, null, null);
				break;
			case 4:
				c = db.query(tabela, null, "morto=? LIMIT 5", args, null, null, null);
				break;
			case 5:
				//c = db.query(tabela, null, "morto=? AND id BETWEEN ? AND ? ORDER BY id DESC LIMIT 5", args, null, null, null);
				c = db.rawQuery("SELECT * FROM (SELECT * FROM memoria WHERE morto =? AND id BETWEEN ? AND ? ORDER BY id DESC LIMIT 5) ORDER BY id ASC", args);
				break;
			case 6:
				//c = db.query(tabela, null, "tag=? AND id BETWEEN ? AND ? ORDER BY id DESC LIMIT 5", args, null, null, null);
				c = db.rawQuery("SELECT * FROM (SELECT * FROM memoria WHERE tag=? AND id BETWEEN ? AND ? ORDER BY id DESC LIMIT 5) ORDER BY id ASC", args);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
						
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
	
	public int getTagMax(SQLiteDatabase db){
		Cursor c = null;
		int a = -1;		
		c = db.rawQuery("select max(tag) from memoria", null);
		if(c.moveToFirst())
			a = c.getInt(0);
		return a;
	}
	
	public int getMaxId(SQLiteDatabase db){
		Cursor c = null;
		int a = -1;
		c = db.rawQuery("select max(id) from memoria", null);
		if(c.moveToFirst())
			a = c.getInt(0);
		return a;
	}
	
	public int getMinId(SQLiteDatabase db){
		Cursor c = null;
		int a = -1;
		c = db.rawQuery("select min(id) from memoria", null);
		if(c.moveToFirst())
			a = c.getInt(0);
		return a;
	}
	
	final String[] query = {"select max(tag) from memoria",
			"select max(id) from memoria",
			"select min(id) from memoria"};
	
	public int getInfoDB(SQLiteDatabase db, int index){
		Cursor c = null;
		int a = -1;
		c = db.rawQuery(query[index], null);
		if(c.moveToFirst())
			a = c.getInt(0);
		return a;
	}
	
		
	/**
	 * Retorna os dead files, são os campos que possuem a coluna morto com valor 1
	 * @param db
	 * @param tabela
	 * @return
	 */
//	public Cursor oretornarTodosDeadFiles(SQLiteDatabase db, String tabela){
//		Cursor c = null;
//		String[] args = {"s"};
//		c = db.query(tabela, null, "morto=?", args, null, null, null);		
//		return c;
//	}
}
	

