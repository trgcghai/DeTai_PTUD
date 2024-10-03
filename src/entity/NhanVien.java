package entity;

import java.time.LocalDate;
import java.util.Objects;

import entity.constraint.GioiTinh;

public class NhanVien {

	private int maNV;
	private String tenNV;
	private LocalDate ngaysinh;
	private String diachi;
	private GioiTinh gioitinh;
	private String sdt;
	private TaiKhoan taikhoan;
	

	public NhanVien(int maNV, String tenNV, LocalDate ngaysinh, String diachi, GioiTinh gioitinh,
			String sdt, TaiKhoan taikhoan) {
		super();
		this.maNV=maNV;
		this.tenNV=tenNV;
		this.ngaysinh=ngaysinh;
		this.diachi=diachi;
		this.gioitinh=gioitinh;
		this.sdt=sdt;
		this.taikhoan=taikhoan;
	}
	
	public NhanVien(int maNV, String tenNV, LocalDate ngaysinh, String diachi, GioiTinh gioitinh,
			String sdt) {
		super();
		this.maNV=maNV;
		this.tenNV=tenNV;
		this.ngaysinh=ngaysinh;
		this.diachi=diachi;
		this.gioitinh=gioitinh;
		this.sdt=sdt;
	}

	public NhanVien(int maNV) {
		super();
		this.maNV=maNV;
	}
	
	public NhanVien(int maNV, String tenNV) {
		super();
		this.maNV=maNV;
		this.tenNV=tenNV;
	}

	public int getMaNV() {
		return maNV;
	}

	public void setMaNV(int maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public LocalDate getNgaysinh() {
		return ngaysinh;
	}

	public void setNgaysinh(LocalDate ngaysinh) {
		this.ngaysinh = ngaysinh;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public GioiTinh getGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(GioiTinh gioitinh) {
		this.gioitinh = gioitinh;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public TaiKhoan getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(TaiKhoan taikhoan) {
		this.taikhoan = taikhoan;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return maNV == other.maNV;
	}

}