package lopicost.spd.utils;

import lopicost.config.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Operaciones sobre ficheros.
 */
public class FileUtil {
	
	
	/** Crea fichero a partir de InputStream
	 * @param name nombre del fichero
	 * @param in InputStream origen
	 * @param location destino físico
	 * @throws Exception FileNotFoundException, IOException

	 */
	public static String saveInputStream(String name, InputStream in, String location) throws Exception {
		String newFileName = location + File.separator + name;
		InputStream fis = null;
		FileOutputStream fos = null;
		int count;
		int bufferSize = 8192;
		byte data[] = new byte[ bufferSize ];
		
		
		fis = (InputStream)in;
			
		if (fis!=null) {
			fos = new FileOutputStream(newFileName);
			while( (count = fis.read( data, 0, bufferSize ) ) != -1 )
			{
				fos.write( data, 0, count );
			}
			fos.close();
			fis.close();
		}
		
		fis = null;
		fos = null;
		
		return newFileName;
	}

	/** Copia fichero "in" sobre fichero "out"
	 * @param in origen
	 * @param out destino
	 * @throws Exception  no existe no permisos RW */
	public static void copyFile(File in, File out) throws Exception {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		;
		fis.close();
		fos.close();
	}

	/** Mueve fichero
	 * @param pre origen
	 * @param post destino
	 * @throws Exception no existe no permisos RW */
	public static void moveFile(File pre, File post) throws Exception {
		copyFile(pre, post);
		pre.delete();
	}

	/** Calcular el tamaño de los ficheros de un directorio
	 * Si recurse es true indica que el calculo se haga recursivamente sumando los tamanyos
	 * de los subdirectorios encontrados
	 * @param dir objeto File que representa el directorio en cuestion
	 * @param recurse (true) explorar subdirectorios (false) no explorarlos
	 * @return suma del tamanyo de los ficheros contenidos en el dire (en bytes) */
	public static long getDirSize(File dir, boolean recurse) {
		long aux = 0;
		File[] dirFiles = dir.listFiles();
		for (int i = 0; i < dirFiles.length; i++) {
			aux = aux + dirFiles[i].length();
			if (dirFiles[i].isDirectory() && recurse) aux = aux + getDirSize(dirFiles[i], true);
		}
		;
		return aux;
	}

	/** Mètodo que retorna la lista de ficheros de un directorio concreto.
	 * Útil para tener una lista de los mensajes de un buzón, por ejemplo para 
	 * tener constancia de los recursos de un aula en un area de ficheros
	 * @param dir objeto File que representa el directorio en cuestion
	 * @return Vector de Strings, Lista de ficheros que contiene (no de los directorios)*/
	public static java.util.Vector getFilesDir(String path) {
		File dir = new File(path);
		String fitxer = "";
		java.util.Vector files = new java.util.Vector();
		File[] v_files = dir.listFiles();
		if (v_files != null && (v_files.length > 0)) {
			for (int i = 0; i < v_files.length; i++) {
				File v_file = v_files[i];
				fitxer = v_file.toString();
				fitxer = StringUtil.replace(path + "/", "", fitxer);
				if (!(fitxer.substring(0, 1).equals(","))) files.add(fitxer);
			}
		}
		return files;
	}

	/** Mètodo que retorna el nombre del fichero sin extensión
	 * @param dir String File que representa el ficheros en cuestion
	 * @return String fichero sin extensión*/
	public static String getFilesSinExt(String fitxer) {
		String res = "";
		int quants = StringUtil.charCount(fitxer, '.');
		if (quants == 0) res = fitxer;
		if (quants == 1) {
			int on = fitxer.lastIndexOf('.');
			res = fitxer.substring(0, on);
		}
		return res;
	}

	/** 
	 * A partir d'un path indica si existeix un fitxer o no.
	 * 
	 * @param psPath el path del fitxer
	 * @return true si existeix, false otherwise
	 * @throws Exception
	 */
	public static boolean existFile(String psPath) throws Exception {

		File _fFile = new File(psPath);
		return _fFile.exists();
	}

