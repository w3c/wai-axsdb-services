package org.w3c.wai.accessdb.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.core.MultivaluedMap;

public class InOutUtils {
	/**
	 * This method makes a "deep clone" of any Java object it is given.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	 public static Object deepClone(Object object) throws IOException, ClassNotFoundException {
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(baos);
	     oos.writeObject(object);
	     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	     ObjectInputStream ois = new ObjectInputStream(bais);
	     return ois.readObject();
	   
	 }
	/**
	 * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
	 * name="file"; filename="filename.extension"] }
	 **/
	// get uploaded filename, is there a easy way in RESTEasy?
	public static String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition")
				.split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	public static File createFile(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public static File createFolder(String path) throws IOException {
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = theDir.mkdir();
			if (result) {
				System.out.println("DIR created");
			}

		}
		return theDir;
	}

	// save to somewhere
	public static void writeFile(byte[] content, String filename)
			throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(content);
		fop.flush();
		fop.close();

	}

	public static File copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to "
						+ dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
		return dest;
	}

	public static void deleteFolderContents(File folder) {
		if (folder != null && folder.listFiles() != null) {
			File[] files = folder.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolderContents(f);
				} else {
					f.delete();
				}
			}
		}
	}

	public static boolean makeDir(String path) {
		return (new File(path)).mkdirs();

	}

	/** Read the contents of the given file. */
	public static String readFile(String path) throws IOException {
		StringBuilder text = new StringBuilder();
		String NL = System.getProperty("line.separator");
		Scanner scanner = new Scanner(new FileInputStream(path), "utf-8");
		try {
			while (scanner.hasNextLine()) {
				text.append(scanner.nextLine() + NL);
			}
		} finally {
			scanner.close();
		}
		return text.toString();
	}
	public static void zip(File directory, File zipfile) throws IOException {
	    URI base = directory.toURI();
	    Deque<File> queue = new LinkedList<File>();
	    queue.push(directory);
	    OutputStream out = new FileOutputStream(zipfile);
	    Closeable res = out;
	    try {
	      ZipOutputStream zout = new ZipOutputStream(out);
	      res = zout;
	      while (!queue.isEmpty()) {
	        directory = queue.pop();
	        for (File kid : directory.listFiles()) {
	          String name = base.relativize(kid.toURI()).getPath();
	          if (kid.isDirectory()) {
	            queue.push(kid);
	            name = name.endsWith("/") ? name : name + "/";
	            zout.putNextEntry(new ZipEntry(name));
	          } else {
	            zout.putNextEntry(new ZipEntry(name));
	            copy(kid, zout);
	            zout.closeEntry();
	          }
	        }
	      }
	    } finally {
	      res.close();
	    }
	  }
	public static void unzip(File zipfile, File directory) throws IOException {
	    ZipFile zfile = new ZipFile(zipfile);
	    Enumeration<? extends ZipEntry> entries = zfile.entries();
	    while (entries.hasMoreElements()) {
	      ZipEntry entry = entries.nextElement();
	      File file = new File(directory, entry.getName());
	      if (entry.isDirectory()) {
	        file.mkdirs();
	      } else {
	        file.getParentFile().mkdirs();
	        InputStream in = zfile.getInputStream(entry);
	        try {
	          copy(in, file);
	        } finally {
	          in.close();
	        }
	      }
	    }
	  }

	
	private static void copy(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    while (true) {
	      int readCount = in.read(buffer);
	      if (readCount < 0) {
	        break;
	      }
	      out.write(buffer, 0, readCount);
	    }
	  }

	  private static void copy(File file, OutputStream out) throws IOException {
	    InputStream in = new FileInputStream(file);
	    try {
	      copy(in, out);
	    } finally {
	      in.close();
	    }
	  }

	  private static void copy(InputStream in, File file) throws IOException {
	    OutputStream out = new FileOutputStream(file);
	    try {
	      copy(in, out);
	    } finally {
	      out.close();
	    }
	  }
}
