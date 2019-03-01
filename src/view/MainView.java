package view;

import java.util.Random;

import com.example.alimentadordememoria.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import controller.ControladorDoDB;
import controller.GuardadorDeEstadosTemplate;

public class MainView extends TelaTemplate implements OnTouchListener, OnGestureListener{	
	static ControladorDoDB mc = null;		
	LinearLayout ll = null;		
	public static Context context;
	static EditText ideia = null;
	final String TABELA="memoria";
	static MenuItem item1, item2, item3, item4;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); //chama o método onCreate da Classe mãe
		context = this.getApplicationContext(); //pega o contexto desta View		
		mc = new ControladorDoDB(context); //instancia um MainControl com o contexto desta View
		setContentView(R.layout.tela1);	//Carrega a tela1	
		ll = (LinearLayout)findViewById(R.id.linearLayout);//conecta o linearLayout a variável ll
		gestureDetector = new GestureDetector(MainView.this, MainView.this);//instancia o gesture para trabalhar com os gestos na tela
		ideia = (EditText)findViewById(R.id.editText1);//conecta o editText1 a variável ideia		
		item1 = (MenuItem) findViewById(R.id.item1);
		item2 = (MenuItem) findViewById(R.id.item2);
		item3 = (MenuItem) findViewById(R.id.item3);
		item4 = (MenuItem) findViewById(R.id.item4);						
		
		//método para adicionar a ação de Touch no LinearLayout
		ll.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				gestureDetector.onTouchEvent(event);
				ll.onTouchEvent(event);
				return true;
			}
		});
		
		//método para adicionar a ação de Touch no EditText
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
		//carregarFirst2();
		
	}	
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void carregarFirst2(){
		try{
			String b = mc.initialResult();
			ideia.setText(b);
			item4.setVisible(false);
			invalidateOptionsMenu();
		}catch(Exception ex){			
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void atualizarMenu(){
		item4.setVisible(false);
		invalidateOptionsMenu();
	}
	
	/**
	 * Método para carregar todos os resultados da tabela memoria do banco
	 */
	public static void loadIdeias(){
		mc.retornarTodosResultados("memoria");		
	}
	
	/**
	 * Método para carregar a ideia no EditText
	 */
	public static void carregarIdeia(){
		try{
			String b = mc.nextResult();
			ideia.setText(b);			
		}catch(Exception ex){			
		}		
	}
		
	/**
	 * Método para carregar a 1ª ideia no EditText
	 */
	public static void carregarFirst(){
		try{
			String b = mc.initialResult();
			ideia.setText(b);
			
		}catch(Exception ex){			
		}
	}
	
	/**
	 * Método para carregar a ideia no EditText
	 */
	public static void carregarIdeiaAnterior(){
		try{
			String b = mc.previousResult();
			if(!b.equals(""))
				ideia.setText(b);
		}catch(Exception ex){			
		}		
	}
	
	public static void carregarIdeia(int posicao){
		try{
			String b = mc.currentResult(posicao); 
			ideia.setText(b);
		}catch(Exception ex){
			
		}
	}
		
	
	/**Explicação:
	 * Método executado ao tocar em algum item do menu
	 * @author Tiago Vitorino
	 * @since 16/02/2019 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.item1:					
			mc.armazenarPositionDoCursor();//esse método armazena a posição do Cursor, antes de chamar a próxima tela 			
			Intent it = new Intent(this, MainView2.class);//Criando a intenção de chamar a próxima classe/Tela			
			startActivity(it);//Inicia o método onCreate da classe MainView2
			break;
		case R.id.item2:
			if(mc.addDeadFile(TABELA, ideia.getText().toString())!=-2){
				Toast.makeText(context, "Adicionado ao arquivo morto", Toast.LENGTH_LONG).show();	
				mc.retornarTodosResultados(TABELA);			
				carregarIdeia();
			}else{
				Toast.makeText(context, "Não foi possível adicionar ao arquivo morto", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.item3:
			if(mc.resultDeadFiles(TABELA)){
				Toast.makeText(context, "Dead Files", Toast.LENGTH_LONG).show();
				carregarIdeia();
			}else{
				Toast.makeText(context, "Não há Dead Files", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.item4:
			mc.retornarTodosResultados(TABELA);			
			carregarIdeia();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
		
	/**
	 * Método de pause	
	 */
	@Override
	protected void onPause(){
		super.onPause();
		int posicao = mc.armazenarPositionDoCursor();
		GuardadorDeEstadosTemplate gd = new GuardadorDeEstadosTemplate();
		gd.guardarEstado("posicao", posicao, this);
		try{

		}catch(Exception ex){
			
		}		
	}
	
	/**
	 * Método de stop
	 */
	@Override
	protected void onStop(){
		super.onStop();
		try{

		}catch(Exception ex){
			
		}
	}
	
	/**
	 * Método que ocorre ao fechar app
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
	 * Método resume
	 */
	@Override
	protected void onResume(){
		super.onResume();
		try{
			mc.abrirConexao();			
			if(mc.checarPosition()){ //método para checar a posição do Cursor
				//método que faz select e atualiza o Cursor
				mc.atualizarCursorAposInserirIdeia("memoria");				
			}
			GuardadorDeEstadosTemplate gd = new GuardadorDeEstadosTemplate();	
			int a = gd.restaurarEstado("posicao", this);
			if(a!=-1){
				carregarIdeia(a);
			}
			//loadIdeias();
		}catch(Exception ex){
			
		}		
	}
	
	/**
	 * Método iniciar
	 */
	@Override
	protected void onStart(){
		super.onStart();
		try{
			mc.abrirConexao();			
		}catch(Exception ex){
			
		}
	}
	
	@SuppressLint("DefaultLocale")
	public static void mudarACor() {
		Random random = new Random();
		String id = String.format("%06d", random.nextInt(999999));
		ideia.setTextColor(Color.parseColor("#" + id));		
	}
}
