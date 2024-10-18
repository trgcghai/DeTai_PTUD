package entity;

public class NhaTuyenDung {
	private String maNTD;
	private String tenNTD;
	private String email;
	private String logo;
	private String soDienThoai;
	private String diaChi;

	public String getMaNTD() {
		return maNTD;
	}

	public void setMaNTD(String maNTD) {
		this.maNTD = maNTD;
	}

	public String getTenNTD() {
		return tenNTD;
	}

	public void setTenNTD(String tenNTD) {
		this.tenNTD = tenNTD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maNTD == null) ? 0 : maNTD.hashCode());
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
		NhaTuyenDung other = (NhaTuyenDung) obj;
		if (maNTD == null) {
			if (other.maNTD != null)
				return false;
		} else if (!maNTD.equals(other.maNTD))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NhaTuyenDung [maNTD=" + maNTD + ", tenNTD=" + tenNTD + ", email=" + email + ", logo=" + logo
				+ ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi + "]";
	}

	public NhaTuyenDung() {
		super();
	}

	public NhaTuyenDung(String maNTD, String tenNTD, String email, String logo, String soDienThoai, String diaChi) {
		super();
		this.maNTD = maNTD;
		this.tenNTD = tenNTD;
		this.email = email;
		this.logo = logo;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
	}

	public NhaTuyenDung(String maNTD) {
		super();
		this.maNTD = maNTD;
	}

}
