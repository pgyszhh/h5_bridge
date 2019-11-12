package com.shuiyinhuo.component.mixdev.utils.io.utils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.io.utils.util.SystemLout;

public class IOUtils {
	/**
	 * 读文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static StringBuffer readerSrcByStream(String filePath, StringBuffer... buffer) {
		StringBuffer reader = new StringBuffer();
		FileInputStream fileInputStream = getFileInputStream(filePath);

		try {

			if (!EmptyAndSizeUtils.isEmpty(fileInputStream)) {
				byte[] bytes = new byte[1024];
				int index = 0;
				while ((index = fileInputStream.read(bytes)) != -1) {
					reader.append(new String(bytes,0,index));
				}
			} else {
				reader.append("读取文件失败");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			releaseIOSrc(fileInputStream);
		}
		return reader;
	}

	/*
	 * 读文件
	 */
	public static StringBuffer readerSrcByBuffer(String path, StringBuffer... buffers) {
		StringBuffer buffer = new StringBuffer();
		FileReader reader = null;
		BufferedReader readBuff = null;
		try {
			reader = new FileReader(getFile(path));
			readBuff = new BufferedReader(reader);
			if(!EmptyAndSizeUtils.isEmpty(reader)){
				if(!EmptyAndSizeUtils.isEmpty(readBuff)){
					String index = null;
					try {
						while ((index = readBuff.readLine()) != null) {
							buffer.append(index).append("\n");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					buffer.append("读取文件失败");
				}
				
			}else{
				buffer.append("读取文件失败");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			releaseIOSrc(readBuff);
		}
		return buffer;
	}

	private static String initBufferToString(StringBuffer buffer) {
		return buffer.toString();
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 * @param buffer
	 * @return
	 */
	public static boolean writeToFileByStream(String filePath, StringBuffer buffer) {
		FileOutputStream fileOutputStream = getFileOutputStream(filePath);
		String orgStr = initBufferToString(buffer);
		byte[] bytes = orgStr.getBytes();
		try {
			if(!EmptyAndSizeUtils.isEmpty(fileOutputStream)){
				fileOutputStream.write(bytes, 0, bytes.length);
				return true;
			}else{
				return false;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			releaseIOSrc(fileOutputStream);
		}

		return false;
	}

	/**
	 * 写文件
	 * 
	 * @param path
	 * @param buffer
	 * @return
	 */
	public static boolean writeSrcToFileByWriter(String path, StringBuffer buffer) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(initFile(path,false),true);
			if(!EmptyAndSizeUtils.isEmpty(writer)){
				writer.write(initBufferToString(buffer));
				return true;
			}else{
				return false;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			releaseIOSrc(writer);
		}

		return false;
	}

	public static FileInputStream getFileInputStream(String path) {
		File file = getFile(path);
		if (!EmptyAndSizeUtils.isEmpty(file)) {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static FileOutputStream getFileOutputStream(String path) {
		File file = initFile(path, false);
		if (!EmptyAndSizeUtils.isEmpty(file)) {
			try {
				return new FileOutputStream(file,true);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			throw new NullPointerException("file is null");
		}
		return null;
	}

	public static File initFile(String path, boolean isMakeDir) {
		File file = getFile(path);
			if (file.exists()) {
				SystemLout.out("创建文件1");
				return file;
			} else {
				if (isMakeDir) {
					file.mkdirs();
				} else {
					try {
						SystemLout.out("创建文件2");
						file.createNewFile();			
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		return file;
	}

	private static File getFile(String path) {
		return new File(path);
	}

	public static void releaseIOSrc(FileWriter fw) {
		if (!EmptyAndSizeUtils.isEmpty(fw)) {
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void releaseIOSrc(BufferedReader bfer) {
		if (!EmptyAndSizeUtils.isEmpty(bfer)) {
			try {
				bfer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void releaseIOSrc(InputStream is) {
		if (!EmptyAndSizeUtils.isEmpty(is)) {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void releaseIOSrc(OutputStream os) {
		if (!EmptyAndSizeUtils.isEmpty(os)) {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void releaseIOSrc(FileOutputStream fos) {
		if (!EmptyAndSizeUtils.isEmpty(fos)) {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void releaseIOSrc(FileInputStream fis) {
		if (!EmptyAndSizeUtils.isEmpty(fis)) {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void releaseIOSrc(InputStream is, OutputStream os) {
		releaseIOSrc(is);
		releaseIOSrc(os);
	}

	public static void releaseIOSrc(InputStream is, FileOutputStream fos) {
		releaseIOSrc(is);
		releaseIOSrc(fos);
	}

	public static void releaseIOSrc(InputStream is, FileInputStream fis) {
		releaseIOSrc(fis);
		releaseIOSrc(is);
	}

	public static void releaseIOSrc(OutputStream os, FileOutputStream fos) {
		releaseIOSrc(os);
		releaseIOSrc(fos);
	}

	public static void releaseIOSrc(OutputStream os, FileInputStream fis) {
		releaseIOSrc(os);
		releaseIOSrc(fis);
	}

	public static void releaseIOSrc(FileOutputStream fos, FileInputStream fis) {
		releaseIOSrc(fos);
		releaseIOSrc(fis);
	}

	public static void releaseIOSrc(InputStream is, OutputStream os, FileOutputStream fos) {
		releaseIOSrc(is, os);
		releaseIOSrc(fos);
	}

	public static void releaseIOSrc(OutputStream os, FileOutputStream fos, FileInputStream fis) {
		releaseIOSrc(os, fos);
		releaseIOSrc(fis);
	}

	public static void releaseIOSrc(InputStream is, OutputStream os, FileOutputStream fos, FileInputStream fis) {
		releaseIOSrc(is, os, fos);
		releaseIOSrc(fis);
	}


/*
	private IOUtils() {
		throw new AssertionError();
	}
*/


	/**
	 * Close closable object and wrap {@link IOException} with {@link
	 * RuntimeException}
	 *
	 * @param closeable closeable object
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				throw new RuntimeException("IOException occurred. ", e);
			}
		}
	}


	/**
	 * Close closable and hide possible {@link IOException}
	 *
	 * @param closeable closeable object
	 */
	public static void closeQuietly(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				// Ignored
			}
		}
	}


	/**
	 *保存文本
	 * @param fileName  文件名字
	 * @param content  内容
	 * @param append  是否累加
	 * @return  是否成功
	 */
	public static boolean saveTextValue(String fileName, String content, boolean append) {

		try {
			File textFile = new File(fileName);
			if (!append && textFile.exists()) textFile.delete();

			FileOutputStream os = new FileOutputStream(textFile);
			os.write(content.getBytes("UTF-8"));
			os.close();
		} catch (Exception ee) {
			return false;
		}

		return true;
	}


	/**
	 * 删除目录下所有文件
	 * @param Path    路径
	 */
	public static void deleteAllFile(String Path) {

		// 删除目录下所有文件
		File path = new File(Path);
		File files[] = path.listFiles();
		if (files != null) {
			for (File tfi : files) {
				if (tfi.isDirectory()) {
					System.out.println(tfi.getName());
				}
				else {
					tfi.delete();
				}
			}
		}
	}
}
