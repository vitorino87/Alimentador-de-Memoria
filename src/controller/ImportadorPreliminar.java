package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

/**
 * Esta classe é preliminar, ela não realiza a importação completa
 * ela só carrega o arquivo selecionado pelo usuário na memória do programa
 * É na classe MainView2 que é definido onde será salvo os dados
 * @author tiago.lucas
 *
 */
public class ImportadorPreliminar {
	private static final String TIPOMIME = "text/csv";
	private static final String NOMEDOPROGRAMA = "FeedMemo";
	Activity ac;
	
	public ImportadorPreliminar(Activity ac){
		this.ac = ac;
	}

	public int abrirArquivo(){
		//Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType(TIPOMIME);
		ac.startActivityForResult(intent, 1);
		return 2;
	}
	
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public ArrayList<String> importar(int requestCode, int resultCode, Intent data){
		ArrayList<String> lista = new ArrayList<String>();
		switch(requestCode){
		case 1:
			if(resultCode == ac.RESULT_OK){
				Uri uri = null;
				uri = data.getData();
				Log.i(NOMEDOPROGRAMA, "Uri: "+uri.toString());
				try {					
					InputStream is  = ac.getContentResolver().openInputStream(uri);
					FileInputStream fis = (FileInputStream)is;
					Reader rd = new InputStreamReader(is, StandardCharsets.UTF_8);					
					int ch;
					String text = "";
					while((ch = rd.read())!=-1){
						if(ch != 10 && ch != 13){
							if(ch!=34)
								text+=String.valueOf((char)ch);						
						}else{
							lista.add(text);
							text="";
						}						
					}										
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return lista;
	}
	
	public void carregarNoBD(ArrayList<String> lista){
		Iterator i = lista.iterator();		
		while(i.hasNext()){

		}
	}
	
}
