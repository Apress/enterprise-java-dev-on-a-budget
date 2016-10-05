package com.ejdoab.tcms.admin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Brian Sam-Bodden 
 */
public class Resources {
	private static Log log = LogFactory.getLog(Resources.class);

	private static String[] _extensions = { "gif", "jpg", "jpeg" };
	private static ImageRegistry _images;
	private static Display _display;

	private static final URL _iconsFolder =
		Resources.class.getResource("/media/icons/");
	private static final URL _imagesFolder =
		Resources.class.getResource("/media/");
		

	private static Map _imageFolders = new HashMap();

	// static initializer
	static {
		_imageFolders.put("icons", _iconsFolder);
		_imageFolders.put("image", _imagesFolder);		
	}

	/**
	 * Non-instantiable static library
	 */
	private Resources() {
	}

	/**
	 * SWT Display objec used to initialize the
	 * Image registry and also as a central location
	 * to get the application's main Display object is needed
	 * 
	 * @param display
	 */
	public static void setDisplay(Display display) {
		_display = display;
		initializeImageRegistry();
	}

	public static Display getDisplay() {
		return _display;
	}

	private static void initializeImageRegistry() {
		if ((_images == null) && (_display != null)) {
			_images = new ImageRegistry(_display);
			// loop throught the image directory and add all images to the registry
			// take the image name sans extension as the key for the registry

			Set keys = _imageFolders.keySet();
			Iterator i = keys.iterator();

			// loop through the different image folders and add the images to the registry
			for (int index = 0, n = keys.size(); index < n; index++) {
				String key = (String) i.next();
				URL location = (URL) _imageFolders.get(key);

				// Convert a URL to a URI
				URI uri = null;
				try {
					uri = new URI(location.toString());
				} catch (URISyntaxException e) {
				}

				if (uri != null) {
					File directory = new File(uri);

					// if it is a directory go throught all the files of a given type and add them
					// to the registry
					if (directory.isDirectory()) {
						String[] files =
							FileUtils.getFilesFromExtension(
								directory.getAbsolutePath(),
								_extensions);
						for (int j = 0; j < files.length; j++) {
							File file = FileUtils.getFile(files[j]);
							
							try {
								ImageDescriptor desc = ImageDescriptor.createFromURL(file.toURL());
								String registryEntry =
									key + "_" + FileUtils.removeExtension(file.getName());
								log.info("adding image file " + file.getName() + " as " + registryEntry);									
								System.out.println("adding image file " + file.getName() + " as " + registryEntry);
								_images.put(registryEntry, desc);
																
							} catch (MalformedURLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						// if the 'single' file is of a given type add it to the registry
						System.out.println("single file");
					}
				}
			}

		}
	}
	
	/**
	 * @return
	 */
	public static ImageRegistry getImageRegistry() {
		return _images;
	}	

	public static void main(String[] args) {
		Display display = new Display();

		Resources.setDisplay(display);
		Resources.initializeImageRegistry();

		final Shell shell = new Shell(display);
		shell.setSize(640, 480);
		shell.setLayout(new FillLayout());

		Label label = new Label(shell, SWT.NONE);
		
		Image image = Resources.getImageRegistry().get("icons_application");
		
		if (image != null) {
			label.setImage(image);
		}
		
		label.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event e) {
				shell.dispose();
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}



}
