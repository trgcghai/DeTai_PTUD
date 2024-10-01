package entity;

import java.util.Objects;

import entity.constraint.VaiTro;

public class TaiKhoan {
	private int maTK;
	private String email;
	private String matkhau;
	private VaiTro vaitro;
	
	public TaiKhoan() {
		
	}
	
	public TaiKhoan(int maTK) {
		this.maTK=maTK;
	}
	
	public TaiKhoan(String email, String matkhau) {
		this.email=email;
		this.matkhau=matkhau;
	}
	
	public TaiKhoan(int maTK, String email, String matkhau, VaiTro vaitro) {
		this.maTK=maTK;
		this.email=email;
		this.matkhau=matkhau;
		this.vaitro=vaitro;
	}
	
	public int getMaTK() {
		return maTK;
	}
	public void setMaTK(int maTK) {
		this.maTK = maTK;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMatkhau() {
		return matkhau;
	}
	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}
	
	public VaiTro getVaitro() {
		return vaitro;
	}
	public void setVaitro(VaiTro vaitro) {
		this.vaitro = vaitro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, matkhau);
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
		return Objects.equals(email, other.email) && Objects.equals(matkhau, other.matkhau);
	}
	
}
