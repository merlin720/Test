package com.fh.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static void main(String[] args) {
		String dirName = "d:/FH/topic/";// 创建目录
		FileUtil.createDir(dirName);
	}

	/**
	 * 创建目录
	 * 
	 * @param destDirName
	 *            目标目录名
	 * @return 目录创建成功返回true，否则返回false
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建单个目录
		if (dir.mkdirs()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param filePathAndName
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 读取到字节数组1
	 * 
	 * @param filePath
	 *            //路径
	 * @throws IOException
	 */
	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		fi.close();
		return buffer;
	}

	/**
	 * 读取到字节数组2
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	/**
	 * 读取到字节数组3
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}

		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray3(String filePath) throws IOException {

		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(filePath, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
					fc.size()).load();
			//System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<String> getFilesInPath(String path){
		return getFilesInPath(path,".dat");
	}
	public static List<String> list = new ArrayList<String>();
	public static List<String> getFilesInPath(String path,String endWithStr) {

		File file = new File(path);
		File[] array = file.listFiles();
		for (int i = 0; i < array.length; i++) {

			if (array[i].isFile()) {
				String name = array[i].getName();
				if (name.endsWith(endWithStr) ) {
					if (".ini".equals(endWithStr)) {
						list.add(array[i].getPath());
					} else {
						list.add(array[i].getName());
					}
				}else if (".ini".equals(endWithStr)) {
					if (name.endsWith(".txt")){
						list.add(array[i].getPath());
					}
				}
//				// only take file name   
				System.out.println("^^^^^" + array[i].getName());
//				// take file path and name   
//				System.out.println("#####" + array[i]);
//				// take file path and name   
//				System.out.println("*****" + array[i].getPath());
			} else if (array[i].isDirectory()){
				getFilesInPath(array[i].getPath(),endWithStr);
			}
		}

		return list;
	}
	public static void copyFile(File sourcefile, File targetFile) throws IOException {

		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourcefile);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream out = new FileOutputStream(targetFile);
		BufferedOutputStream outbuff = new BufferedOutputStream(out);

		// 缓冲数组
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = input.read(b)) != -1) {
			outbuff.write(b, 0, len);
		}

		//关闭文件
		outbuff.close();
		input.close();

	}

	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {

		// 新建目标目录
		(new File(targetDir)).mkdirs();

		// 获取源文件夹当下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(targetDir + File.separator + sourceFile.getName());
				copyFile(sourceFile, targetFile);

			}

			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + File.separator + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + File.separator + file[i].getName();

				copyDirectiory(dir1, dir2);
			}
		}

	}



	public static void deleteFile(File file) {
		// 判断传递进来的是文件还是文件夹,如果是文件,直接删除,如果是文件夹,则判断文件夹里面有没有东西
		if (file.isDirectory()) {
			// 如果是目录,就删除目录下所有的文件和文件夹
			File[] files = file.listFiles();
			// 遍历目录下的文件和文件夹
			for (File f : files) {
				// 如果是文件,就删除
				if (f.isFile()) {
					System.out.println("已经被删除的文件:" + f);
					// 删除文件
					f.delete();
				} else if (file.isDirectory()) {
					// 如果是文件夹,就递归调用文件夹的方法
					deleteFile(f);
				}
			}
			// 删除文件夹自己,如果它低下是空的,就会被删除
			System.out.println("已经被删除的文件夹:" + file);
			file.delete();
		}

		// 如果是文件,就直接删除自己
		System.out.println("已经被删除的文件:" + file);
		file.delete();

	}

}