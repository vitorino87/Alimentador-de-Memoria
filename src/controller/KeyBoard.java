package controller;

import android.inputmethodservice.InputMethodService;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodSession;
import android.view.inputmethod.InputMethodSubtype;

public class KeyBoard extends InputMethodService implements InputMethod, InputType{

	KeyBoard(){
		super();
	}
	public boolean onKeyUp(int keyCode, KeyEvent event){
		
		return false;	
	}
	@Override
	public void attachToken(IBinder token) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void bindInput(InputBinding binding) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unbindInput() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void startInput(InputConnection inputConnection, EditorInfo info) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void restartInput(InputConnection inputConnection, EditorInfo attribute) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void createSession(SessionCallback callback) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSessionEnabled(InputMethodSession session, boolean enabled) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void revokeSession(InputMethodSession session) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showSoftInput(int flags, ResultReceiver resultReceiver) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hideSoftInput(int flags, ResultReceiver resultReceiver) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void changeInputMethodSubtype(InputMethodSubtype subtype) {
		// TODO Auto-generated method stub
		
	}
}
