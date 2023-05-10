package android.code.editor.utils;

import java.util.Observable;

public class ThemeObservable extends Observable {
	public void notifyThemeChanged() {
		setChanged();
		notifyObservers("O");
	}
}
