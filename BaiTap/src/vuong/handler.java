package vuong;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.*;

public class handler {
	
	public Connection cn;
	
	public void KetNoiCSDL(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            println("Nap Driver Thanh Cong !");
       	  	String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=QLTB;trustServerCertificate=true";
            String user = "sa";
            String pass = "123456";
			cn = DriverManager.getConnection(url, user, pass);
        }
        catch (Exception e) { e.printStackTrace(); }
    }	
	void println(Object obj) { System.out.println(obj); }
	
    public void HienThi(int type)
    {
        if(type == 1) {
        	HienThiNhomTB();
        }
        else if(type == 2) {
        	HienThiTB();
        }
        else { println("Lua chon khong phu hop !"); return;}
    }
    void HienThiNhomTB() {
    	try{
            String query = "select * from NhomThietBi";
            PreparedStatement pr = cn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                println(rs.getString("MaNhom") + " | " + rs.getString("TenNhom"));
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
    void HienThiTB() {
    	try{
            String query = "select * from ThietBi";
            PreparedStatement pr = cn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                println(rs.getString("MaNhom") + " | " + rs.getString("MaTB") + " | " + rs.getString("TenTB") + " | " + rs.getString("NgaySanXuat") + " | " + rs.getString("Gia"));
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }
    
    public String RandomMaNhom() {
    	try{
    		List<String> list = new ArrayList<String>();
    		Random rand = new Random();
            String query = "select * from NhomThietBi";
            PreparedStatement pr = cn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
               list.add(rs.getString("MaNhom"));
            }
            if(!list.isEmpty()) { 
            	int n = rand.nextInt(list.size());
            	return list.get(n);
            }
            
        }
        catch (Exception e) { e.printStackTrace(); }
    	return "";
    }
    
    public int plus(String str) {
        str = str.replaceAll("[^0-9,-\\.]", ",");
        String[] item = str.split(",");
        for (int i = 0; i < item.length; i++) {
            try {
                Double.parseDouble(item[i]);
                int n = Integer.parseInt(item[i]);
                n++;
                return n;
            } catch (NumberFormatException e) {}
        }
        return 0;
    }
    
    public String RandomMaTB(String maNhom) {
		String s = "";
    	try{
            String query = "select top 1 MaTB from ThietBi where MaNhom = '" + maNhom + "' order by MaTB desc;";
            PreparedStatement pr = cn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                String n = rs.getString("MaTB");
                int i = plus(n);
                if(i < 10) {
                	s = "0" + i;
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    	return s;
    }
    
    public String RandomTenTB(String maNhom) {
    	switch(maNhom)
    	{
		case "MT":
		{
			String[] list = { "Máy tính xách tay", "Máy tính để bàn", "Máy tính bảng", "Máy tính Server", "Siêu máy tính" };
			Random rand = new Random();
			int n = rand.nextInt(list.length);
			return list[n];
		}
		case "DT":
		{
			String[] list = { "Iphone 7", "Iphone 8", "Iphone 9", "Iphone 10", "Samsung Galaxy S12", "Nokia N14" };
			Random rand = new Random();
			int n = rand.nextInt(list.length);
			return list[n];
		}
		case "TV":
		{
			String[] list = { "Sony 55 inch Full HD", "Sony 55 inch 4K", "LG 55 inch Full HD", "LG 65 inch 4K" };
			Random rand = new Random();
			int n = rand.nextInt(list.length);
			return list[n];
		}
    	}
    	return "";
    }
   
    public String RandomNgaySX() {    	
    	GregorianCalendar gc = new GregorianCalendar();
        int year = 2013;
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);    	
    	return gc.get(gc.YEAR) + "/" + (gc.get(gc.MONTH) + 1) + "/" + gc.get(gc.DAY_OF_MONTH);
    }
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    public int GiaTB() {
    	Random rand = new Random();
    	return rand.nextInt(5000000, 20000000);
    }
    
    public boolean CheckRows(String bang, String tencot, String data){
        try{
            String query = "select * from "+ bang +" where " + tencot + " = '" + data +"';";
            PreparedStatement pr = cn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    public void insert(String bang, String tencot ,String data) {
    	try{
    		if(!CheckRows(bang, tencot, data)) {
    		String query = "insert into "+ bang +" values (" + data +");";
    		Statement statement = cn.createStatement();
    		statement.executeUpdate(query);
    		println("Nhập thành công !");
    	}
    	else {
    		println("Dữ liệu đã tồn tại !");
    		return;
    	}
    		}
    catch (Exception e) { e.printStackTrace(); }
    }
    
    public void delete(String bang, String tencot,String data) {
    	try{
    		if(CheckRows(bang, tencot, data)) {
    		String query = "delete from "+ bang + " where " + tencot + " = '" + data +"';";
    		Statement statement = cn.createStatement();
    		statement.executeUpdate(query);
    		println("Xóa dữ liệu thành công !");
    	}
    	else {
    		println("Dữ liệu không tồn tại !");
    		return;
    	}
    		}
    catch (Exception e) { e.printStackTrace(); }
    }
	
    public void find(String str) {
    	try{
            String query = "select * from ThietBi where MaTB like '%"+ str +"%' or TenTB like '%"+ str +"%'";
            PreparedStatement pr = cn.prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                println(rs.getString("MaNhom") + " | " + rs.getString("MaTB") + " | " + rs.getString("TenTB") + " | " + rs.getString("NgaySanXuat") + " | " + rs.getString("Gia"));
            }
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void update(String bang, String matb, List<String> cot ,List<String> data) {
    	try{
    		if(cot.size() > data.size() || data.size() > cot.size()) {
    			println("Cau truy van bi sai ! Vui kiem tra du lieu !");
    			return;
    		}
    		else {
    			String updates = "";
    			for(int i = 0; i < cot.size(); i++)
    			{
    				updates += cot.get(i) + " = '"+ data.get(i) + "',";
    			}
    			String query = "update " +  bang + " set "+ updates +" where MaTB = '" + matb + "'";
    			Statement statement = cn.createStatement();
        		statement.executeUpdate(query);
    		}
            
        }
        catch (Exception e) { e.printStackTrace(); }
    }
    
    
}
