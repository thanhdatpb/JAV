package vuong;

import java.util.*;

public class App {
	static void println(Object obj) { System.out.println(obj); }
	public static void main(String[] args) {
		handler hd = new handler();
		hd.KetNoiCSDL();
		println("Hien 2 Bang DTB: 1. NhomThietBi va 2. ThietBi\nBan Muon Hien Thi Bang Nao ?\nNhap So: ");
		//Scanner sc = new Scanner(System.in);
		//int type = sc.nextInt();
		//hd.HienThi(type);

		List<String> cot = new ArrayList<String>();
		List<String> data = new ArrayList<String>();
		
		cot.add("TenTB"); cot.add("Gia");
		data.add("Iphone XS Max"); data.add("10000000");
		hd.update("ThietBi", "DT01", cot, data);
	}

}
