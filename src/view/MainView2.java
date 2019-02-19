package view;

import com.example.alimentadordememoria.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import controller.EditWatcher;
import controller.MainControl;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class MainView2 extends Activity {
	public static EditText txtIdeia;
	Button btnInserir, btnDeletar;
	LinearLayout ll;
	MainControl mc;
	int pixelAnterior=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// chama o m�todo onCreate da Classe m�e
		setContentView(R.layout.tela2);// Carrega a tela2
		final Context context = this.getApplicationContext();// pega o contexto desta View
		txtIdeia = (EditText) findViewById(R.id.ideia);// conecta o EditText � vari�vel txtIdeia
		btnInserir = (Button) findViewById(R.id.button1);// conecta o Button � vari�vel btnInserir
		btnDeletar = (Button) findViewById(R.id.deletar);// conecta o Button � vari�vel btnDeletar
		ll = (LinearLayout) findViewById(R.id.linearLayout);

		btnInserir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mc = new MainControl(context);// instancia um MainControl com o contexto atual
				mc.abrirConexao();// abre a conex�o com o banco
				String ideia = txtIdeia.getText().toString();// adiciona o texto adicionado pelo usu�rio na vari�vel ideia
				if (!ideia.equals("")) { // se ideia n�o for ""
					Long l = mc.inserirRow(ideia, "memoria"); // insere no DB a string ideia na tabela memoria
					if (l > -1) { // se o m�todo anterior retornar um valor maior que -1
						Toast.makeText(context, "Ideia Salva!", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, "Ideia N�o Salva!", Toast.LENGTH_SHORT).show();
					}
				}
				mc.fecharConexao(); // fecha a conex�o
				txtIdeia.setText("");// limpa a tela
			}
		});

		btnDeletar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mc = new MainControl(context);// instancia um MainControl com o contexto atual
				mc.abrirConexao();// abre a conex�o com o banco
				String ideia = txtIdeia.getText().toString();// adiciona o texto adicionado pelo usu�rio na vari�vel ideia
				if (!ideia.equals("")) { // se ideia n�o for ""
					Boolean l = mc.deletarRow(ideia, "memoria"); // delete no DB a string ideia na tabela memoria
					if (l) { // se return true
						Toast.makeText(context, "Ideia Removida!", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, "Ideia N�o Removida!", Toast.LENGTH_SHORT).show();
					}
				}
				mc.fecharConexao(); // fecha a conex�o
				txtIdeia.setText("");// limpa a tela

			}
		});
		EditWatcher ew = new EditWatcher();
		txtIdeia.addTextChangedListener(ew);
		

		txtIdeia.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
			EditWatcher ew = new EditWatcher();

			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
					int oldRight, int oldBottom) {
				setPixelAnterior(test(v, bottom, oldBottom, ew));	
			}
		});		
				
		Runnable r = new Runnable() {
			
			@Override
			public void run() {			
				
			}
		};
		
		//InputFilter[] filterArray = new InputFilter[1];
		//filterArray[0] = new InputFilter.LengthFilter(25);
		//filterArray[1] = new InputFilter.LengthFilter(50);
		//filterArray[2] = new InputFilter.LengthFilter(75);
		//filterArray[3] = new InputFilter.LengthFilter(100);
		//txtIdeia.setFilters(filterArray);		
	}
	
	public void setPixelAnterior(int pixel){
		this.pixelAnterior = pixel;
	}
	
	public int getPixelAnterior(){
		return this.pixelAnterior;
	}

	public int test(View v, int bottom, int oldBottom, EditWatcher ew) {
		EditText et = (EditText) v;
		et.addTextChangedListener(ew);
		int b=0;
		if (bottom != oldBottom && oldBottom != 0) {
			//et = ew.alterarTextSize(et);
			b = ew.getQtdePixelHeightAntes();
			int a = et.getHeight();
			if (a > b) {
				et = ew.alterarTextSize(et);
				a = et.getHeight();								
			}
		}
		return b;
	}

	/**
	 * M�todo de pause
	 */
	@Override
	protected void onPause() {
		super.onPause();
		try {
		} catch (Exception ex) {

		}
	}

	/**
	 * M�todo de stop
	 */
	@Override
	protected void onStop() {
		super.onStop();
		try {
		} catch (Exception ex) {

		}
	}

	/**
	 * M�todo que ocorre ao fechar app
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			mc.fecharConexao();
		} catch (Exception ex) {

		}
	}

	/**
	 * M�todo resume
	 */
	@Override
	protected void onResume() {
		super.onResume();
		try {
			mc.abrirConexao();
		} catch (Exception ex) {

		}
	}

	/**
	 * M�todo iniciar
	 */
	@Override
	protected void onStart() {
		super.onStart();
		try {
		} catch (Exception ex) {

		}
	}
}
