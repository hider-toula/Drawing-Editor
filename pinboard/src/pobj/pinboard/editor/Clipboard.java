package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard {
	
	// this should work
	private static Clipboard clipboard;
	private List<Clip> clips;
	private List<ClipboardListener> listeners;

	private Clipboard() {
	}

	public List<Clip> copyFromClipboard() {
		List<Clip> list = new ArrayList<Clip>();
		for (Clip clip : clipboard.clips) {
			list.add(clip.copy());
		}
		return list;
	}

	public void copyToClipboard(List<Clip> clips) {
		for (Clip clip : clips) {
			clipboard.clips.add(clip.copy());
		}
		for (ClipboardListener clipl : clipboard.listeners) {
			clipl.clipboardChanged();
		}

	}

	public void clear() {
		clipboard.clips = new ArrayList<Clip>();
		for (ClipboardListener clipl : clipboard.listeners) {
			clipl.clipboardChanged();
		}
		
	}

	public boolean isEmpty() {
		return clipboard.clips.size() == 0;
	}

	public void addListener(ClipboardListener listener) {
		clipboard.listeners.add(listener);
	}

	
	public void removeListener(ClipboardListener listener) {
		clipboard.listeners.remove(listener);
	}

	public static Clipboard getInstance() {
		if (clipboard == null) {
			clipboard = new Clipboard();
			clipboard.clips = new ArrayList<Clip>();
			clipboard.listeners = new ArrayList<ClipboardListener>();
			return clipboard;
		} else {
			return clipboard;
		}

	}

}
