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
		super.onCreate(savedInstanceState);//chama o método onCreate da Classe mãe
		setContentView(R.layout.tela2);//Carrega a tela2
		final Context context = this.getApplicationContext();//pega o contexto desta View
		txtIdeia = (EditText)findViewById(R.id.ideia);//conecta o EditText à variável txtIdeia
		btnInserir = (Button)findViewById(R.id.button1);//conecta o Button à variável btnInserir
		btnDeletar = (Button)findViewById(R.id.deletar);//conecta o Button à variável btnDeletar
		
		btnInserir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {							
				mc = new MainControl(context);//instancia um MainControl com o contexto atual
				mc.abrirConexao();//abre a conexão com o banco
				String ideia = txtIdeia.getText().toString();//adiciona o texto adicionado pelo usuário na variável ideia
				if(!ideia.equals("")){ //se ideia não for ""
					Long l =mc.inserirRow(ideia, "memoria");  //insere no DB a string ideia na tabela memoria
					if(l>-1){ //se o método anterior retornar um valor maior que -1
						Toast.makeText(context, "Ideia Salva!", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "Ideia Não Salva!", Toast.LENGTH_SHORT).show();
					}
				}
				mc.fecharConexao(); //fecha a conexão
				txtIdeia.setText("");//limpa a tela				
			}
		});
		
		btnDeletar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mc = new MainControl(context);//instancia um MainControl com o contexto atual
				mc.abrirConexao();//abre a conexão com o banco
				String ideia = txtIdeia.getText().toString();//adiciona o texto adicionado pelo usuário na variável ideia
				if(!ideia.equals("")){ //se ideia não for ""
					Boolean l =mc.deletarRow(ideia, "memoria");  //delete no DB a string ideia na tabela memoria
					if(l){ //se return true
						Toast.makeText(context, "Ideia Removida!", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "Ideia Não Removida!", Toast.LENGTH_SHORT).show();
					}
				}
				mc.fecharConexao(); //fecha a conexão
				txtIdeia.setText("");//limpa a tela				

			}
		});
		
		//métodos para trabalhar com EditText quando alguma tecla foi teclada
		//EditWatcher ew = new EditWatcher();				
		//txtIdeia.addTextChangedListener(ew);
		
		
	}
	
	/**
	 * Método de pause	
	 */
	@Override
	protected void onPause(){
		super.onPause();
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
		}catch(Exception ex){
			
		}
	}	
}
