package view;

import com.example.alimentadordememoria.R;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import controller.EditWatcher;
import controller.MainControl;

public class MainView2 extends Activity{
	public static EditText txtIdeia;
	Button btnInserir, btnDeletar;
	MainControl mc;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);//chama o m�todo onCreate da Classe m�e
		setContentView(R.layout.tela2);//Carrega a tela2
		final Context context = this.getApplicationContext();//pega o contexto desta View
		txtIdeia = (EditText)findViewById(R.id.ideia);//conecta o EditText � vari�vel txtIdeia
		btnInserir = (Button)findViewById(R.id.button1);//conecta o Button � vari�vel btnInserir
		btnDeletar = (Button)findViewById(R.id.deletar);//conecta o Button � vari�vel btnDeletar
		
		btnInserir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {							
				mc = new MainControl(context);//instancia um MainControl com o contexto atual
				mc.abrirConexao();//abre a conex�o com o banco
				String ideia = txtIdeia.getText().toString();//adiciona o texto adicionado pelo usu�rio na vari�vel ideia
				if(!ideia.equals("")){ //se ideia n�o for ""
					Long l =mc.inserirRow(ideia, "memoria");  //insere no DB a string ideia na tabela memoria
					if(l>-1){ //se o m�todo anterior retornar um valor maior que -1
						Toast.makeText(context, "Ideia Salva!", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "Ideia N�o Salva!", Toast.LENGTH_SHORT).show();
					}
				}
				mc.fecharConexao(); //fecha a conex�o
				txtIdeia.setText("");//limpa a tela				
			}
		});
		
		btnDeletar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mc = new MainControl(context);//instancia um MainControl com o contexto atual
				mc.abrirConexao();//abre a conex�o com o banco
				String ideia = txtIdeia.getText().toString();//adiciona o texto adicionado pelo usu�rio na vari�vel ideia
				if(!ideia.equals("")){ //se ideia n�o for ""
					Boolean l =mc.deletarRow(ideia, "memoria");  //delete no DB a string ideia na tabela memoria
					if(l){ //se return true
						Toast.makeText(context, "Ideia Removida!", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "Ideia N�o Removida!", Toast.LENGTH_SHORT).show();
					}
				}
				mc.fecharConexao(); //fecha a conex�o
				txtIdeia.setText("");//limpa a tela				

			}
		});
		
		//m�todos para trabalhar com EditText quando alguma tecla foi teclada
		//EditWatcher ew = new EditWatcher();				
		//txtIdeia.addTextChangedListener(ew);
		
		
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
		}catch(Exception ex){
			
		}
	}	
}
