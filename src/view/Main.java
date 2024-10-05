package view;

import javax.swing.JLabel;

import controller.Database;
import dao.NhanVien_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

public class Main {
	public static void main(String[] args) {
//		new LoginFrame().setVisible(true);
//		new MainFrame("MinhDat").setVisible(true);
		
//		new TaiKhoanFrame("MinhDat").setVisible(true);
//		
//		new UngVienTestFrame("MinhDat").setVisible(true);
		
//		new NhaTuyenDungFrame("MinhDat").setVisible(true);
//		new TinTuyenDungFrame("MinhDat").setVisible(true);
//		new HopDongFrame("Minh Dat").setVisible(true);
		
//============================================================================================
//		new LoginFrame().setVisible(true);
//		new MainFrame("MinhDat").setVisible(true);
		
//		new NhanVienFrame("MinhDat").setVisible(true);
//		Nhân viên chưa có tài khoản mới sử dụng chức năng cấp tài khoản
		
//		new TaiKhoanFrame("MinhDat").setVisible(true);
		
//		new UngVienFrame("MinhDat").setVisible(true);
		
//		new HoSoFrame("MinhDat").setVisible(true);
//		Chức năng cập nhật hồ sơ: hồ sơ đã nộp chỉ cập nhật trạng thái hồ sơ,
//		còn hồ sơ chưa nộp thì trạng thái hồ sơ là Chưa nộp
//============================================================================================		

		new ThongKeTinTuyenDungFrame("MinhDat").setVisible(true);
	}
}
