package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
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
	    tx = (TextView)layout.findViewById(R.id.textViewTag);
	    //String msg = "";
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
	            		   int tag = Integer.parseInt(((EditText) layout.findViewById(R.id.editTextTag)).getText().toString());  	            		   
	            		   mc.addOrChangeTag(tabela, ideia, tag);	  
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
	            		   menu.clear();	           					  
	            		   mmv.chamarMenuInicial(R.menu.menutags);	
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
