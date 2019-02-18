package view;

import com.example.alimentadordememoria.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import controller.MainControl;
import controller.TelaAux;

public class MainView extends TelaTemplate implements OnTouchListener, OnGestureListener{	
	static MainControl mc = null;		
	LinearLayout ll = null;		
	public static Context context;
	static EditText ideia = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); //chama o m�todo onCreate da Classe m�e
		context = this.getApplicationContext(); //pega o contexto desta View		
		mc = new MainControl(context); //instancia um MainControl com o contexto desta View
		setContentView(R.layout.tela1);	//Carrega a tela1	
		ll = (LinearLayout)findViewById(R.id.linearLayout);//conecta o linearLayout a vari�vel ll
		gestureDetector = new GestureDetector(MainView.this, MainView.this);//instancia o gesture para trabalhar com os gestos na tela
		ideia = (EditText)findViewById(R.id.editText1);//conecta o editText1 a vari�vel ideia					
		
		//m�todo para adicionar a a��o de Touch no LinearLayout
		ll.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				gestureDetector.onTouchEvent(event);
				ll.onTouchEvent(event);
				return true;
			}
		});
		
		//m�todo para adicionar a a��o de Touch no EditText
		ideia.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				gestureDetector.onTouchEvent(event);
				ideia.onTouchEvent(event);
				return true;
			}
		});
		//faz a busca e carrega os dados do banco num cursor
		loadIdeias();
		//move o cursor para o primeiro elemento retornado do banco
		carregarFirst();
	}	
	
	/**
	 * M�todo para carregar todos os resultados da tabela memoria do banco
	 */
	public static void loadIdeias(){
		mc.retornarTodosResultados("memoria");		
	}
	
	/**
	 * M�todo para carregar a ideia no EditText
	 */
	public static void carregarIdeia(){
		try{
			String b = mc.nextResult();
			ideia.setText(b);
		}catch(Exception ex){			
		}		
	}
		
	/**
	 * M�todo para carregar a 1� ideia no EditText
	 */
	public static void carregarFirst(){
		try{
			String b = mc.initialResult();
			ideia.setText(b);
		}catch(Exception ex){			
		}
	}
	
	/**
	 * M�todo para carregar a ideia no EditText
	 */
	public static void carregarIdeiaAnterior(){
		try{
			String b = mc.previousResult();
			if(!b.equals(""))
				ideia.setText(b);
		}catch(Exception ex){			
		}		
	}
		
	
	/**Explica��o:
	 * M�todo executado ao tocar em algum item do menu
	 * @author Tiago Vitorino
	 * @since 16/02/2019 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.item1:					
			mc.armazenarPositionDoCursor();//esse m�todo armazena a posi��o do Cursor, antes de chamar a pr�xima tela 			
			Intent it = new Intent(this, MainView2.class);//Criando a inten��o de chamar a pr�xima classe/Tela			
			startActivity(it);//Inicia o m�todo onCreate da classe MainView2
			break;
		}
		return super.onOptionsItemSelected(item);
	}
		
	/**
	 * M�todo de pause	
	 */
	@Override
	protected void onPause(){
		super.onPause();
		try{

		}catch(Exception ex){
			
		}		
	}
	
	/**
	 * M�todo de stop
	 */
	@Override
	protected void onStop(){
		super.onStop();
		try{

		}catch(Exception ex){
			
		}
	}
	
	/**
	 * M�todo que ocorre ao fechar app
	 */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		try{
			mc.fecharConexao();
		}catch(Exception ex){
			
		}
	}
	
	/**
	 * M�todo resume
	 */
	@Override
	protected void onResume(){
		super.onResume();
		try{
			mc.abrirConexao();			
			if(mc.checarPosition()){ //m�todo para checar a posi��o do Cursor
				//m�todo que faz select e atualiza o Cursor
				mc.atualizarCursorAposInserirIdeia("memoria");				
			}
			//loadIdeias();
		}catch(Exception ex){
			
		}		
	}
	
	/**
	 * M�todo iniciar
	 */
	@Override
	protected void onStart(){
		super.onStart();
		try{
			mc.abrirConexao();			
		}catch(Exception ex){
			
		}
	}
}
