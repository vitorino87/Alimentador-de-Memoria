package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import controller.ControladorDoDB;
import com.example.alimentadordememoria.R;

public class JanelaDeTags {
	
	Activity ac;
	private int tag;	
	ControladorDoDB mc;
	String tabela;
	String ideia;
	TextView tx = null;
	Menu menu;
	private int a;
	MenuDoMainView mmv;
	static private int tagCarregada;
	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnApagar;
	EditText edt;
	static boolean checarMenu=false;
		
	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}	

	JanelaDeTags(Activity ac, ControladorDoDB mc, String tabela, String ideia, Menu menu){
		this.ac = ac;
		this.mc = mc;
		this.tabela = tabela;
		this.ideia = ideia;
		this.menu = menu;		
	}
	
	/***
	 * Este método cria a caixa de dialogo.
	 * Escolha 0 para criar a caixa de dialogo para adicionar
	 * Escolha 1 para criar a caixa de dialogo para alterar 
	 * @param choose
	 * @return
	 */
	public Dialog onCreateDialog(final int choose) {
		
		if(ideia.contains(","))
			ideia = ideia.replace(",", "\u0375");
		
		AlertDialog.Builder alert = new AlertDialog.Builder(ac);
		// Get the layout inflater
	    LayoutInflater inflater = ac.getLayoutInflater();	  
	    final View layout = inflater.inflate(R.layout.janela_de_tags, null);
	    edt = (EditText) layout.findViewById(R.id.editTextTag);
	    tx = (TextView)layout.findViewById(R.id.textViewTag);
	    btn1 = (Button)layout.findViewById(R.id.button1);
	    btn2 = (Button)layout.findViewById(R.id.button2);
	    btn3 = (Button)layout.findViewById(R.id.button3);
	    btn4 = (Button)layout.findViewById(R.id.button4);
	    btn5 = (Button)layout.findViewById(R.id.button5);
	    btn6 = (Button)layout.findViewById(R.id.button6);
	    btn7 = (Button)layout.findViewById(R.id.button7);
	    btn8 = (Button)layout.findViewById(R.id.button8);
	    btn9 = (Button)layout.findViewById(R.id.button9);
	    btn0 = (Button)layout.findViewById(R.id.button0);
	    btnApagar = (Button)layout.findViewById(R.id.buttonApagar);
	    btn1.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"1");
			}
		});
	    btn2.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"2");
			}
		});
	    btn3.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"3");
			}
		});
	    btn4.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"4");
			}
		});
	    btn5.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"5");
			}
		});
	    btn6.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"6");
			}
		});
	    btn7.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"7");
			}
		});
	    btn8.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"8");
			}
		});
	    btn9.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"9");
			}
		});
	    btn0.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				edt.setText(edt.getText()+"0");
			}
		});
	    btnApagar.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				String texto = edt.getText().toString();
				int tam = texto.length();				
				if(tam>0)
					edt.setText(texto.subSequence(0, tam-1));
			}
		});
	    String msg[] = {"Adicionar tag","Alterar para tag","Carregar tag..."};
	    mmv = new MenuDoMainView(ac, menu);
	    switch(choose){
		case 0:
		case 1:
			tx.setText(msg[choose] + "\nTag Atual: "+mc.getTagAtual());			
			alert.setView(layout)
			.setTitle(msg[choose])
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {            	                     
	            	   try { 	    	            		   
	            		   a = mc.armazenarPositionDoCursor();
	            		   //int tag = Integer.parseInt(((EditText) layout.findViewById(R.id.editTextTag)).getText().toString());
	            		   int tag = Integer.parseInt(edt.getText().toString());
	            		   mc.addOrChangeTag(tabela, ideia, tag);	 
	            		   MainView.tagMax.setText("Tag Max: "+mc.getTagMax());
	            		   if(choose==0){
	            			   Toast.makeText(ac, "Adicionado na tag "+tag, Toast.LENGTH_LONG).show();
	            		   	   mc.retornarTodosResultados(tabela, "n", "0");
	            		   	   mc.goToPositionCursor(a-1);	            		   	   
	            		   }else{	            			   
	            			   Toast.makeText(ac, "Alterado para tag " +tag, Toast.LENGTH_LONG).show();
	            			   mc.retornarTodosResultados(tabela, "", String.valueOf(tagCarregada));
	            			   if(mc.initialResult()!=""){
	            				   mc.goToPositionCursor(a-1);	            				   
	            			   }else{
	            				   menu.clear();
	            				   checarMenu = false;
	            				   mmv.chamarMenuInicial(R.menu.menu);
	            				   mc.retornarTodosResultados(tabela, "n", "0");	            				   
	            				   Toast.makeText(ac, "Retornou porque não há mais tag "+tagCarregada, Toast.LENGTH_LONG).show();
	            			   }	            			   
	            		   }
	            		   MainView.carregarIdeia();
	            		   
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						Toast.makeText(ac, "Não foi possível adicionar a tag", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
	               }
	           })
	           .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });			    

			break;
			
		case 2:			
			tx.setText(msg[choose]);			
			alert.setView(layout)
			.setTitle(msg[choose])		
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {    
	            	   int a = mc.armazenarPositionDoCursor();   //armazenado a posição atual do cursor
	            	   tagCarregada = Integer.parseInt(((EditText) layout.findViewById(R.id.editTextTag)).getText().toString()); 	            	   
	            	   mc.retornarTodosResultados(tabela,"",String.valueOf(tagCarregada));
	            	   if(mc.initialResult()!=""){
	            		   Toast.makeText(ac, "Carregada tag "+tagCarregada, Toast.LENGTH_LONG).show();	            		   	            		              		 
	            		   //menu.clear();	          
	            		   if(!checarMenu){
	            			   mmv.chamarMenuInicial(R.menu.menutags);
	            			   checarMenu = true;
	            		   }
	            	   }else{
	            		   Toast.makeText(ac, "Não foi possível carregar a tag", Toast.LENGTH_LONG).show();
	            		   mc.retornarTodosResultados(tabela, "n", "0");
	            		   mc.goToPositionCursor(a-1);			//utilizando a posição atual do cursor
	            	   }
	            	   MainView.carregarIdeia(); 
	               }
	           })
	           .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });
			break;								    		
	    }
	    return alert.create();
	}
}
