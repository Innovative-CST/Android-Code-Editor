/*
 *  This file is part of Android Code Editor.
 *
 *  Android Code Editor is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Android Code Editor is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with Android Code Editor.  If not, see <https://www.gnu.org/licenses/>.
 */

package android.code.editor.ui.activities;

import android.code.editor.databinding.ActivityTerminalBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.MainThread;
import com.blankj.utilcode.util.KeyboardUtils;
import com.termux.terminal.TerminalEmulator;
import com.termux.terminal.TerminalSession;
import com.termux.terminal.TerminalSessionClient;
import com.termux.view.TerminalViewClient;

public class TerminalActivity extends BaseActivity
    implements TerminalSessionClient, TerminalViewClient {

  private ActivityTerminalBinding binding;
  private String cwd;
  private TerminalSession session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityTerminalBinding.inflate(getLayoutInflater());
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
      window.setStatusBarColor(Color.BLACK);
      window.setNavigationBarColor(Color.BLACK);
      getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
    setContentView(binding.getRoot());
    if (getIntent().hasExtra("path")) {
      cwd = getIntent().getStringExtra("path");
    } else {
      cwd = Environment.getExternalStorageDirectory().getAbsolutePath();
    }
    binding.terminalView.setTextSize(28);
    String[] env = {};
    String[] argsList = {};
    session =
        new TerminalSession(
            "/system/bin/sh",
            cwd,
            env,
            argsList,
            TerminalEmulator.DEFAULT_TERMINAL_CURSOR_STYLE,
            this);
    binding.terminalView.attachSession(session);
    binding.terminalView.setTerminalViewClient(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    binding = null;
  }

  @Override
  public float onScale(float scale) {
    return 1F;
  }

  @Override
  public void onSingleTapUp(MotionEvent e) {
    KeyboardUtils.showSoftInput(binding.terminalView);
  }

  @Override
  public boolean shouldBackButtonBeMappedToEscape() {
    return false;
  }

  @Override
  public boolean shouldEnforceCharBasedInput() {
    return false;
  }

  @Override
  public boolean shouldUseCtrlSpaceWorkaround() {
    return false;
  }

  @Override
  public void copyModeChanged(boolean copyMode) {}

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent e, TerminalSession session) {
    if (!session.isRunning()) {
      if (e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
        finish();
      }
    }
    return false;
  }

  @Override
  public boolean onLongPress(MotionEvent event) {
    return true;
  }

  @Override
  public boolean readControlKey() {
    return false;
  }

  @Override
  public boolean readAltKey() {
    return false;
  }

  @Override
  public boolean onCodePoint(int codePoint, boolean ctrlDown, TerminalSession session) {
    return false;
  }

  @Override
  public void onEmulatorSet() {}

  @Override
  public void logError(String tag, String message) {}

  @Override
  public void logWarn(String tag, String message) {}

  @Override
  public void logInfo(String tag, String message) {}

  @Override
  public void logDebug(String tag, String message) {}

  @Override
  public void logVerbose(String tag, String message) {}

  @Override
  public void logStackTraceWithMessage(String tag, String message, Exception e) {}

  @Override
  public void logStackTrace(String tag, Exception e) {}

  @Override
  public void onTextChanged(TerminalSession changedSession) {
    binding.terminalView.onScreenUpdated();
  }

  @Override
  public void onTitleChanged(TerminalSession changedSession) {}

  @Override
  public void onSessionFinished(TerminalSession finishedSession) {}

  @Override
  public void onBell(TerminalSession session) {}

  @Override
  public void onColorsChanged(TerminalSession session) {}

  @Override
  public void onTerminalCursorStateChange(boolean state) {}

  @Override
  public Integer getTerminalCursorStyle() {
    return TerminalEmulator.DEFAULT_TERMINAL_CURSOR_STYLE;
  }

  @Override
  public void onClipboardText(TerminalSession arg0, String arg1) {}

  @Override
  @MainThread
  public void onBackPressed() {
    if (!session.isRunning()) {
      super.onBackPressed();
    }
  }
}
