package controller;

import android.text.Editable;
import android.text.TextWatcher;

//classe para lidar com o EditText, ao digitar alguma coisa nele.

public class EditWatcher extends view.MainView2 implements TextWatcher{

	//String text="";
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		//text = txtIdeia.getText().toString();
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		//String text2 = s.toString();
		//int a = txtIdeia.getLineCount();//add if
		//int index = text2.length()-1;
		//if(text2.charAt(index)=='\n'){
		//	txtIdeia.setText(text);
		//	a = txtIdeia.getLineCount();
		//}
		//while(a!=1){			
		//	float size = txtIdeia.getTextSize();
		//	txtIdeia.setTextSize((float) (size-1));
		//	a = txtIdeia.getLineCount();
		//}
	}

}
