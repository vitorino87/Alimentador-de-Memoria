package view;

import com.example.alimentadordememoria.R;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TelaTemplate extends Activity implements OnTouchListener, OnGestureListener{
	GestureDetector gestureDetector;	
//	@Override
//	public void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.tela1);					
//	}
	
	/**
	 * M�todo para deslizar o dedo na tela
	 * e obter alguma a��o
	 * @author Tiago Vitorino
	 * @since 16/02/2019
	 */
	@Override
	public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float velocityX, float velocityY) {
		if(motionEvent1 == null){
			return false;
		}else{
			//movimento da direita para esquerda
			if (motionEvent1.getX() - motionEvent2.getX() > 50) {
				controller.TelaAux.moverDireitaParaEsquerda();
				return true;
			}
			//movimento da esquerda para direita
			if (motionEvent2.getX() - motionEvent1.getX() > 50) {
				controller.TelaAux.moverEsquerdaParaDireita();
				return true;
			} else {
				return true;
			}
		}
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**Explica��o:
	*m�todo para criar o menu
	*No KitKat ele cria aqueles 3 pontinhos que serve para acessar o menu.
	*Para funcionar � necess�rio criar uma pasta chamada menu em /res
	*E adicionar um .xml do tipo menu
	*E por fim adicionar um item ou um grupo 
	*Neste projeto foi adicionado um item.
	*@author Tiago Vitorino
	*@since 16/02/2019
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		//getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}		
}
