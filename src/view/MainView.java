package view;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import controller.ControladorDoDB;
import controller.GuardadorDeEstadosTemplate;

public class MainView extends TelaTemplate implements OnTouchListener, OnGestureListener{	
	static ControladorDoDB mc = null;		
	static LinearLayout ll = null;		
	public static Context context;
	static EditText ideia = null;
	final String TABELA="memoria";
	ViewGroup gi = null;
	Menu menu = null;	
	static boolean allcaps = false;
	static boolean isColored = true;
	private static String bkp = "";
	private JanelaDeTags jt;
	private static TextView tagView = null;
	static TextView tagMax;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); //chama o m�todo onCreate da Classe m�e
		context = this.getApplicationContext(); //pega o contexto desta View		
		mc = new ControladorDoDB(context); //instancia um MainControl com o contexto desta View
		setContentView(R.layout.tela1);	//Carrega a tela1	
		ll = (LinearLayout)findViewById(R.id.linearLayout);//conecta o linearLayout a vari�vel ll
		gestureDetector = new GestureDetector(MainView.this, MainView.this);//instancia o gesture para trabalhar com os gestos na tela
		ideia = (EditText)findViewById(R.id.editText1);//conecta o editText1 a vari�vel ideia
		tagView = (TextView) findViewById(R.id.textView1);
		tagMax = (TextView)findViewById(R.id.tagMax);
		
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
		
		tagMax.setText("Tag Max: "+mc.getTagMax());
		
	}	
	
	/**
	 * M�todo para carregar todos os resultados da tabela memoria do banco
	 */
	public static void loadIdeias(){mc.retornarTodosResultados("memoria","n","0");}
	
	/**
	 * M�todo para carregar a ideia no EditText
	 */
	@SuppressLint("NewApi")
	public static void carregarIdeia(){
		try{			
			String b = mc.nextResult();			
			carregar(b);
			//ideia.setText(a);			
		}catch(Exception ex){}		
	}
		
	/**
	 * M�todo para carregar a 1� ideia no EditText
	 */
	public static void carregarFirst(){
		try{
			String b = mc.initialResult();
			carregar(b);
			//ideia.setText(b);
			
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
				carregar(b);
				//ideia.setText(b);
		}catch(Exception ex){			
		}		
	}
	
	public static void carregarIdeia(int posicao){
		try{
			String b = mc.currentResult(posicao);
			carregar(b);
			//ideia.setText(b);
		}catch(Exception ex){
			
		}
	}
	
	@SuppressLint("DefaultLocale")
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void carregar(String b){
		bkp = b;
		String a = null;
		try {
			a = new String(b.getBytes("UTF8"), StandardCharsets.UTF_8);
			a = a.replace("\u0375", ",");
		} catch (UnsupportedEncodingException e) {e.printStackTrace();}	
		
//		if(a.length()>15)
//			ideia.setGravity(Gravity.FILL);
//		else
//			ideia.setGravity(Gravity.CENTER);
		
		if(allcaps)
			ideia.setText(a.toUpperCase());
		else
			ideia.setText(a);
		
		if(ideia.getLineCount()>1)
			ideia.setGravity(Gravity.FILL);
		else
			ideia.setGravity(Gravity.CENTER);			
		if(isColored){
			controller.GeradorDeCorRandomizado gcr = new controller.GeradorDeCorRandomizado();
			String cor = gcr.gerarCorRandomizada();
			ll.setBackgroundColor(Color.parseColor("#"+cor));
			ideia.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}else
			ll.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		tagView.setText("Tag: "+mc.getTagAtual());
		
	}
	
	/**Explica��o:
	 * M�todo executado ao tocar em algum item do menu
	 * @author Tiago Vitorino
	 * @since 16/02/2019 
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		jt = new JanelaDeTags(MainView.this, mc, TABELA, ideia.getText().toString(),menu);
		int id = item.getItemId();		
		int a;
		switch (id) {
		case R.id.item1:					
			mc.armazenarPositionDoCursor();//esse m�todo armazena a posi��o do Cursor, antes de chamar a pr�xima tela 			
			Intent it = new Intent(this, MainView2.class);//Criando a inten��o de chamar a pr�xima classe/Tela
			startActivity(it);//Inicia o m�todo onCreate da classe MainView2			
			break;
		case R.id.item2:
			a = mc.armazenarPositionDoCursor();
			if(mc.addOrDelDeadFile(TABELA, ideia.getText().toString(),"s")!=-2){
				Toast.makeText(context, "Adicionado ao arquivo morto", Toast.LENGTH_LONG).show();	
				mc.retornarTodosResultados(TABELA,"n","0");	
				mc.goToPositionCursor(a-1);
				carregarIdeia();
			}else{
				Toast.makeText(context, "N�o foi poss�vel adicionar ao arquivo morto", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.item3:
			if(mc.resultDeadFiles(TABELA)){				
				Toast.makeText(context, "Dead Files", Toast.LENGTH_LONG).show();				
				menu.clear();
				MenuDoMainView mmv = new MenuDoMainView(MainView.this, menu);	   
			    mmv.chamarMenuInicial(R.menu.menu2);
			    carregarIdeia();
			}else{
				Toast.makeText(context, "N�o h� Dead Files", Toast.LENGTH_LONG).show();					
			}
			break;		
			
		case R.id.item4:			
			if (item.isChecked()) item.setChecked(false);
            else item.setChecked(true);			
			allcaps = item.isChecked();				
			if(allcaps)
				ideia.setText(ideia.getText().toString().toUpperCase());
			else
				ideia.setText(bkp);
			break;
		case R.id.item5:
			a = mc.armazenarPositionDoCursor();
			if(mc.addOrDelDeadFile(TABELA, ideia.getText().toString(), "n")!=2){
				Toast.makeText(context, "Removido do arquivo morto", Toast.LENGTH_LONG).show();
				if(mc.resultDeadFiles(TABELA)){
					mc.goToPositionCursor(a-1);
					carregarIdeia();
				}else{
					menu.clear();
					MenuDoMainView mmv = new MenuDoMainView(MainView.this, menu);	   
				    mmv.chamarMenuInicial(R.menu.menu);
					Toast.makeText(context, "N�o h� Dead Files, por isso Retornou", Toast.LENGTH_LONG).show();
					mc.retornarTodosResultados(TABELA,"n","0");						
					carregarIdeia();
				}
				
			}else{
				Toast.makeText(context, "N�o foi poss�vel remover do arquivo morto", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.item6:
//			mc.retornarTodosResultados(TABELA,"n",null);			
//			carregarIdeia();
//			menu.clear();
//			MenuDoMainView mmv = new MenuDoMainView(MainView.this, menu);	   
//		    mmv.chamarMenuInicial(R.menu.menu);
			retornar();
			break;
			
		case R.id.item7:
			if(item.isChecked())
				item.setChecked(false);
			else
				item.setChecked(true);				
			isColored=item.isChecked();		
			break;
			
		case R.id.item8:			
			jt.onCreateDialog(0).show();	
			//mc.addOrChangeTag(TABELA, ideia.getText().toString(), jt.getTag());
			break;
			
		case R.id.itemVisualizarItensTag:
			jt.onCreateDialog(2).show();
			break;
			
		case R.id.itemChangeTag:
			jt.onCreateDialog(1).show();
			break;
			
		case R.id.itemTagRetornar:
			retornar();
			JanelaDeTags.checarMenu = false;
			break;
		}		
		return super.onOptionsItemSelected(item);
	}
	
	public void retornar(){
		mc.retornarTodosResultados(TABELA,"n","0");			
		carregarIdeia();
		menu.clear();
		MenuDoMainView mmv = new MenuDoMainView(MainView.this, menu);	   
	    mmv.chamarMenuInicial(R.menu.menu);
	}
		
	/**
	 * M�todo de pause	
	 */
	@Override
	protected void onPause(){
		super.onPause();
		int posicao = mc.armazenarPositionDoCursor();
		GuardadorDeEstadosTemplate gd = new GuardadorDeEstadosTemplate();
		gd.guardarEstado("posicao", posicao, this);
	}
	
	/**
	 * M�todo de stop
	 */
	@Override
	protected void onStop(){super.onStop();}
	
	/**
	 * M�todo que ocorre ao fechar app
	 */
	@Override
	protected void onDestroy(){
		super.onDestroy();
		try{
			mc.fecharConexao();
		}catch(Exception ex){}
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
			GuardadorDeEstadosTemplate gd = new GuardadorDeEstadosTemplate();	
			int a = gd.restaurarEstado("posicao", this);
			if(a!=-1){
				carregarIdeia(a);
			}
		}catch(Exception ex){}		
	}
	
	/**
	 * M�todo iniciar
	 */
	@Override
	protected void onStart(){
		super.onStart();
		try{
			mc.abrirConexao();			
		}catch(Exception ex){}
	}
	
	@SuppressLint("DefaultLocale")
	public static void mudarACor() {
		Random random = new Random();
		String id = String.format("%06d", random.nextInt(999999));
		ideia.setTextColor(Color.parseColor("#" + id));		
	}		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	    	    
	    MenuDoMainView mmv = new MenuDoMainView(MainView.this, menu);	
	    this.menu = menu;
	    return mmv.chamarMenuInicial(R.menu.menu);	    
	}
	
	
}
