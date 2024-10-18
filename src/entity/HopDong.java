package entity;

import java.time.LocalDate;

public class HopDong {
	private String maHD;
	private double phiDichVu;
	private LocalDate thoiGian;
	private TinTuyenDung tinTuyenDung;
	private UngVien ungVien;
	private NhanVien nhanVien;

	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public double getPhiDichVu() {
		return phiDichVu;
	}

	public void setPhiDichVu(double phiDichVu) {
		this.phiDichVu = phiDichVu;
	}

	public LocalDate getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(LocalDate thoiGian) {
		this.thoiGian = thoiGian;
	}

	public TinTuyenDung getTinTuyenDung() {
		return tinTuyenDung;
	}

	public void setTinTuyenDung(TinTuyenDung tinTuyenDung) {
		this.tinTuyenDung = tinTuyenDung;
	}

	public UngVien getUngVien() {
		return ungVien;
	}

	public void setUngVien(UngVien ungVien) {
		this.ungVien = ungVien;
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
		result = prime * result + ((maHD == null) ? 0 : maHD.hashCode());
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
		HopDong other = (HopDong) obj;
		if (maHD == null) {
			if (other.maHD != null)
				return false;
		} else if (!maHD.equals(other.maHD))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HopDong [maHD=" + maHD + ", phiDichVu=" + phiDichVu + ", thoiGian=" + thoiGian + ", tinTuyenDung="
				+ tinTuyenDung + ", ungVien=" + ungVien + ", nhanVien=" + nhanVien + "]";
	}

	public HopDong(String maHD, double phiDichVu, LocalDate thoiGian, TinTuyenDung tinTuyenDung, UngVien ungVien,
			NhanVien nhanVien) {
		super();
		this.maHD = maHD;
		this.phiDichVu = phiDichVu;
		this.thoiGian = thoiGian;
		this.tinTuyenDung = tinTuyenDung;
		this.ungVien = ungVien;
		this.nhanVien = nhanVien;
	}

	public HopDong() {
		super();
	}

}
