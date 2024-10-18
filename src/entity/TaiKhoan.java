package entity;

import java.util.Objects;

import entity.constraint.VaiTro;

public class TaiKhoan {
	private String maTk;
	private String email;
	private String matKhau;
	private VaiTro vaiTro;
	private NhanVien nhanVien;

	public String getMaTk() {
		return maTk;
	}

	public void setMaTk(String maTk) {
		this.maTk = maTk;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public VaiTro getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, matKhau);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		return Objects.equals(email, other.email) && Objects.equals(matKhau, other.matKhau);
	}

	@Override
	public String toString() {
		return "TaiKhoan [maTk=" + maTk + ", email=" + email + ", matKhau=" + matKhau + ", vaiTro=" + vaiTro
				+ ", nhanVien=" + nhanVien + "]";
	}

	public TaiKhoan() {
		super();
	}

	public TaiKhoan(String maTk, String email, String matKhau, VaiTro vaiTro, NhanVien nhanVien) {
		super();
		this.maTk = maTk;
		this.email = email;
		this.matKhau = matKhau;
		this.vaiTro = vaiTro;
		this.nhanVien = nhanVien;
	}
	
	public TaiKhoan(String email, String matKhau) {
		super();
		this.email = email;
		this.matKhau = matKhau;
	}
}
