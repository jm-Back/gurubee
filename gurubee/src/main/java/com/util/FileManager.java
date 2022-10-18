package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

public class FileManager {
	/**
	 * 파일 다운로드 메소드
	 * 
	 * @param saveFilename		서버에저장된파일명
	 * @param originalFilename	클라이언트가업로드한파일명
	 * @param pathname			서버에저장된경로
	 * @param resp				HttpServletResponse 객체
	 * @return					다운로드성공여부
	 */
	public static boolean doFiledownload(String saveFilename, String originalFilename, String pathname,
			HttpServletResponse resp) {
		boolean flag = false;

		BufferedInputStream bis = null;
		OutputStream os = null;

		try {
			originalFilename = new String(originalFilename.getBytes("euc-kr"), "8859_1");
			pathname = pathname + File.separator + saveFilename;
			File f = new File(pathname);
			if (!f.exists()) {
				return flag;
			}

			// 클라이언트에게 전송 할 문서타입을 스트림으로 설정
			resp.setContentType("application/octet-stream");

			// 파일명은 헤더에 실어서 전송
			resp.setHeader("Content-disposition", "attachment;filename=" + originalFilename);

			// 클라이언트에게 파일의 내용을 전송
			byte[] b = new byte[2048];
			bis = new BufferedInputStream(new FileInputStream(f));

			// 클라이언트에게 전송할 출력 스트림
			os = resp.getOutputStream();

			int n;
			while ((n = bis.read(b)) != -1) {
				os.write(b, 0, n);
			}
			os.flush();

			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e2) {
				}
			}

			if (os != null) {
				try {
					os.close();
				} catch (Exception e2) {
				}
			}
		}

		return flag;
	}

	/**
	 * 파일 이름 변경(파일명을 "년월일시분초나노초.확장자" 로 변경)
	 * 
	 * @param pathname		파일이저장된 경로
	 * @param filename		변경할 파일명
	 * @return				새로운파일명
	 */
	public static String doFilerename(String pathname, String filename) {
		String newname = "";

		String fileExt = filename.substring(filename.lastIndexOf("."));
		String s = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		s += System.nanoTime();
		s += fileExt;

		try {
			File f1 = new File(pathname + File.separator + filename);
			File f2 = new File(pathname + File.separator + s);
			f1.renameTo(f2);

			newname = s;
		} catch (Exception e) {
		}

		return newname;
	}

	/**
	 * 파일 삭제
	 * 
	 * @param pathname	파일이 저장된 경로
	 * @param filename	삭제할 파일명
	 * @return			파일 삭제 성공 여부
	 */
	public static boolean doFiledelete(String pathname, String filename) {
		String path = pathname + File.separator + filename;

		return doFiledelete(path);
	}

	/**
	 * 파일 삭제
	 * 
	 * @param pathname	파일이 저장된 경로 및 삭제할 파일명
	 * @return			파일 삭제 성공 여부
	 */
	public static boolean doFiledelete(String pathname) {
		try {
			File f = new File(pathname);

			if (! f.exists()) { // 파일이 없으면
				return false;
			}

			f.delete();
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
