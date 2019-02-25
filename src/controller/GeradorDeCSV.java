package controller;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;

public class GeradorDeCSV {			
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public String getCSV(ControladorDoDB mc, String TABELA){
		mc.abrirConexao();
		mc.retornarTodosResultados(TABELA);
		Cursor cursor = mc.getCursor();
		String csv="";
		int qtdeColunas = cursor.getColumnCount();
		int qtdeDeRows = cursor.getCount();		
		cursor.moveToFirst();
		for(int i=0; i<qtdeDeRows;i++){
			for(int j=0; j<qtdeColunas;j++){				
				//n�o estou armazenando o campo id, porque ele � auto-increment�vel e chave prim�ria
				//abaixo, verifica se o campo � integer e converte para String
				if(cursor.getType(j)==Cursor.FIELD_TYPE_INTEGER && !cursor.getColumnName(j).equals("id"))  
					csv+=String.valueOf(cursor.getString(j));
				else if(cursor.getType(j)==Cursor.FIELD_TYPE_STRING) //verificando se o tipo � String
					csv+="\""+cursor.getString(j)+"\"";	//armazenando o campo ideia
				if(!cursor.getColumnName(j).equals("ideia"))//verifica se a coluna � ideia
					csv+=",";	//ideia � o �ltimo campo, por isso n�o � necess�rio adicionar virgula
			}
			cursor.moveToNext();	//movendo para o pr�ximo resultado		
			csv+="\n";				//pulando linha
		}
		return csv;
	}
	
}