	public static boolean deleteDir(File dir) {
		// to see if this directory is actually a symbolic link to a directory,
		// we want to get its canonical path - that is, we follow the link to
		// the file it's actually linked to
		File candir;
		try {
			candir = dir.getCanonicalFile();
		} catch (IOException e) {
			return false;
		}

		// a symbolic link has a different canonical path than its actual path,
		// unless it's a link to itself
		if (!candir.equals(dir.getAbsoluteFile())) {
			// this file is a symbolic link, and there's no reason for us to
			// follow it, because then we might be deleting something outside of
			// the directory we were told to delete
			return false;
		}

		// now we go through all of the files and subdirectories in the
		// directory and delete them one by one
		File[] files = candir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];

				// in case this directory is actually a symbolic link, or it's
				// empty, we want to try to delete the link before we try
				// anything
				boolean deleted = !file.delete();
				if (deleted) {
					// deleting the file failed, so maybe it's a non-empty
					// directory
					if (file.isDirectory()) deleteDir(file);

					// otherwise, there's nothing else we can do
				}
			}
		}

		// now that we tried to clear the directory out, we can try to delete it
		// again
		return dir.delete(); 
	}

	/**
	 * Ens llegirà el contingut d'un fitxer de configuració i ens retornarà
	 * un treemap amb aquests valors.<br>
	 * El fitxer de configuració contindrà linies al estil clau=valor,
	 * les linies que comencin per # seràn comentaris.
	 * 
	 * @param p_fitxer: String que contindrà el nom del fitxer de configuració.
	 * @return
	 * @throws Exception

	 */
	public static TreeMap FileToTreeMap(String p_fitxer) throws Exception {
		try {
			TreeMap v_treemap = new TreeMap();
			File v_fitxer = new File(p_fitxer);
			FileInputStream v_stream = new FileInputStream(v_fitxer);
			long v_len = v_fitxer.length();
			byte[] v_filedata = new byte[(int) v_len];
			v_stream.read(v_filedata);
			v_stream.close();
			String v_dades = new String(v_filedata);
			StringTokenizer v_st = new StringTokenizer(v_dades, "\r\n");
			while (v_st.hasMoreTokens()) {
				String v_linia = v_st.nextToken();
				if (!v_linia.startsWith("#") && !v_linia.startsWith(";")
						&& v_linia.indexOf("=") > 0) {
					String v_key = v_linia.substring(0, v_linia.indexOf("="));
					String v_value;
					if (v_linia.indexOf("=") < v_linia.length())
						v_value = v_linia.substring(v_linia.indexOf("=") + 1);
					else
						v_value = "";
					v_treemap.put(v_key, v_value != null ? v_value : "");
				}
			}
			return v_treemap;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
	 * Indica si existe o no un fichero dada su url absoluta
	 * @param theUrl pasar la variable con "/" delante
	 * @return
	 */
	public static boolean resourceExists( String theUrl )
	{
		try {
			URL url = new URL( theUrl );
			URLConnection conn = url.openConnection();
			conn.getInputStream().close();
			Logger.get().debug(theUrl + " existeix");
			return true;
		} catch (Exception e) {
			Logger.get().debug(theUrl + " no existeix");
			return false;
		}
	}
	


	public static void renameFile(String readpath, String oldNameFile, String newNamefile)
	{
		File file = new File( readpath+File.separatorChar+oldNameFile );
		
	    // File (or directory) with new name
	    File file2 = new File( readpath+File.separatorChar+newNamefile);
	    
	    // Rename file (or directory)
	    boolean success = file.renameTo(file2);
	    if (!success) {
	    	Logger.get().error("renameFile - Origen:"+readpath+File.separatorChar+oldNameFile);
	    	Logger.get().error("renameFile - Destino:"+readpath+File.separatorChar+newNamefile);	    	
	    }
	}
	
	
    
}
