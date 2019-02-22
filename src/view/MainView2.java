package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.example.alimentadordememoria.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
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
	Button btnExportar,btnImportar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// chama o método onCreate da Classe mãe
		setContentView(R.layout.tela2);// Carrega a tela2
		final Context context = this.getApplicationContext();// pega o contexto desta View
		txtIdeia = (EditText) findViewById(R.id.ideia);// conecta o EditText à variável txtIdeia
		btnInserir = (Button) findViewById(R.id.button1);// conecta o Button à variável btnInserir
		btnDeletar = (Button) findViewById(R.id.deletar);// conecta o Button à variável btnDeletar
		ll = (LinearLayout) findViewById(R.id.linearLayout);
		btnExportar = (Button) findViewById(R.id.btnExportar);
		btnImportar = (Button) findViewById(R.id.btnImportar);
		
		btnExportar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);				
				intent.setType("text/csv");
				startActivityForResult(intent, 1);
			}
		});

		btnInserir.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mc = new MainControl(context);// instancia um MainControl com o contexto atual
				mc.abrirConexao();// abre a conexão com o banco
				String ideia = txtIdeia.getText().toString();// adiciona o texto adicionado pelo usuário na variável ideia
				if (!ideia.equals("")) { // se ideia não for ""
					Long l = mc.inserirRow(ideia, "memoria"); // insere no DB a string ideia na tabela memoria
					if (l > -1) { // se o método anterior retornar um valor maior que -1
						Toast.makeText(context, "Ideia Salva!", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, "Ideia Não Salva!", Toast.LENGTH_SHORT).show();
					}
				}
				mc.fecharConexao(); // fecha a conexão
				txtIdeia.setText("");// limpa a tela
			}
		});

		btnDeletar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mc = new MainControl(context);// instancia um MainControl com o contexto atual
				mc.abrirConexao();// abre a conexão com o banco
				String ideia = txtIdeia.getText().toString();// adiciona o texto adicionado pelo usuário na variável ideia
				if (!ideia.equals("")) { // se ideia não for ""
					Boolean l = mc.deletarRow(ideia, "memoria"); // delete no DB a string ideia na tabela memoria
					if (l) { // se return true
						Toast.makeText(context, "Ideia Removida!", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(context, "Ideia Não Removida!", Toast.LENGTH_SHORT).show();
					}
				}
				mc.fecharConexao(); // fecha a conexão
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
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				 ContentResolver cr = null;
				 try {
					cr.openFileDescriptor(data.getData(), null);
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} 
				try {
					ParcelFileDescriptor pfd = cr.openFileDescriptor(data.getData(), null);					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String FilePath = data.getData().getPath();
				try {
					InputStream is = new FileInputStream(FilePath);					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// textFile.setText(FilePath);
			}
			break;

		}

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
	 * Método de pause
	 */
	@Override
	protected void onPause() {
		super.onPause();
		try {
		} catch (Exception ex) {

		}
	}

	/**
	 * Método de stop
	 */
	@Override
	protected void onStop() {
		super.onStop();
		try {
		} catch (Exception ex) {

		}
	}

	/**
	 * Método que ocorre ao fechar app
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
	 * Método resume
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
	 * Método iniciar
	 */
	@Override
	protected void onStart() {
		super.onStart();
		try {
		} catch (Exception ex) {

		}
	}
}
