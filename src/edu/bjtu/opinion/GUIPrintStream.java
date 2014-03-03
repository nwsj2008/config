package edu.bjtu.opinion;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

class GUIPrintStream extends PrintStream {

	private Text component;
	private StringBuffer sb = new StringBuffer();
	private Display display;
	private static boolean isChange = false;

	public GUIPrintStream(OutputStream out, Text component) {
		super(out);
		this.component = component;
		display = Display.getDefault();
	}

	public void write(byte[] buf, int off, int len) {
		final String message = new String(buf, off, len);
		if (message != null && !message.equals("")) {
			sb.append(message);
			if (sb.length() > 20000) {
				sb.replace(0, 10000, "");
			}
			isChange = true;
		}

		display.asyncExec(new Runnable() {
			public void run() {
				if (isChange) {
					component.setText(sb.toString());
					component.setSelection(component.getText().length() - 1);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					isChange = false;
				}
			}
		});

	}
}
