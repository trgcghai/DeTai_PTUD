package entity;

import java.time.LocalDate;

public class UngVien {
	private String maUV;
	private String tenUV;
	private String email;
	private LocalDate ngaySinh;
	private String diaChi;
	private String gioiTinh;
	private String soDienThoai;

	public String getMaUV() {
		return maUV;
	}

	public void setMaUV(String maUV) {
		this.maUV = maUV;
	}

	public String getTenUV() {
		return tenUV;
	}

	public void setTenUV(String tenUV) {
		this.tenUV = tenUV;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maUV == null) ? 0 : maUV.hashCode());
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
		UngVien other = (UngVien) obj;
		if (maUV == null) {
			if (other.maUV != null)
				return false;
		} else if (!maUV.equals(other.maUV))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UngVien [maUV=" + maUV + ", tenUV=" + tenUV + ", email=" + email + ", ngaySinh=" + ngaySinh
				+ ", diaChi=" + diaChi + ", gioiTinh=" + gioiTinh + ", soDienThoai=" + soDienThoai + "]";
	}

	public UngVien() {
		super();
	}

	public UngVien(String maUV, String tenUV, String email, LocalDate ngaySinh, String diaChi, String gioiTinh,
			String soDienThoai) {
		super();
		this.maUV = maUV;
		this.tenUV = tenUV;
		this.email = email;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.gioiTinh = gioiTinh;
		this.soDienThoai = soDienThoai;
	}

	public UngVien(String maUV) {
		super();
		this.maUV = maUV;
	}
}
