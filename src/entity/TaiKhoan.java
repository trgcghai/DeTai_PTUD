package entity;

public class TaiKhoan {
	private String maTk;
	private String email;
	private String matKhau;
	private String vaiTro;
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

	public String getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(String vaiTro) {
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maTk == null) ? 0 : maTk.hashCode());
		return result;
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
		if (maTk == null) {
			if (other.maTk != null)
				return false;
		} else if (!maTk.equals(other.maTk))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TaiKhoan [maTk=" + maTk + ", email=" + email + ", matKhau=" + matKhau + ", vaiTro=" + vaiTro
				+ ", nhanVien=" + nhanVien + "]";
	}

	public TaiKhoan() {
		super();
	}

	public TaiKhoan(String maTk, String email, String matKhau, String vaiTro, NhanVien nhanVien) {
		super();
		this.maTk = maTk;
		this.email = email;
		this.matKhau = matKhau;
		this.vaiTro = vaiTro;
		this.nhanVien = nhanVien;
	}
}
